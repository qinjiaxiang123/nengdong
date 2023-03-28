package com.nengdong.orderservice.mapper;

import com.nengdong.orderservice.entity.StudentOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
public interface StudentOrderMapper extends BaseMapper<StudentOrder> {

    Integer countOrder(String day);
}
