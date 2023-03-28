package com.nengdong.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.member.entity.EduStudent;
import com.nengdong.member.entity.EduTeacher;
import com.nengdong.member.mapper.EduTeacherMapper;
import com.nengdong.member.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.member.utils.MD5;
import com.nengdong.member.vo.LoginVo;
import com.nengdong.member.vo.RegisterVo;
import com.nengdong.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    @Override
    public void register(RegisterVo registerVo) {
        //1 获取参数，验空
        String name = registerVo.getName();
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String type = registerVo.getType();
        String password = registerVo.getPassword();

        System.out.println(registerVo);
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(mobile)||
                StringUtils.isEmpty(code)||StringUtils.isEmpty(password)||StringUtils.isEmpty(type)){
            throw new NengdongException(20001,"注册信息缺失");
        }
        //2 验证手机号是否重复
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new NengdongException(20001,"手机号重复");
        }
        //3 验证短信验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new NengdongException(20001,"验证码错误");
        }


        //5 补充信息后插入数据库
        EduTeacher ucenterMember = new EduTeacher();
        ucenterMember.setName(name);
        ucenterMember.setTelephone(mobile);
        ucenterMember.setPassword(password);
        ucenterMember.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        ucenterMember.setIsDeleted(false);
        baseMapper.insert(ucenterMember);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1 获取参数，验空
        String telephone = loginVo.getMobile();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(telephone)||StringUtils.isEmpty(password)){
            throw new NengdongException(20001,"手机号或密码有误");
        }
        //2 根据手机号获取用户信息
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone);
        EduTeacher ucenterMember = baseMapper.selectOne(queryWrapper);
        if(ucenterMember==null){
            throw new NengdongException(20001, "手机号或密码有误");
        }

        if(!password.equals(ucenterMember.getPassword())){
            throw new NengdongException(20001,"手机号或密码有误");
        }
        //4生成token字符串
        String token = JwtUtils.getJwtTokenUltra(ucenterMember.getId(),ucenterMember.getName(),"teacher");

        //String token = JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());

        return token;
    }
}
