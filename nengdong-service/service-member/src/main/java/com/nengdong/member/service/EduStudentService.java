package com.nengdong.member.service;

import com.nengdong.member.entity.EduStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.member.vo.LoginVo;
import com.nengdong.member.vo.RegisterVo;

import java.awt.*;
import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
public interface EduStudentService extends IService<EduStudent> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    Integer countRegister(String day);

    void updateStudent(EduStudent eduStudent);

    boolean checkPassword(EduStudent eduStudent);


    List<EduStudent> getStudentUcenterByList(List list);
}
