package com.nengdong.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.member.entity.EduStudent;
import com.nengdong.member.entity.EduTeacher;
import com.nengdong.member.mapper.EduStudentMapper;
import com.nengdong.member.mapper.EduTeacherMapper;
import com.nengdong.member.service.EduStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.member.service.EduTeacherService;
import com.nengdong.member.utils.MD5;
import com.nengdong.member.vo.LoginVo;
import com.nengdong.member.vo.RegisterVo;
import com.nengdong.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements EduStudentService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    //用户注册
    @Override
    public void register(RegisterVo registerVo) {
        String personname = registerVo.getName();
        String telephone = registerVo.getMobile();
        String checkcode = registerVo.getCode();
        String type = registerVo.getType();
        String password = registerVo.getPassword();
        //1 数据验空
        if(StringUtils.isEmpty(personname)||StringUtils.isEmpty(telephone)||
                StringUtils.isEmpty(checkcode)||StringUtils.isEmpty(password)||StringUtils.isEmpty(type)){
            throw new NengdongException(20001,"注册信息缺失");
        }
        //2 验证手机号是否重复
        QueryWrapper<EduStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",telephone);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new NengdongException(20001,"手机号重复");
        }
        //3 验证短信验证码
        String redisCode = redisTemplate.opsForValue().get(telephone);
        if(!checkcode.equals(redisCode)){
            throw new NengdongException(20001,"验证码错误");
        }
        //4 使用MD5加密密码
        String md5Password = MD5.encrypt(password);
        //5 补充信息后插入数据库
        EduStudent ucenterMember = new EduStudent();
        ucenterMember.setName(personname);
        ucenterMember.setMobile(telephone);
        ucenterMember.setPassword(md5Password);
        ucenterMember.setAvatar("https://nengdong.oss-cn-hongkong.aliyuncs.com/avatar/default.jpg");//默认头像
        ucenterMember.setIsDisabled(false);
        baseMapper.insert(ucenterMember);
    }

    //"https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg"
    @Override
    public String login(LoginVo loginVo) {
        //1 获取参数，验空
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new NengdongException(20001,"手机号或密码有误");
        }
        //2 根据手机号获取用户信息
        QueryWrapper<EduStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        EduStudent ucenterMember = baseMapper.selectOne(queryWrapper);
        if(ucenterMember==null){
                throw new NengdongException(20001, "手机号或密码有误");
        }
        //3 密码加密后验证密码
        String md5Password = MD5.encrypt(password);
        if(!md5Password.equals(ucenterMember.getPassword())){
            throw new NengdongException(20001,"手机号或密码有误");
        }
        //4生成token字符串
        String token = JwtUtils.getJwtTokenUltra(ucenterMember.getId(),ucenterMember.getName(),"student");

        //String token = JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());

        return token;
    }

    @Override
    public Integer countRegister(String day) {
//        Date datebegin = null;
//        Date dateend=null;
//        try {
//            datebegin = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(day+" 00:00:00");
//            dateend = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(day+" 23:59:59");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        QueryWrapper<EduStudent> queryWrapper=new QueryWrapper<>();
//        queryWrapper.ge("gmt_create",datebegin);
//        queryWrapper.le("gmt_create",datebegin);


        Integer count = baseMapper.countRegister(day);
        System.out.println(count);
        return count;
    }

    @Override
    public void updateStudent(EduStudent eduStudent) {
        baseMapper.updateById(eduStudent);
    }

    @Override
    public boolean checkPassword(EduStudent eduStudent) {
        String encrypt = MD5.encrypt(eduStudent.getPassword());
        EduStudent eduStudent1 = baseMapper.selectById(eduStudent.getId());
        if(encrypt.equals(eduStudent1.getPassword())){
            return true;
        }
        return false;

    }

    @Override
    public List<EduStudent> getStudentUcenterByList(List list) {

        List<EduStudent> list1 = baseMapper.selectBatchIds(list);

        return list1;
    }


}
