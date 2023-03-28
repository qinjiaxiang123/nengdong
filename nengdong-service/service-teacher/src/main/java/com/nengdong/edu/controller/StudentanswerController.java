package com.nengdong.edu.controller;


import com.nengdong.edu.entity.Homeworktest;
import com.nengdong.edu.entity.Studentanswer;
import com.nengdong.edu.service.HomeworktestService;
import com.nengdong.edu.service.StudentanswerService;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Studentanserwenda;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Studentanswerdanxuan;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Studentanswerduoxuan;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Totalform;
import com.nengdong.edu.vo.studentanswer.*;
import com.nengdong.edu.vo.studentanswer.timu.Studentanswerdanxuanvo;
import com.nengdong.edu.vo.studentanswer.timu.Studentanswerduoxuanvo;
import com.nengdong.edu.vo.studentanswer.timu.Studentanswerwendavo;
import com.nengdong.utils.Result;
import com.nengdong.utils.TransferUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-19
 */
@RestController
@Api(description="试卷管理")
@CrossOrigin
@RequestMapping("/edu/studentanswer")
public class StudentanswerController {


    @Autowired
    StudentanswerService studentanswerService;



    @Autowired
    HomeworktestService homeworktestService;
    @ApiOperation(value="添加学生试卷")
    @PostMapping("addStudentAnswer")
    public Result addStudentAnswer(@RequestBody Totalform totalform){
        String courseIdByNameId = homeworktestService.getCourseIdByNameId(totalform.getNameid());
        totalform.setCourseid(courseIdByNameId);
        boolean b=studentanswerService.addStudentAnswer(totalform);
        return Result.ok();
    }

    @ApiOperation(value="根据试卷nameid和studentname获取已完成的试卷")
    @PostMapping("getStudentAnswerByNameIdAndStudentName")
    public Result getStudentAnswerByNameIdAndStudentName(@RequestBody Nameidandstudentname nameidandstudentname){
         List<Studentanswer> studentanswerList=studentanswerService.getByNameIdAndStudentName(nameidandstudentname);


         Studentanswervo studentanswervo = new Studentanswervo();

         List<Studentanswerdanxuanvo> studentanswerdanxuanvoList=new ArrayList<>();
         List<Studentanswerduoxuanvo> studentanswerduoxuanvoList=new ArrayList<>();
         List<Studentanswerwendavo> studentanswerwendavoList=new ArrayList<>();


        String title=new String();
        for (Studentanswer s:studentanswerList
             ) {
            List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());
            title= homeworktestList.get(0).getName();
        }





        for (Studentanswer s:studentanswerList
             ) {
            if(s.getType().equals("1")){
                List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());



                Studentanswerdanxuanvo studentanswerdanxuanvo = new Studentanswerdanxuanvo();
                for (Homeworktest h:homeworktestList
                     ) {
                    if(s.getNo().equals(h.getNo())&&h.getType().equals("1")){

                        studentanswerdanxuanvo.setAnswer(TransferUtils.numberlisttostringlist(s.getCorrectanswer()));
                        studentanswerdanxuanvo.setIscorrect(s.getIsCorrect());
                        studentanswerdanxuanvo.setNo(s.getNo());
                        studentanswerdanxuanvo.setQuestion(h.getQuestion());
                        studentanswerdanxuanvo.setScore(s.getScore());
                        studentanswerdanxuanvo.setStudentscore(s.getStudentscore());
                        studentanswerdanxuanvo.setStudentanswer(s.getAnswer());
                        studentanswerdanxuanvo.setValuea(h.getValuea());
                        studentanswerdanxuanvo.setValueb(h.getValueb());
                        studentanswerdanxuanvo.setValuec(h.getValuec());
                        studentanswerdanxuanvo.setValued(h.getValued());
                    }
                }

                studentanswerdanxuanvoList.add(studentanswerdanxuanvo);

            }else if(s.getType().equals("2")){
                List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());

                Studentanswerduoxuanvo studentanswerduoxuanvo = new Studentanswerduoxuanvo();
                for (Homeworktest h:homeworktestList
                ) {
                    if(s.getNo().equals(h.getNo())&&h.getType().equals("2")){

                        String correctanswer = TransferUtils.numberlisttostringlist(s.getCorrectanswer());
                        char[] chars = correctanswer.toCharArray();
                        Arrays.sort(chars);

                        List<String> list=new ArrayList<>();

                        for (int i = 0; i <chars.length ; i++) {
                            list.add(String.valueOf(chars[i]));
                        }


                        studentanswerduoxuanvo.setAnswer(list);

                        studentanswerduoxuanvo.setIscorrect(s.getIsCorrect());
                        studentanswerduoxuanvo.setNo(s.getNo());
                        studentanswerduoxuanvo.setQuestion(h.getQuestion());
                        studentanswerduoxuanvo.setScore(s.getScore());
                        studentanswerduoxuanvo.setStudentscore(s.getStudentscore());


                        String answer = s.getAnswer();
                        char[] chars1 = answer.toCharArray();
                        Arrays.sort(chars1);
                        List<String> list1=new ArrayList<>();

                        for (int i = 0; i <chars1.length ; i++) {
                            list1.add(String.valueOf(chars1[i]));
                        }


                        studentanswerduoxuanvo.setStudentanswer(list1);
                        studentanswerduoxuanvo.setValuea(h.getValuea());
                        studentanswerduoxuanvo.setValueb(h.getValueb());
                        studentanswerduoxuanvo.setValuec(h.getValuec());
                        studentanswerduoxuanvo.setValued(h.getValued());
                    }
                }

                studentanswerduoxuanvoList.add(studentanswerduoxuanvo);

            }else if(s.getType().equals("3")){
                List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());

                Studentanswerwendavo studentanswerwendavo = new Studentanswerwendavo();
                for (Homeworktest h:homeworktestList
                ) {
                    if(s.getNo().equals(h.getNo())&&h.getType().equals("3")){




                       studentanswerwendavo.setAnswer(s.getCorrectanswer());
                       studentanswerwendavo.setIscorrect(s.getIsCorrect());
                       studentanswerwendavo.setNo(s.getNo());
                       studentanswerwendavo.setQuestion(h.getQuestion());
                       studentanswerwendavo.setScore(s.getScore());
                       studentanswerwendavo.setStudentscore(s.getStudentscore());
                       studentanswerwendavo.setStudentanswer(s.getAnswer());

                    }
                }

                studentanswerwendavoList.add(studentanswerwendavo);

            }
        }


        studentanswervo.setStudentanswerdanxuanvoList(studentanswerdanxuanvoList);
        studentanswervo.setStudentanswerduoxuanList(studentanswerduoxuanvoList);
        studentanswervo.setStudentanswerwendavoList(studentanswerwendavoList);

        studentanswervo.setTitle(title);

        return Result.ok().data("studentanswervo",studentanswervo);
    }




    @ApiOperation(value="根据试卷nameid获取问答题")
    @PostMapping("getStudentAnswerWenDaByNameIdAndStudentName")
    public Result getStudentAnswerWenDaByNameIdAndStudentName(@RequestBody Nameidandstudentname nameidandstudentname){
        List<Studentanswer> studentanswerList=studentanswerService.getWenDaByNameIdAndStudentName(nameidandstudentname);
        return Result.ok().data("Stuentanswer",studentanswerList);
    }

    @ApiOperation(value="根据试卷nameid获取平均分")
    @GetMapping("getAverageByNameIdAndStudentName/{nameid}")
    public Result getAverageByNameIdAndStudentName(@PathVariable String nameid){

        Studentansweraverage studentansweraverage=studentanswerService.getAverageByNameIdAndStudentName(nameid);
        return Result.ok().data("studentansweraverage",studentansweraverage);
    }


    @ApiOperation(value="根据试卷nameid和studentname获取试卷状态")
    @PostMapping("getStatusByNameIdAndStudentName")
    public Result getStatusByNameIdAndStudentName(@RequestBody Nameidandstudentname nameidandstudentname){
        boolean b=studentanswerService.getStatusByNameIdAndStudentName(nameidandstudentname);
        return Result.ok().data("isdone",b);
    }



    @ApiOperation(value="根据试卷nameid获取试卷分数")
    @GetMapping("getScoreByNameId/{nameid}")
    public Result getScoreByNameId(@PathVariable String nameid){
        Scorelist scorelist=studentanswerService.getScoreByNameId(nameid);
        return Result.ok().data("scorelist",scorelist);
    }


    @ApiOperation(value="根据试卷nameid查询统计数据")
    @GetMapping("getScoreStaByNameIdAndStudentId/{nameid}")
    public Result getScoreStaByNameIdAndStudentId(@PathVariable String nameid){
        Staofscore staofscore =studentanswerService.getScoreStaByNameIdAndStudentId(nameid);
        return Result.ok().data("staofscore",staofscore);
    }

    @ApiOperation(value="根据试卷courseid和studentname获取试卷id")
    @PostMapping("getNameIdByNameIdAndStudentName")
    public Result getNameIdByNameIdAndStudentName(@RequestBody Courseidandstudentname courseidandstudentname){
        String nameid =studentanswerService.getNameIdByNameIdAndStudentName(courseidandstudentname);
        return Result.ok().data("nameid",nameid);
    }

    @ApiOperation(value="根据试卷courseid和studentname获取试卷列表")
    @PostMapping("getAnswerListByNameIdAndStudentName")
    public Result getAnswerListByNameIdAndStudentName(@RequestBody Courseidandstudentname courseidandstudentname){
        List<Answerlist> answerlist=studentanswerService.getAnswerListByNameIdAndStudentName(courseidandstudentname);
        for (int i = 0; i <answerlist.size(); i++) {
            answerlist.get(i).setName(homeworktestService.getNamebyNameid(answerlist.get(i).getNameid()));
        }


        return Result.ok().data("answerlist",answerlist);
    }

    @ApiOperation(value="根据试卷id判断是否要进行批改")
    @GetMapping("getCorrectStatusByNameId/{nameid}")
    public Result getCorrectStatusByNameId(@PathVariable String nameid){
        boolean b  =studentanswerService.getCorrectStatusByNameId(nameid);
        return Result.ok().data("CorrectStatus",b);
    }

    @ApiOperation(value="根据试卷id获取需要批改的题目")
    @GetMapping("getNeedCorrectByNameId/{nameid}")
    public Result getNeedCorrectByNameId(@PathVariable String nameid){
        List<Studentanswer> studentanswerList =studentanswerService.getNeedCorrectByNameId(nameid);
        return Result.ok().data("studentanswerList",studentanswerList);
    }

    @ApiOperation(value="根据试卷id和no批改问答题")
    @PostMapping("correctByNameIdandNoandData")
    public Result correctByNameIdandNoandData(@RequestBody Nameidandnoandscore nameidandnoandscore){
        boolean b  =studentanswerService.correctByNameIdandNoandData(nameidandnoandscore);
        return Result.ok();
    }





    @ApiOperation(value="根据试卷nameid获取已完成的试卷")
    @GetMapping("getStudentAnswerByNameId/{nameid}")
    public Result getStudentAnswerByNameId(@PathVariable String nameid){
        List<Studentanswer> studentanswerList=studentanswerService.getStudentAnswerByNameId(nameid);


        Studentanswervo studentanswervo = new Studentanswervo();

        List<Studentanswerdanxuanvo> studentanswerdanxuanvoList=new ArrayList<>();
        List<Studentanswerduoxuanvo> studentanswerduoxuanvoList=new ArrayList<>();
        List<Studentanswerwendavo> studentanswerwendavoList=new ArrayList<>();


        String title=new String();
        for (Studentanswer s:studentanswerList
        ) {
            List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());
            title= homeworktestList.get(0).getName();
        }





        for (Studentanswer s:studentanswerList
        ) {
            if(s.getType().equals("1")){
                List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());



                Studentanswerdanxuanvo studentanswerdanxuanvo = new Studentanswerdanxuanvo();
                for (Homeworktest h:homeworktestList
                ) {
                    if(s.getNo().equals(h.getNo())&&h.getType().equals("1")){

                        studentanswerdanxuanvo.setAnswer(TransferUtils.numberlisttostringlist(s.getCorrectanswer()));
                        studentanswerdanxuanvo.setIscorrect(s.getIsCorrect());
                        studentanswerdanxuanvo.setNo(s.getNo());
                        studentanswerdanxuanvo.setQuestion(h.getQuestion());
                        studentanswerdanxuanvo.setScore(s.getScore());
                        studentanswerdanxuanvo.setStudentscore(s.getStudentscore());
                        studentanswerdanxuanvo.setStudentanswer(s.getAnswer());
                        studentanswerdanxuanvo.setValuea(h.getValuea());
                        studentanswerdanxuanvo.setValueb(h.getValueb());
                        studentanswerdanxuanvo.setValuec(h.getValuec());
                        studentanswerdanxuanvo.setValued(h.getValued());
                    }
                }

                studentanswerdanxuanvoList.add(studentanswerdanxuanvo);

            }else if(s.getType().equals("2")){
                List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());

                Studentanswerduoxuanvo studentanswerduoxuanvo = new Studentanswerduoxuanvo();
                for (Homeworktest h:homeworktestList
                ) {
                    if(s.getNo().equals(h.getNo())&&h.getType().equals("2")){

                        String correctanswer = TransferUtils.numberlisttostringlist(s.getCorrectanswer());
                        char[] chars = correctanswer.toCharArray();
                        Arrays.sort(chars);

                        List<String> list=new ArrayList<>();

                        for (int i = 0; i <chars.length ; i++) {
                            list.add(String.valueOf(chars[i]));
                        }


                        studentanswerduoxuanvo.setAnswer(list);

                        studentanswerduoxuanvo.setIscorrect(s.getIsCorrect());
                        studentanswerduoxuanvo.setNo(s.getNo());
                        studentanswerduoxuanvo.setQuestion(h.getQuestion());
                        studentanswerduoxuanvo.setScore(s.getScore());
                        studentanswerduoxuanvo.setStudentscore(s.getStudentscore());


                        String answer = s.getAnswer();
                        char[] chars1 = answer.toCharArray();
                        Arrays.sort(chars1);
                        List<String> list1=new ArrayList<>();

                        for (int i = 0; i <chars1.length ; i++) {
                            list1.add(String.valueOf(chars1[i]));
                        }


                        studentanswerduoxuanvo.setStudentanswer(list1);
                        studentanswerduoxuanvo.setValuea(h.getValuea());
                        studentanswerduoxuanvo.setValueb(h.getValueb());
                        studentanswerduoxuanvo.setValuec(h.getValuec());
                        studentanswerduoxuanvo.setValued(h.getValued());
                    }
                }

                studentanswerduoxuanvoList.add(studentanswerduoxuanvo);

            }else if(s.getType().equals("3")){
                List<Homeworktest> homeworktestList = homeworktestService.getHomeworkByNameId(s.getNameId());

                Studentanswerwendavo studentanswerwendavo = new Studentanswerwendavo();
                for (Homeworktest h:homeworktestList
                ) {
                    if(s.getNo().equals(h.getNo())&&h.getType().equals("3")){




                        studentanswerwendavo.setAnswer(s.getCorrectanswer());
                        studentanswerwendavo.setIscorrect(s.getIsCorrect());
                        studentanswerwendavo.setNo(s.getNo());
                        studentanswerwendavo.setQuestion(h.getQuestion());
                        studentanswerwendavo.setScore(s.getScore());
                        studentanswerwendavo.setStudentscore(s.getStudentscore());
                        studentanswerwendavo.setStudentanswer(s.getAnswer());

                    }
                }

                studentanswerwendavoList.add(studentanswerwendavo);

            }
        }


        studentanswervo.setStudentanswerdanxuanvoList(studentanswerdanxuanvoList);
        studentanswervo.setStudentanswerduoxuanList(studentanswerduoxuanvoList);
        studentanswervo.setStudentanswerwendavoList(studentanswerwendavoList);

        studentanswervo.setTitle(title);

        return Result.ok().data("studentanswervo",studentanswervo);
    }

}

