package com.nengdong.edu.api;




import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nengdong.edu.client.OrderClient;
import com.nengdong.edu.entity.Course;
import com.nengdong.edu.service.ChapterService;
import com.nengdong.edu.service.CourseService;
import com.nengdong.edu.vo.chapter.ChapterVo;
import com.nengdong.edu.vo.course.CourseQueryVo;
import com.nengdong.edu.vo.course.CourseWebVo;
import com.nengdong.utils.JwtUtils;
import com.nengdong.utils.Result;
import com.nengdong.utils.vo.CourseWebVoForOrder;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(description="前台课程展示")
@RestController
@RequestMapping("/edu/courseapi")
@CrossOrigin
public class CourseApiController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "带条件分页查询课程列表")
    @PostMapping("getCourseApiPageVo/{current}/{limit}")
    public Result getCourseApiPageVo(@PathVariable Long current,
                                     @PathVariable Long limit,
                                     @RequestBody CourseQueryVo courseQueryVo){
        Page<Course> page = new Page<>(current,limit);
        System.out.println("--------------------------");
        System.out.println(courseQueryVo);
        System.out.println("--------------------------");
        Map<String,Object> map = courseService.getCourseApiPageVo(page,courseQueryVo);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询课程相关信息")
    @GetMapping("getCourseWebInfo/{courseId}")
    public Result getCourseWebInfo(@PathVariable String courseId,
                              HttpServletRequest request){
        //1 查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        //2查询课程大纲信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(courseId);
        //3根据课程id、用户id查询是已购买,远程调用
        Claims memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String memberId=(String)memberIdByJwtToken.get("id");
        System.out.println("memberId ="+memberId);
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, memberId);
        return Result.ok().data("courseWebVo",courseWebVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuyCourse",isBuyCourse);
    }


    @ApiOperation(value = "根据课程id查询课程相关信息跨模块")
    @GetMapping("getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(
            @PathVariable("courseId") String courseId){
        //1 查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        CourseWebVoForOrder courseWebVoForOrder = new CourseWebVoForOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoForOrder);
        return courseWebVoForOrder;
    }


    @ApiOperation(value = "根据多个课程id查询课程发布信息")
    @PostMapping("getCoursePublishByListId")
    public Result getCoursePublishByListId(@RequestBody List<String> list){
        List<Course> listvo = courseService.getCoursePublishByListId(list);
        return Result.ok().data("coursePublishVo",listvo);
    }


}
