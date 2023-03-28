package com.nengdong.edu.mapper;

import com.nengdong.edu.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-22
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    Integer countTeacher(String day);
}
