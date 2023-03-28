package com.nengdong.member.service;

import com.nengdong.member.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.member.vo.LoginVo;
import com.nengdong.member.vo.RegisterVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);
}
