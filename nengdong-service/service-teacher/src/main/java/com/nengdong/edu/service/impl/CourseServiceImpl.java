package com.nengdong.edu.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nengdong.edu.client.VodClient;
import com.nengdong.edu.entity.Chapter;
import com.nengdong.edu.entity.Course;
import com.nengdong.edu.entity.CourseDescription;
import com.nengdong.edu.entity.Videoandfile;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.edu.mapper.CourseMapper;
import com.nengdong.edu.service.ChapterService;
import com.nengdong.edu.service.CourseDescriptionService;
import com.nengdong.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.edu.service.VideoandfileService;
import com.nengdong.edu.vo.course.CourseInfoForm;
import com.nengdong.edu.vo.course.CoursePublishVo;
import com.nengdong.edu.vo.course.CourseQueryVo;
import com.nengdong.edu.vo.course.CourseWebVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoandfileService videoService;

    @Autowired
    private VodClient vodClient;

    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        //1添加课程信息
        Course eduCourse = new Course();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert==0){
            throw  new NengdongException(20001,"创建课程失败");
        }
        //2获取课程id
        String courseId = eduCourse.getId();
        //3添加课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseId);
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionService.save(courseDescription);

        return courseId;
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        //1根据id查询课程信息
        Course eduCourse = baseMapper.selectById(id);
        System.out.println("----");
        System.out.println(eduCourse);
        //2封装课程信息
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        //3根据id查询课程描述信息
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        //4封装课程描述
        courseInfoForm.setDescription(courseDescription.getDescription());
        return courseInfoForm;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //1复制课程数据
        Course eduCourse = new Course();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        //2更新课程数据
        int update = baseMapper.updateById(eduCourse);
        //3判断是否成功
        if(update==0){
            throw  new NengdongException(20001,"修改课程失败");
        }
        //4更新课程描述
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoForm.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo =
                baseMapper.getCoursePublishById(id);
        return coursePublishVo;
    }

    //根据id删除课程相关信息
    @Override
    public void delCourseInfo(String id) {
        //1  删除视频
        //1.1查询相关小节
        QueryWrapper<Videoandfile> videoIdWrapper = new QueryWrapper<>();
        videoIdWrapper.eq("course_id",id);
        List<Videoandfile> list = videoService.list(videoIdWrapper);
        //1.2遍历获取视频id
        List<String> videoIdList = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            Videoandfile eduVideo = list.get(i);
            videoIdList.add(eduVideo.getVideoSourceId());
        }
        //1.3判断，调接口
        if(videoIdList.size()>0){
            vodClient.delVideo(videoIdList);
        }


        //2删除小节
        QueryWrapper<Videoandfile> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        videoService.remove(videoWrapper);
        // 3删除章节
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",id);
        chapterService.remove(chapterWrapper);
        // 4删除课程描述
        courseDescriptionService.removeById(id);
        // 5删除课程
        int delete = baseMapper.deleteById(id);
        if(delete==0){
            throw  new NengdongException(20001,"删除课程失败");
        }
    }

    //带条件分页查询课程列表
    @Override
    public Map<String, Object> getCourseApiPageVo(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //1 取出查询条件
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();

        //2 验空，不为空拼写到查询条件
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(buyCountSort)){
            queryWrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(gmtCreateSort)){
            queryWrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(priceSort)){
            queryWrapper.orderByDesc("price");
        }
        queryWrapper.eq("status","Normal");
        //3 分页查询
        baseMapper.selectPage(pageParam,queryWrapper);
        //4 封装数据
        List<Course> recordlist = pageParam.getRecords();
        long currentpage = pageParam.getCurrent();
        long pagelist = pageParam.getPages();
        long datasize = pageParam.getSize();
        long totalpage = pageParam.getTotal();
        boolean hasNextpage = pageParam.hasNext();
        boolean hasPreviouspage = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", recordlist);
        map.put("current", currentpage);
        map.put("pages", pagelist);
        map.put("size", datasize);
        map.put("total", totalpage);
        map.put("hasNext", hasNextpage);
        map.put("hasPrevious", hasPreviouspage);
        return map;
    }

    @Override
    public CourseWebVo getCourseWebVo(String id) {
        CourseWebVo courseWebVo = baseMapper.getCourseWebVo(id);
        System.out.println("1231231231231231312312312");
        System.out.println(courseWebVo);
        return courseWebVo;
    }

    //根据教师id查询课程列表
    @Override
    public List<Course> getCoursePublishByTeacherId(String id) {
        System.out.println("++++");
        System.out.println(id);
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("gmt_create");
        courseWrapper.eq("teacher_id",id);
        courseWrapper.last("LIMIT 8");
        List<Course> courseList = this.list(courseWrapper);
        return courseList;
    }

    @Override
    public List<Course> getCoursePublishByListId(List list) {
        List list1 = baseMapper.selectBatchIds(list);
        return list1;
    }

    @Override
    public Integer countCourse(String day) {
        Integer count = baseMapper.countCourse(day);
        System.out.println(count);
        return count;
    }

    @Override
    public Boolean buyCourse(String courseid) {
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.eq("id",courseid);
        Course course = baseMapper.selectOne(courseQueryWrapper);
        course.setBuyCount(course.getBuyCount()+1);
        int i = baseMapper.updateById(course);

        if(i>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Boolean viewCourse(String courseid) {
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.eq("id",courseid);
        Course course = baseMapper.selectOne(courseQueryWrapper);
        course.setViewCount(course.getViewCount()+1);
        int i = baseMapper.updateById(course);

        if(i>0){
            return true;
        }else {
            return false;
        }
    }

}
