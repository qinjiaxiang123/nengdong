package com.nengdong.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.vo.subject.ExcelSubjectData;
import com.nengdong.edu.entity.Subject;

import com.nengdong.edu.manage.NengdongException;
import com.nengdong.edu.service.SubjectService;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener() {}
    //创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        //1读取数据验空

        if(excelSubjectData==null){
            throw  new NengdongException(20001,"导入课程分类失败");
        }


        //2判断一级分类名称是否重复
        Subject existOneSubject = this.existOneSubject(subjectService, excelSubjectData.getOneSubjectName());

        //3一级不重复插入数据库
        if(existOneSubject==null){
            existOneSubject = new Subject();
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();
        //4判断二级名称是否重复
        System.out.println(excelSubjectData.getTwoSubjectName());
        if(excelSubjectData.getTwoSubjectName()!=null) {
            Subject existTwoSubject = this.existTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(), pid);
            //5二级不重复插入数据库
            if (existTwoSubject == null) {
                existTwoSubject = new Subject();
                existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
                existTwoSubject.setParentId(pid);
                subjectService.save(existTwoSubject);
            }
        }
    }

    //判断一级分类是否重复
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        wrapper.eq("title",name);
        Subject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //判断二级分类是否重复
    private Subject existTwoSubject(SubjectService subjectService, String name,String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",pid);
        wrapper.eq("title",name);
        Subject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

