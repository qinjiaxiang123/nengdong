package com.nengdong.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nengdong.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-22
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeacherApiPage(Page<Teacher> page);

    Integer countTeacher(String day);
}
