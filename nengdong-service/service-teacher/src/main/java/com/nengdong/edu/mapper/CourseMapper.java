package com.nengdong.edu.mapper;

import com.nengdong.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nengdong.edu.vo.course.CoursePublishVo;
import com.nengdong.edu.vo.course.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublishById(String id);

    CourseWebVo getCourseWebVo(String id);


    Integer countCourse(String day);
}
