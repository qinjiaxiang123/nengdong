package com.nengdong.edu.service;

import com.nengdong.edu.vo.subject.OneSubjectVo;
import com.nengdong.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
public interface SubjectService extends IService<Subject> {

    void addSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubjectVo> getAllSubject();

    void flush();
}
