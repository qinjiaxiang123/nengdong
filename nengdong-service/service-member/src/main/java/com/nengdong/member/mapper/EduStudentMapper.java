package com.nengdong.member.mapper;

import com.nengdong.member.entity.EduStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-29
 */
public interface EduStudentMapper extends BaseMapper<EduStudent> {

    Integer countRegister(String day);
}
