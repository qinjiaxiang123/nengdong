package com.nengdong.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nengdong.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.edu.vo.course.CourseInfoForm;
import com.nengdong.edu.vo.course.CoursePublishVo;
import com.nengdong.edu.vo.course.CourseQueryVo;
import com.nengdong.edu.vo.course.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
public interface CourseService extends IService<Course> {


    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishById(String id);

    void delCourseInfo(String id);

    Map<String, Object> getCourseApiPageVo(Page<Course> page, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseWebVo(String courseId);

    List<Course> getCoursePublishByTeacherId(String id);

    List<Course> getCoursePublishByListId(List list);

    Integer countCourse(String day);

    Boolean buyCourse(String courseid);

    Boolean viewCourse(String courseid);
}
