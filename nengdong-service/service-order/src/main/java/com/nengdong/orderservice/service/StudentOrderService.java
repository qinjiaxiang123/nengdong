package com.nengdong.orderservice.service;

import com.nengdong.orderservice.entity.StudentOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
public interface StudentOrderService extends IService<StudentOrder> {

    String createOrder(String courseId, String memberId);

    List<String> getStudentByCourseId(String courseid);

    Integer countOrder(String day);

    Map<String, Object> createNative(String orderNo);
}
