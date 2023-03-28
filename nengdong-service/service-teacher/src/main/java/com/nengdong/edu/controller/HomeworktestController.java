package com.nengdong.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.entity.Homeworktest;
import com.nengdong.edu.service.HomeworktestService;
import com.nengdong.edu.vo.homeworktest.*;

import com.nengdong.edu.vo.homeworktestvo.Danxuandomainvo;
import com.nengdong.edu.vo.homeworktestvo.Duoxuandomainvo;
import com.nengdong.edu.vo.homeworktestvo.Nameidandno;
import com.nengdong.edu.vo.homeworktestvo.Wendadomainvo;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-16
 */
@RestController
@Api(description="作业管理")
@RequestMapping("/edu/homeworktest")
@CrossOrigin
public class HomeworktestController {
    @Autowired
    private HomeworktestService homeworktestService;

    @ApiOperation(value = "添加作业信息")
    @PostMapping("addHomeworkExam")
    public Result addHomeworkExam(@RequestBody Homeworkform homeworkform){



        Boolean boolen=homeworktestService.saveByType(homeworkform);
        return Result.ok();
    }

    @ApiOperation(value = "删除作业信息")
    @GetMapping("delHomeworkExam/{homeworkid}")
    public Result delHomeworkExam(@PathVariable String homeworkid){

        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper<>();
        homeworktestQueryWrapper.eq("nameid",homeworkid);

        boolean b = homeworktestService.remove(homeworktestQueryWrapper);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }

    }

    @ApiOperation(value = "教师添加作业信息")
    @PostMapping("addHomeworkExamByTeacher")
    public Result addHomeworkExamByTeacher(@RequestBody Homeworkform homeworkform){



        Boolean boolen=homeworktestService.saveByType(homeworkform);
        return Result.ok();
    }

    @ApiOperation(value = "根据nameid获取name")
    @GetMapping("getNamebyNameid/{nameid}")
    public Result getNamebyNameid(@PathVariable String nameid){
        String name = homeworktestService.getNamebyNameid(nameid);
        return Result.ok().data("name",name);
    }

    @ApiOperation(value = "根据nameid和no获取question")
    @PostMapping("getQuestionbyNameidAndNo")
    public Result getQuestionbyNameidAndNo(@RequestBody Nameidandno nameidandno){
        String question = homeworktestService.getQuestionbyNameidAndNo(nameidandno);
        return Result.ok().data("question",question);
    }



    @ApiOperation(value = "根据试卷id获取作业信息")
    @GetMapping("getHomeworkExamById/{homeworkid}")
    public Result getHomework(@PathVariable String homeworkid){
        List<Homeworktest> homeworktests= homeworktestService.getHomeworkByNameId(homeworkid);



        List<Danxuandomainvo> danxuandomains=new ArrayList();
        List<Duoxuandomainvo> duoxuandomains=new ArrayList();
        List<Wendadomainvo> wendadomains=new ArrayList();


        for (int i = 0; i <homeworktests.size(); i++) {
            String type = homeworktests.get(i).getType();
            if(type.equals("1")){
                Danxuandomainvo danxuandomain = new Danxuandomainvo();
                danxuandomain.setAnswer(homeworktests.get(i).getAnswer());
                danxuandomain.setQuestion(homeworktests.get(i).getQuestion());
                danxuandomain.setScore(homeworktests.get(i).getScore());
                danxuandomain.setValuea(homeworktests.get(i).getValuea());
                danxuandomain.setValueb(homeworktests.get(i).getValueb());
                danxuandomain.setValuec(homeworktests.get(i).getValuec());
                danxuandomain.setValued(homeworktests.get(i).getValued());
                danxuandomain.setNo(homeworktests.get(i).getNo());

                danxuandomains.add(danxuandomain);
            }else if(type.equals("2")) {
                Duoxuandomainvo duoxuandomain =new Duoxuandomainvo();
                String answer = homeworktests.get(i).getAnswer();
                List<String> answerlist=new ArrayList();
                for (int j = 0; j <answer.length(); j++) {
                    answerlist.add(new String(answer.charAt(j)+ ""));
                }

                duoxuandomain.setAnswer(answerlist);
               duoxuandomain.setQuestion(homeworktests.get(i).getQuestion());
               duoxuandomain.setScore(homeworktests.get(i).getScore());
               duoxuandomain.setValuea(homeworktests.get(i).getValuea());
               duoxuandomain.setValueb(homeworktests.get(i).getValueb());
               duoxuandomain.setValuec(homeworktests.get(i).getValuec());
               duoxuandomain.setValued(homeworktests.get(i).getValued());
                duoxuandomain.setNo(homeworktests.get(i).getNo());

                duoxuandomains.add(duoxuandomain);
            }else if(type.equals("3")){
                Wendadomainvo wendadomain = new Wendadomainvo();
                wendadomain.setAnswer(homeworktests.get(i).getAnswer());
                wendadomain.setQuestion(homeworktests.get(i).getQuestion());
                wendadomain.setScore(homeworktests.get(i).getScore());
                wendadomain.setNo(homeworktests.get(i).getNo());
                wendadomains.add(wendadomain);

            }
        }
        Collections.sort(danxuandomains, new Comparator<Danxuandomainvo>() {
            @Override
            public int compare(Danxuandomainvo o1, Danxuandomainvo o2) {
                return Integer.parseInt(o1.getNo())-Integer.parseInt(o2.getNo());
            }
        });
        Collections.sort(duoxuandomains, new Comparator<Duoxuandomainvo>() {
            @Override
            public int compare(Duoxuandomainvo o1, Duoxuandomainvo o2) {
                return Integer.parseInt(o1.getNo())-Integer.parseInt(o2.getNo());
            }
        });
       Collections.sort(wendadomains, new Comparator<Wendadomainvo>() {
           @Override
           public int compare(Wendadomainvo o1, Wendadomainvo o2) {
               return Integer.parseInt(o1.getNo())-Integer.parseInt(o2.getNo());
           }
       });
        return Result.ok().data("danxuandomain",danxuandomains).data("duoxuandomain",duoxuandomains).data("wendadomain",wendadomains).data("message",homeworktests.get(0).getName());
    }

    @ApiOperation(value = "根据试卷id获取作业信息")
    @GetMapping("getHomeworkMessageById/{homeworkid}")
    public Result getHomeworkMessageById(@PathVariable String homeworkid){
        HomeworkMessage homeworkMessage=homeworktestService.getHomeworkMessageById(homeworkid);
        return Result.ok().data("homeworkMessage",homeworkMessage);
    }

    @ApiOperation(value = "根据课程id获取试卷信息")
    @GetMapping("getTestPaperInfo/{courseid}")
    public Result getTestPaperInfo(@PathVariable String courseid){
        List<Testpaperinfo> testpaperinfoList=homeworktestService.getTestPaperInfoByCourseId(courseid);
        return Result.ok().data("testpaperinfoList",testpaperinfoList);
    }

    @ApiOperation(value = "根据试卷id获取课程信息")
    @GetMapping("getCourseIdByNameId/{nameid}")
    public Result getCourseIdByNameId(@PathVariable String nameid){
        String courseid =homeworktestService.getCourseIdByNameId(nameid);
        return Result.ok().data("courseid",courseid);
    }
}

