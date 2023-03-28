package com.nengdong.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.entity.Studentanswer;
import com.nengdong.edu.mapper.StudentanswerMapper;
import com.nengdong.edu.service.StudentanswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Studentanserwenda;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Studentanswerdanxuan;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Studentanswerduoxuan;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Totalform;
import com.nengdong.edu.vo.studentanswer.*;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-19
 */
@Service
public class StudentanswerServiceImpl extends ServiceImpl<StudentanswerMapper, Studentanswer> implements StudentanswerService {

    @Override
    public boolean addStudentAnswer(Totalform totalform) {
        List<Studentanswerdanxuan> studentanswerdanxuanList = totalform.getDanxuan();
        List<Studentanswerduoxuan> studentanswerduoxuanList = totalform.getDuoxuan();
        List<Studentanserwenda> studentanswerwendaList = totalform.getWenda();

        String courseid = totalform.getCourseid();
        System.out.println(courseid);
        String studentid= totalform.getStudentid();
        String nameid = totalform.getNameid();

        for (Studentanswerdanxuan s:studentanswerdanxuanList
             ) {
            Studentanswer studentanswer = new Studentanswer();
            studentanswer.setNameId(nameid);
            studentanswer.setCourseId(courseid);
            studentanswer.setStudentId(studentid);
            studentanswer.setAnswer(s.getStudentanswer());
            studentanswer.setCorrectanswer(s.getAnswer());
            studentanswer.setNo(s.getNo());
            studentanswer.setType("1");
            studentanswer.setScore(s.getScore());

            if(s.getAnswer().equals(s.getStudentanswer())){
                studentanswer.setIsCorrect("1");
                studentanswer.setStudentscore(s.getScore());
            }else {
                studentanswer.setIsCorrect("0");
                studentanswer.setStudentscore("0");
            }
            baseMapper.insert(studentanswer);
        }

        for (Studentanswerduoxuan s:studentanswerduoxuanList
        ) {
            Studentanswer studentanswer = new Studentanswer();
            studentanswer.setNameId(nameid);
            studentanswer.setCourseId(courseid);
            studentanswer.setStudentId(studentid);
            studentanswer.setNo(s.getNo());
            studentanswer.setType("2");
            studentanswer.setScore(s.getScore());

            List<String> answer = s.getAnswer();
            String totalanswer=new String();
            for (String s1:answer
                 ) {
                totalanswer+=s1;
            }
            char[] chars = totalanswer.toCharArray();
            Arrays.sort(chars);
            String totalanswersort=new String(chars);
            List<String>  studentanswer1 = s.getStudentanswer();
            String totalanswer1=new String();
            for (String s1:studentanswer1
            ) {
                totalanswer1+=s1;
            }
            char[] chars1 = totalanswer1.toCharArray();
            Arrays.sort(chars1);

            String totalanswersort1=new String(chars1);

            studentanswer.setAnswer(totalanswersort1);
            studentanswer.setCorrectanswer(totalanswersort);
            if(totalanswersort.equals(totalanswersort1)){
                studentanswer.setIsCorrect("1");
                studentanswer.setStudentscore(s.getScore());
            }else {
                studentanswer.setIsCorrect("0");
                studentanswer.setStudentscore("0");
            }

            baseMapper.insert(studentanswer);
        }

        for (Studentanserwenda s:studentanswerwendaList
             ) {
            Studentanswer studentanswer = new Studentanswer();
            studentanswer.setNameId(nameid);
            studentanswer.setCourseId(courseid);
            studentanswer.setStudentId(studentid);
            studentanswer.setNo(s.getNo());
            studentanswer.setType("3");
            studentanswer.setScore(s.getScore());
            studentanswer.setIsCorrect("0");
            studentanswer.setStudentscore("-1");
            studentanswer.setCorrectanswer(s.getAnswer());
            studentanswer.setAnswer(s.getStudentanswer());

            baseMapper.insert(studentanswer);

        }
        return true;
    }

    @Override
    public boolean getStatusByNameIdAndStudentName(Nameidandstudentname nameidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameidandstudentname.getNameid());
        studentanswerQueryWrapper.eq("student_id",nameidandstudentname.getStudentname());


        Integer integer = baseMapper.selectCount(studentanswerQueryWrapper);

        if (integer>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Scorelist getScoreByNameIdAndStudentName(Nameidandstudentname nameidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameidandstudentname.getNameid());
        studentanswerQueryWrapper.eq("student_id",nameidandstudentname.getStudentname());

        Scorelist scorelist = new Scorelist();


        int studentanswerdanxuanscore=0;
        int studentanswertotaldanxuanscore=0;
        int studentanswerduoxuanscore=0;
        int studentanswertotalduoxuanscore=0;
        int studentanswerwendascore=0;
        int studentanswertotalwendascore=0;



        List<Studentanswer> studentanswerList = baseMapper.selectList(studentanswerQueryWrapper);
        for (Studentanswer s:studentanswerList
             ) {
            if(s.getType().equals("1")){
                studentanswertotaldanxuanscore+=Integer.parseInt(s.getScore());
                studentanswerdanxuanscore+=Integer.parseInt(s.getStudentscore());
            }else if(s.getType().equals("2")){
                studentanswertotalduoxuanscore+=Integer.parseInt(s.getScore());
                studentanswerduoxuanscore+=Integer.parseInt(s.getStudentscore());
            }else if(s.getType().equals("3")){
                if(Integer.parseInt(s.getStudentscore())!=-1) {
                    studentanswerwendascore += Integer.parseInt(s.getStudentscore());
                }else {
                    studentanswerwendascore += Integer.parseInt("0");
                }
                studentanswertotalwendascore += Integer.parseInt(s.getScore());

            }

        }
        scorelist.setTotalscore(String.valueOf(studentanswertotaldanxuanscore+studentanswertotalduoxuanscore+studentanswertotalwendascore));
        scorelist.setStudenttotalscorel(String.valueOf(studentanswerdanxuanscore+studentanswerduoxuanscore+studentanswerwendascore));
        scorelist.setDanxuanscore(String.valueOf(studentanswertotaldanxuanscore));
        scorelist.setStudentdanxuanscore(String.valueOf(studentanswerdanxuanscore));
        scorelist.setDuoxuanscore(String.valueOf(studentanswertotalduoxuanscore));
        scorelist.setStudentduoxuanscore(String.valueOf(studentanswerduoxuanscore));
        scorelist.setWendascore(String.valueOf(studentanswertotalwendascore));
        scorelist.setStudentwendascore(String.valueOf(studentanswerwendascore));

        return scorelist;

    }

    @Override
    public String getNameIdByNameIdAndStudentName(Courseidandstudentname courseidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("course_id",courseidandstudentname.getCourseid());
        studentanswerQueryWrapper.eq("student_id",courseidandstudentname.getStudentname());
        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);
        return studentanswers.get(0).getNameId();
    }

    @Override
    public boolean getCorrectStatusByNameId(String nameid) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameid);
        studentanswerQueryWrapper.eq("type","3");
        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);

        for (Studentanswer s:studentanswers
             ) {
            if (Integer.parseInt(s.getStudentscore())<0){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Studentanswer> getStudentAnswerByNameId(String nameid) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameid);
        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);

        return studentanswers;
    }

    @Override
    public boolean correctByNameIdandNoandData(Nameidandnoandscore nameidandnoandscore) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameidandnoandscore.getNameid());
        studentanswerQueryWrapper.eq("no",nameidandnoandscore.getNo());
        studentanswerQueryWrapper.eq("type","3");
        Studentanswer studentanswer = baseMapper.selectOne(studentanswerQueryWrapper);
        studentanswer.setStudentscore(nameidandnoandscore.getScore());
        baseMapper.updateById(studentanswer);


        return true;
    }

    @Override
    public Scorelist getScoreByNameId(String nameid) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameid);

        Scorelist scorelist = new Scorelist();


        int studentanswerdanxuanscore=0;
        int studentanswertotaldanxuanscore=0;
        int studentanswerduoxuanscore=0;
        int studentanswertotalduoxuanscore=0;
        int studentanswerwendascore=0;
        int studentanswertotalwendascore=0;



        List<Studentanswer> studentanswerList = baseMapper.selectList(studentanswerQueryWrapper);
        for (Studentanswer s:studentanswerList
        ) {
            if(s.getType().equals("1")){
                studentanswertotaldanxuanscore+=Integer.parseInt(s.getScore());
                studentanswerdanxuanscore+=Integer.parseInt(s.getStudentscore());
            }else if(s.getType().equals("2")){
                studentanswertotalduoxuanscore+=Integer.parseInt(s.getScore());
                studentanswerduoxuanscore+=Integer.parseInt(s.getStudentscore());
            }else if(s.getType().equals("3")){
                if(Integer.parseInt(s.getStudentscore())!=-1) {
                    studentanswerwendascore += Integer.parseInt(s.getStudentscore());
                }else {
                    studentanswerwendascore += Integer.parseInt("0");
                }
                studentanswertotalwendascore += Integer.parseInt(s.getScore());

            }

        }
        scorelist.setTotalscore(String.valueOf(studentanswertotaldanxuanscore+studentanswertotalduoxuanscore+studentanswertotalwendascore));
        scorelist.setStudenttotalscorel(String.valueOf(studentanswerdanxuanscore+studentanswerduoxuanscore+studentanswerwendascore));
        scorelist.setDanxuanscore(String.valueOf(studentanswertotaldanxuanscore));
        scorelist.setStudentdanxuanscore(String.valueOf(studentanswerdanxuanscore));
        scorelist.setDuoxuanscore(String.valueOf(studentanswertotalduoxuanscore));
        scorelist.setStudentduoxuanscore(String.valueOf(studentanswerduoxuanscore));
        scorelist.setWendascore(String.valueOf(studentanswertotalwendascore));
        scorelist.setStudentwendascore(String.valueOf(studentanswerwendascore));

        return scorelist;
    }

    @Override
    public List<Answerlist> getAnswerListByNameIdAndStudentName(Courseidandstudentname courseidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("course_id",courseidandstudentname.getCourseid());
        studentanswerQueryWrapper.eq("student_id",courseidandstudentname.getStudentname());

        List<Answerlist> answerlists=new ArrayList<>();

        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);

        HashSet<String> hashSet=new HashSet();
        for (Studentanswer s:studentanswers
             ) {
            hashSet.add(s.getNameId());
        }

        List<String> nameidlist = new ArrayList<>();
        for (String s:hashSet
             ) {
            nameidlist.add(s);
        }


        for (int i = 0; i <nameidlist.size() ; i++) {
            Answerlist answerlist = new Answerlist();

            QueryWrapper<Studentanswer> studentanswerQueryWrapper1=new QueryWrapper<>();
            studentanswerQueryWrapper.eq("name_id",nameidlist.get(i));

            List<Studentanswer> studentanswers1 = baseMapper.selectList(studentanswerQueryWrapper1);




            float studentscore=0;
            float totalscore=0;

            for (Studentanswer s:studentanswers1
                 ) {
                if(s.getType().equals("3")&&Float.parseFloat(s.getStudentscore())<0){
                    studentscore+=0;
                }else {
                    studentscore+=Float.parseFloat(s.getStudentscore());
                }
                totalscore+=Float.parseFloat(s.getScore());


            }



            answerlist.setNameid(studentanswers1.get(0).getNameId());
            answerlist.setStudentscore(studentscore+"");
            answerlist.setTotalscore(totalscore+"");

            answerlists.add(answerlist);
        }






        return answerlists;
    }

    @Override
    public List<Studentanswer> getNeedCorrectByNameId(String nameid) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameid);
        studentanswerQueryWrapper.eq("type","3");

        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);
        List<Studentanswer> studentanswerList=new ArrayList<>();
        for (Studentanswer s:studentanswers
             ) {
            if(Float.parseFloat(s.getStudentscore())<0){
                studentanswerList.add(s);
            }
        }

        return studentanswerList;
    }

    @Override
    public Scorelist getScoreByNameIdAndStudentId(Nameidandstudentname nameidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameidandstudentname.getNameid());
        studentanswerQueryWrapper.eq("student_id",nameidandstudentname.getStudentname());

        Scorelist scorelist = new Scorelist();


        int studentanswerdanxuanscore=0;
        int studentanswertotaldanxuanscore=0;
        int studentanswerduoxuanscore=0;
        int studentanswertotalduoxuanscore=0;
        int studentanswerwendascore=0;
        int studentanswertotalwendascore=0;



        List<Studentanswer> studentanswerList = baseMapper.selectList(studentanswerQueryWrapper);

        List<String> delnamelist=new ArrayList<>();

        for (Studentanswer s:studentanswerList
        ) {
            if(s.getType().equals("1")){
                studentanswertotaldanxuanscore+=Integer.parseInt(s.getScore());
                studentanswerdanxuanscore+=Integer.parseInt(s.getStudentscore());
            }else if(s.getType().equals("2")){
                studentanswertotalduoxuanscore+=Integer.parseInt(s.getScore());
                studentanswerduoxuanscore+=Integer.parseInt(s.getStudentscore());
            }else if(s.getType().equals("3")){
                if(Integer.parseInt(s.getStudentscore())!=-1) {
                    studentanswerwendascore += Integer.parseInt(s.getStudentscore());
                }else {
                    return null;
                }
                studentanswertotalwendascore += Integer.parseInt(s.getScore());

            }

        }
        scorelist.setTotalscore(String.valueOf(studentanswertotaldanxuanscore+studentanswertotalduoxuanscore+studentanswertotalwendascore));
        scorelist.setStudenttotalscorel(String.valueOf(studentanswerdanxuanscore+studentanswerduoxuanscore+studentanswerwendascore));
        scorelist.setDanxuanscore(String.valueOf(studentanswertotaldanxuanscore));
        scorelist.setStudentdanxuanscore(String.valueOf(studentanswerdanxuanscore));
        scorelist.setDuoxuanscore(String.valueOf(studentanswertotalduoxuanscore));
        scorelist.setStudentduoxuanscore(String.valueOf(studentanswerduoxuanscore));
        scorelist.setWendascore(String.valueOf(studentanswertotalwendascore));
        scorelist.setStudentwendascore(String.valueOf(studentanswerwendascore));

        return scorelist;
    }

    @Override
    public Staofscore getScoreStaByNameIdAndStudentId(String nameid) {
        List<Nameidandstudentname> nameIdAndStudentIdByNameid = getNameIdAndStudentIdByNameid(nameid);
        Staofscore staofscore = new Staofscore();

        int[] totalsta=new int[10];
        int[] danxuansta=new int[10];
        int[] duoxuansta=new int[10];
        int[] wendasta=new int[10];



        for (Nameidandstudentname n:nameIdAndStudentIdByNameid
             ) {

            Scorelist scorelist = getScoreByNameIdAndStudentId(n);
            System.out.println(scorelist);
            Float totalpercent =Float.parseFloat(scorelist.getStudenttotalscorel())/Float.parseFloat(scorelist.getTotalscore());
            if(0<=totalpercent&&totalpercent<0.1){
                totalsta[0]+=1;
            }else if(0.1<=totalpercent&&totalpercent<0.2){
                totalsta[1]+=1;
            }else if(0.2<=totalpercent&&totalpercent<0.3){
                totalsta[2]+=1;
            }else if(0.3<=totalpercent&&totalpercent<0.4){
                totalsta[3]+=1;
            }else if(0.4<=totalpercent&&totalpercent<0.5){
                totalsta[4]+=1;
            }else if(0.5<=totalpercent&&totalpercent<0.6){
                totalsta[5]+=1;
            }else if(0.6<=totalpercent&&totalpercent<0.7){
                totalsta[6]+=1;
            }else if(0.7<=totalpercent&&totalpercent<0.8){
                totalsta[7]+=1;
            }else if(0.8<=totalpercent&&totalpercent<0.9){
                totalsta[8]+=1;
            }else if(0.9<=totalpercent&&totalpercent<=1){
                totalsta[9]+=1;
            }

            Float danxuanpercent =Float.parseFloat(scorelist.getStudentdanxuanscore())/Float.parseFloat(scorelist.getDanxuanscore());
            if(0<=danxuanpercent&&danxuanpercent<0.1){
                danxuansta[0]+=1;
            }else if(0.1<=danxuanpercent&&danxuanpercent<0.2){
                danxuansta[1]+=1;
            }else if(0.2<=danxuanpercent&&danxuanpercent<0.3){
                danxuansta[2]+=1;
            }else if(0.3<=danxuanpercent&&danxuanpercent<0.4){
                danxuansta[3]+=1;
            }else if(0.4<=danxuanpercent&&danxuanpercent<0.5){
                danxuansta[4]+=1;
            }else if(0.5<=danxuanpercent&&danxuanpercent<0.6){
                danxuansta[5]+=1;
            }else if(0.6<=danxuanpercent&&danxuanpercent<0.7){
                danxuansta[6]+=1;
            }else if(0.7<=danxuanpercent&&danxuanpercent<0.8){
                danxuansta[7]+=1;
            }else if(0.8<=danxuanpercent&&danxuanpercent<0.9){
                danxuansta[8]+=1;
            }else if(0.9<=danxuanpercent&&danxuanpercent<=1){
                danxuansta[9]+=1;
            }else {
                danxuansta[0]+=1;
            }

            Float duoxuanpercent =Float.parseFloat(scorelist.getStudentduoxuanscore())/Float.parseFloat(scorelist.getDuoxuanscore());
            if(0<=duoxuanpercent&&duoxuanpercent<0.1){
                duoxuansta[0]+=1;
            }else if(0.1<=duoxuanpercent&&duoxuanpercent<0.2){
                duoxuansta[1]+=1;
            }else if(0.2<=duoxuanpercent&&duoxuanpercent<0.3){
                duoxuansta[2]+=1;
            }else if(0.3<=duoxuanpercent&&duoxuanpercent<0.4){
                duoxuansta[3]+=1;
            }else if(0.4<=duoxuanpercent&&duoxuanpercent<0.5){
                duoxuansta[4]+=1;
            }else if(0.5<=duoxuanpercent&&duoxuanpercent<0.6){
                duoxuansta[5]+=1;
            }else if(0.6<=duoxuanpercent&&duoxuanpercent<0.7){
                duoxuansta[6]+=1;
            }else if(0.7<=duoxuanpercent&&duoxuanpercent<0.8){
                duoxuansta[7]+=1;
            }else if(0.8<=duoxuanpercent&&duoxuanpercent<0.9){
                duoxuansta[8]+=1;
            }else if(0.9<=duoxuanpercent&&duoxuanpercent<=1){
                duoxuansta[9]+=1;
            }else {
                duoxuansta[0]+=1;
            }

            Float wendapercent =Float.parseFloat(scorelist.getStudentwendascore())/Float.parseFloat(scorelist.getWendascore());
            if(0<=wendapercent&&wendapercent<0.1){
                wendasta[0]+=1;
            }else if(0.1<=wendapercent&&wendapercent<0.2){
                wendasta[1]+=1;
            }else if(0.2<=wendapercent&&wendapercent<0.3){
                wendasta[2]+=1;
            }else if(0.3<=wendapercent&&wendapercent<0.4){
                wendasta[3]+=1;
            }else if(0.4<=wendapercent&&wendapercent<0.5){
                wendasta[4]+=1;
            }else if(0.5<=wendapercent&&wendapercent<0.6){
                wendasta[5]+=1;
            }else if(0.6<=wendapercent&&wendapercent<0.7){
                wendasta[6]+=1;
            }else if(0.7<=wendapercent&&wendapercent<0.8){
                wendasta[7]+=1;
            }else if(0.8<=wendapercent&&wendapercent<0.9){
                wendasta[8]+=1;
            }else if(0.9<=wendapercent&&wendapercent<=1){
                wendasta[9]+=1;
            }else {
                wendasta[0]+=1;
            }

        }



        staofscore.setTotalscoresta(totalsta);
        staofscore.setDanxuanscoresta(danxuansta);
        staofscore.setDuoxuanscoresta(duoxuansta);
        staofscore.setWendascoresta(wendasta);




        return staofscore;

    }

    @Override
    public List<Nameidandstudentname> getNameIdAndStudentIdByNameid(String nameid) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameid);

        List<Studentanswer> studentanswerList = baseMapper.selectList(studentanswerQueryWrapper);



        HashSet<String> hashSet=new HashSet<>();


        for (Studentanswer s:studentanswerList
             ) {
            hashSet.add(s.getStudentId());
        }

        List<Nameidandstudentname> nameidandstudentnameList=new ArrayList<>();

        for (String s:hashSet
             ) {
            Nameidandstudentname nameidandstudentname = new Nameidandstudentname();
            nameidandstudentname.setStudentname(s);
            nameidandstudentname.setNameid(nameid);

            nameidandstudentnameList.add(nameidandstudentname);
        }

        return nameidandstudentnameList;

    }




    @Override
    public List<Studentanswer> getByNameIdAndStudentName(Nameidandstudentname nameidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameidandstudentname.getNameid());
        studentanswerQueryWrapper.eq("student_id",nameidandstudentname.getStudentname());


        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);

        return studentanswers;
    }


    @Override
    public List<Studentanswer> getWenDaByNameIdAndStudentName(Nameidandstudentname nameidandstudentname) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameidandstudentname.getNameid());
        studentanswerQueryWrapper.eq("student_id",nameidandstudentname.getStudentname());
        studentanswerQueryWrapper.eq("type","3");


        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);

        return studentanswers;
    }

    @Override
    public Studentansweraverage getAverageByNameIdAndStudentName(String nameid) {
        QueryWrapper<Studentanswer> studentanswerQueryWrapper=new QueryWrapper<>();
        studentanswerQueryWrapper.eq("name_id",nameid);


        List<Studentanswer> studentanswers = baseMapper.selectList(studentanswerQueryWrapper);
        Integer totoalcount = baseMapper.selectCount(studentanswerQueryWrapper);

        int totalscore=0;
        int danxuanscore=0;
        int duoxuanscore=0;
        int wendascore=0;
        for (Studentanswer s:studentanswers
             ) {
            if(s.getScore()!=null) {
                totalscore += Integer.parseInt(s.getStudentscore());
            }
            if(s.getScore()!=null&&s.getType()!=null&&s.getType().equals("1")){
                danxuanscore += Integer.parseInt(s.getStudentscore());

            }
            if(s.getScore()!=null&&s.getType()!=null&&s.getType().equals("2")){
                duoxuanscore += Integer.parseInt(s.getStudentscore());

            }
            if(s.getScore()!=null&&s.getType()!=null&&s.getType().equals("3")){
                wendascore += Integer.parseInt(s.getStudentscore());

            }
        }

        QueryWrapper<Studentanswer> studentanswerQueryWrapperDanxuan=new QueryWrapper<>();
        studentanswerQueryWrapperDanxuan.eq("name_id",nameid);
        studentanswerQueryWrapperDanxuan.eq("type","1");

        Integer danxuancount = baseMapper.selectCount(studentanswerQueryWrapperDanxuan);


        QueryWrapper<Studentanswer> studentanswerQueryWrapperDuoxuan=new QueryWrapper<>();
        studentanswerQueryWrapperDuoxuan.eq("name_id",nameid);
        studentanswerQueryWrapperDuoxuan.eq("type","2");

        Integer duoxuancount = baseMapper.selectCount(studentanswerQueryWrapperDuoxuan);



        QueryWrapper<Studentanswer> studentanswerQueryWrapperWenda=new QueryWrapper<>();
        studentanswerQueryWrapperWenda.eq("name_id",nameid);
        studentanswerQueryWrapperWenda.eq("type","3");

        Integer wendacount = baseMapper.selectCount(studentanswerQueryWrapperWenda);


        Studentansweraverage studentansweraverage = new Studentansweraverage();

        float totalaverage;
        float danxuanaverage;
        float duoxuanaverage;
        float wendaaverage;
        if(totoalcount!=0){
            totalaverage=totalscore/totoalcount;
            studentansweraverage.setTotalaverage(totalaverage+"");

        }else {
            studentansweraverage.setTotalaverage("0");
        }
        if(danxuancount!=0){
            danxuanaverage=danxuanscore/danxuancount;
            studentansweraverage.setDanxuanaverage(danxuanaverage+"");

        }else {
            studentansweraverage.setDanxuanaverage("0");
        }
        if(duoxuancount!=0){
            duoxuanaverage=duoxuanscore/duoxuancount;
            studentansweraverage.setDuoxuanaverage(duoxuanaverage+"");

        }else {
            studentansweraverage.setDuoxuanaverage("0");

        }
        if(wendacount!=0){
            wendaaverage=wendascore/wendacount;
            studentansweraverage.setWendaaverage(wendaaverage+"");

        }else {
            studentansweraverage.setWendaaverage("0");
        }




        return studentansweraverage;
    }


}
