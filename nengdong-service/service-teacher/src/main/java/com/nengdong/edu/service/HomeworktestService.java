package com.nengdong.edu.service;

import com.nengdong.edu.entity.Homeworktest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.edu.vo.homeworktest.HomeworkMessage;
import com.nengdong.edu.vo.homeworktest.Homeworkform;
import com.nengdong.edu.vo.homeworktest.Testpaperinfo;
import com.nengdong.edu.vo.homeworktestvo.Nameidandno;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-16
 */
public interface HomeworktestService extends IService<Homeworktest> {

    Boolean saveByType(Homeworkform homeworkform);


    List<Homeworktest> getHomeworkByNameId(String homeworkid);

    List<Testpaperinfo> getTestPaperInfoByCourseId(String courseid);

    String getCourseIdByNameId(String nameid);

    HomeworkMessage getHomeworkMessageById(String homeworkid);

    String getNamebyNameid(String nameid);

    String getQuestionbyNameid(String nameid);

    String getQuestionbyNameidAndNo(Nameidandno nameidandno);


}
