package com.nengdong.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.vo.subject.ExcelSubjectData;
import com.nengdong.edu.vo.subject.OneSubjectVo;
import com.nengdong.edu.vo.subject.TwoSubjectVo;
import com.nengdong.edu.entity.Subject;
import com.nengdong.edu.listener.SubjectExcelListener;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.edu.mapper.SubjectMapper;
import com.nengdong.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    //批量导入课程分类
    @Override
    public void addSubject(MultipartFile file, SubjectService subjectService) {
        try {
            subjectService.flush();
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,
                    new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
            throw new NengdongException(20001,"导入课程分类失败");

        }
    }

    //查询所有课程分类
    @Override
    public List<OneSubjectVo> getAllSubject() {
        //查询一级分类
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<Subject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //查询二级分类
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<Subject> twoSubjectList = baseMapper.selectList(wrapperTwo);


        //封装一级分类
        List<OneSubjectVo> allSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //3.1取出每一个一级分类
            Subject oneSubject = oneSubjectList.get(i);
            //3.2EduSubject转化OneSubjectVo
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
//            oneSubjectVo.setId(oneSubject.getId());
//            oneSubjectVo.setTitle(oneSubject.getTitle());
            BeanUtils.copyProperties(oneSubject, oneSubjectVo);
            allSubjectList.add(oneSubjectVo);

            //找到根一级有关的二级进行封装
            List<TwoSubjectVo> twoSubjectVos  = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //4.1取出每一个二级分类
                Subject twoSubject = twoSubjectList.get(m);
                //4.2 判断是否归属此一级
                if(twoSubject.getParentId().equals(oneSubject.getId())){
                    //4.3EduSubject转化TwoSubjectVo
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject,twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                }
            }
            oneSubjectVo.setChildren(twoSubjectVos);
        }
        return allSubjectList;
    }

    @Override
    public void flush() {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.ge("id",0);
        baseMapper.delete(wrapper);
    }
}
