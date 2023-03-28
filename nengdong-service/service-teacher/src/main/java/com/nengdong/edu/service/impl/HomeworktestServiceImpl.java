package com.nengdong.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.nengdong.edu.entity.Homeworktest;
import com.nengdong.edu.mapper.HomeworktestMapper;
import com.nengdong.edu.service.HomeworktestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.edu.vo.homeworktest.*;
import com.nengdong.edu.vo.homeworktestvo.Nameidandno;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-16
 */
@Service
public class HomeworktestServiceImpl extends ServiceImpl<HomeworktestMapper, Homeworktest> implements HomeworktestService {
    @Override
    public Boolean saveByType(Homeworkform homeworkform) {
        //提起前端作业信息
        List<Danxuandomain> danxuandomain = homeworkform.getDanxuandomain();
        List<Duoxuandomain> duoxuandomain = homeworkform.getDuoxuandomain();
        List<Wendadomain> wendadomain = homeworkform.getWendadomain();
        String message = homeworkform.getMessage();
        String uuid =UUID.randomUUID().toString();
        for (int i = 0; i < danxuandomain.size(); i++) {
            Homeworktest homeworktest = new Homeworktest();
            homeworktest.setAnswer(danxuandomain.get(i).getAnswer());
            homeworktest.setCourseid(homeworkform.getCourseid());
            homeworktest.setName(message);
            homeworktest.setQuestion(danxuandomain.get(i).getQuestion());
            homeworktest.setNo((i+1) + "");
            homeworktest.setScore(danxuandomain.get(i).getScore());
            homeworktest.setType("1");
            homeworktest.setValuea(danxuandomain.get(i).getValuea());
            homeworktest.setValueb(danxuandomain.get(i).getValueb());
            homeworktest.setValuec(danxuandomain.get(i).getValuec());
            homeworktest.setValued(danxuandomain.get(i).getValued());
            homeworktest.setNameid(uuid);
            baseMapper.insert(homeworktest);
        }
        for (int i = 0; i < duoxuandomain.size(); i++) {
            Homeworktest homeworktest = new Homeworktest();
            List answer = duoxuandomain.get(i).getAnswer();
            Collections.sort(answer);
            String s = new String();
            for (int j = 0; j < answer.size(); j++) {
                s += answer.get(j);
            }
            homeworktest.setAnswer(s);
            homeworktest.setCourseid(homeworkform.getCourseid());
            homeworktest.setName(message);
            homeworktest.setQuestion(duoxuandomain.get(i).getQuestion());
            homeworktest.setScore(duoxuandomain.get(i).getScore());
            homeworktest.setType("2");
            homeworktest.setNo((i+1) + "");
            homeworktest.setValuea(duoxuandomain.get(i).getValuea());
            homeworktest.setValueb(duoxuandomain.get(i).getValueb());
            homeworktest.setValuec(duoxuandomain.get(i).getValuec());
            homeworktest.setValued(duoxuandomain.get(i).getValued());
            homeworktest.setNameid(uuid);
            baseMapper.insert(homeworktest);
        }
        for (int i = 0; i < wendadomain.size(); i++) {
            Homeworktest homeworktest = new Homeworktest();
            homeworktest.setAnswer(wendadomain.get(i).getAnswer());
            homeworktest.setQuestion(wendadomain.get(i).getQuestion());
            homeworktest.setCourseid(homeworkform.getCourseid());
            homeworktest.setName(message);
            homeworktest.setNo((i+1) + "");
            homeworktest.setScore(wendadomain.get(i).getScore());
            homeworktest.setType("3");
            homeworktest.setNameid(uuid);
            baseMapper.insert(homeworktest);
        }
        return true;
    }

    @Override
    public List<Homeworktest> getHomeworkByNameId(String homeworkid) {
        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper();
        homeworktestQueryWrapper.eq("nameid",homeworkid);
        List<Homeworktest> homeworktests = baseMapper.selectList(homeworktestQueryWrapper);
       return homeworktests;
    }

    @Override
    public List<Testpaperinfo> getTestPaperInfoByCourseId(String courseid) {



        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper();
        homeworktestQueryWrapper.eq("courseid",courseid);
        List<Homeworktest> homeworktestList = baseMapper.selectList(homeworktestQueryWrapper);
        int totalscore=0;

        List<Testpaperinfo> testpaperinfoList =new ArrayList();


        HashSet<String> hashSet=new HashSet();
        for (int i = 0; i <homeworktestList.size() ; i++) {
            hashSet.add(homeworktestList.get(i).getNameid());
        }


        for (String nameid:hashSet
             ) {
            QueryWrapper<Homeworktest> homeworktestQueryWrapperdanxuan=new QueryWrapper();
            homeworktestQueryWrapperdanxuan.eq("nameid",nameid);
            homeworktestQueryWrapperdanxuan.eq("type",1+"");
            Integer danxuannumber = baseMapper.selectCount(homeworktestQueryWrapperdanxuan);

            QueryWrapper<Homeworktest> homeworktestQueryWrapperduoxuan=new QueryWrapper();
            homeworktestQueryWrapperduoxuan.eq("nameid",nameid);
            homeworktestQueryWrapperduoxuan.eq("type",2+"");
            Integer duoxuannumber = baseMapper.selectCount(homeworktestQueryWrapperduoxuan);

            QueryWrapper<Homeworktest> homeworktestQueryWrapperwenda=new QueryWrapper();
            homeworktestQueryWrapperwenda.eq("nameid",nameid);
            homeworktestQueryWrapperwenda.eq("type",1+"");
            Integer wendanumber = baseMapper.selectCount(homeworktestQueryWrapperwenda);


            QueryWrapper<Homeworktest> homeworktestQueryWrapperscore=new QueryWrapper();
            homeworktestQueryWrapperscore.eq("nameid",nameid);
            List<Homeworktest> homeworktestListscore = baseMapper.selectList(homeworktestQueryWrapperscore);

            for (int i = 0; i < homeworktestListscore.size(); i++) {

                int score = Integer.parseInt(homeworktestListscore.get(i).getScore());
                totalscore+=score;

            }


            Testpaperinfo testpaperinfo = new Testpaperinfo();
            testpaperinfo.setNameid(nameid);
            testpaperinfo.setName(homeworktestListscore.get(0).getName());
            testpaperinfo.setDanxuancount(danxuannumber+"");
            testpaperinfo.setDuoxuancount(duoxuannumber+"");
            testpaperinfo.setWendacount(wendanumber+"");
            testpaperinfo.setTotalscore(totalscore+"");

            testpaperinfoList.add(testpaperinfo);


        }





        return testpaperinfoList;
    }

    @Override
    public String getCourseIdByNameId(String nameid) {
        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper<>();
        homeworktestQueryWrapper.eq("nameid",nameid);

        List<Homeworktest> homeworktests = baseMapper.selectList(homeworktestQueryWrapper);
        return homeworktests.get(0).getCourseid();
    }

    @Override
    public HomeworkMessage getHomeworkMessageById(String homeworkid) {
        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper<>();
        homeworktestQueryWrapper.eq("nameid",homeworkid);

        List<Homeworktest> homeworktests = baseMapper.selectList(homeworktestQueryWrapper);



        int danxuan=0;
        int duoxuan=0;
        int wenda=0;
        int total=0;

        for (Homeworktest h:homeworktests
             ) {
            if(h.getType().equals("1")){
                danxuan+=Integer.parseInt(h.getScore());
            }else if(h.getType().equals("2")){
                duoxuan+=Integer.parseInt(h.getScore());
            }
            else if(h.getType().equals("3")){
                wenda+=Integer.parseInt(h.getScore());
            }
        }


        total=danxuan+duoxuan+wenda;

        HomeworkMessage homeworkMessage = new HomeworkMessage();
        homeworkMessage.setTotaldanxuanscore(String.valueOf(danxuan));
        homeworkMessage.setTotalduoxuanscore(String.valueOf(duoxuan));
        homeworkMessage.setTotalwendascore(String.valueOf(wenda));
        homeworkMessage.setTotalscore(String.valueOf(total));

        return homeworkMessage;
    }

    @Override
    public String getNamebyNameid(String nameid) {
        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper<>();
        homeworktestQueryWrapper.eq("nameid",nameid);

        List<Homeworktest> homeworktests = baseMapper.selectList(homeworktestQueryWrapper);

        return homeworktests.get(0).getName();
    }

    @Override
    public String getQuestionbyNameid(String nameid) {
        return null;
    }

    @Override
    public String getQuestionbyNameidAndNo(Nameidandno nameidandno) {
        QueryWrapper<Homeworktest> homeworktestQueryWrapper=new QueryWrapper<>();
        homeworktestQueryWrapper.eq("nameid",nameidandno.getNameid());
        homeworktestQueryWrapper.eq("no",nameidandno.getNo());
        homeworktestQueryWrapper.eq("type","3");

        Homeworktest homeworktest = baseMapper.selectOne(homeworktestQueryWrapper);
        return homeworktest.getQuestion();
    }




}
