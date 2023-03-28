package com.nengdong.edu.service;

import com.nengdong.edu.entity.Studentanswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.edu.vo.homeworktesttostudentanswer.Totalform;
import com.nengdong.edu.vo.studentanswer.*;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-19
 */
public interface StudentanswerService extends IService<Studentanswer> {

    List<Studentanswer> getByNameIdAndStudentName(Nameidandstudentname nameidandstudentname);

    List<Studentanswer> getWenDaByNameIdAndStudentName(Nameidandstudentname nameidandstudentname);

    Studentansweraverage getAverageByNameIdAndStudentName(String nameid);

    boolean addStudentAnswer(Totalform totalform);


    boolean getStatusByNameIdAndStudentName(Nameidandstudentname nameidandstudentname);

    Scorelist getScoreByNameIdAndStudentName(Nameidandstudentname nameidandstudentname);

    String getNameIdByNameIdAndStudentName(Courseidandstudentname courseidandstudentname);

    boolean getCorrectStatusByNameId(String nameid);

    List<Studentanswer> getStudentAnswerByNameId(String nameid);

    boolean correctByNameIdandNoandData(Nameidandnoandscore nameidandnoandscore);

    Scorelist getScoreByNameId(String nameid);

    List<Answerlist> getAnswerListByNameIdAndStudentName(Courseidandstudentname courseidandstudentname);

    List<Studentanswer> getNeedCorrectByNameId(String nameid);

    Scorelist getScoreByNameIdAndStudentId(Nameidandstudentname nameidandstudentname);

    Staofscore getScoreStaByNameIdAndStudentId(String nameid);

    List<Nameidandstudentname> getNameIdAndStudentIdByNameid(String nameid);
}
