package com.nengdong.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.utils.vo.CourseWebVoForOrder;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.orderservice.client.EduClient;
import com.nengdong.orderservice.client.UcenterClient;
import com.nengdong.orderservice.entity.StudentOrder;
import com.nengdong.orderservice.mapper.StudentOrderMapper;
import com.nengdong.orderservice.service.StudentOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.orderservice.utils.OrderNoUtil;
import com.nengdong.utils.vo.UcenterMemberForOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
@Service
public class StudentOrderServiceImpl extends ServiceImpl<StudentOrderMapper, StudentOrder> implements StudentOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id、用户id创建订单
    @Override
    public String createOrder(String courseId, String memberId) {
        //1 跨模块获取课程信息

        CourseWebVoForOrder courseInfoForOrder = eduClient.getCourseInfoForOrder(courseId);
        //1.1进行校验
        if(courseInfoForOrder==null){
            new NengdongException(20001,"获取课程信息失败");
        }
        //2跨模块获取用户信息
        System.out.println("memberId"+memberId);
        UcenterMemberForOrder ucenterForOrder = ucenterClient.getUcenterForOrder(memberId);
        //2.2进行校验
        if(ucenterForOrder==null){
            new NengdongException(20001,"获取用户信息失败");
        }
        //3生成订单编号
        String orderNo = OrderNoUtil.getOrderNo();
        //4封装数据，存入数据库
        StudentOrder order = new StudentOrder();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoForOrder.getTitle());
        order.setCourseCover(courseInfoForOrder.getCover());
        order.setTeacherName(courseInfoForOrder.getTeacherName());
        order.setTotalFee(courseInfoForOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterForOrder.getMobile());
        order.setNickname(ucenterForOrder.getNickname());
        order.setStatus(1);
        order.setPayType(1);//1：微信
        baseMapper.insert(order);

        return orderNo;
    }

    @Override
    public List<String> getStudentByCourseId(String courseid) {
        QueryWrapper<StudentOrder> studentOrderQueryWrapper=new QueryWrapper();
        studentOrderQueryWrapper.eq("course_id",courseid);
        List<String> list=new ArrayList();

        List<StudentOrder> studentOrders = baseMapper.selectList(studentOrderQueryWrapper);

        for (int i = 0; i <studentOrders.size(); i++) {
            list.add(studentOrders.get(i).getMemberId());
        }

        return list;

    }

    @Override
    public Integer countOrder(String day) {
        Integer count = baseMapper.countOrder(day);
        System.out.println(count);
        return count;
    }

    @Override
    public Map<String, Object> createNative(String orderNo) {
        QueryWrapper<StudentOrder> studentOrderQueryWrapper=new QueryWrapper();
        studentOrderQueryWrapper.eq("order_no",orderNo);
        StudentOrder studentOrder = baseMapper.selectOne(studentOrderQueryWrapper);

        HashMap<String, Object> m = new HashMap<>();

        m.put("course_id",studentOrder.getCourseId());
        m.put("total_fee",studentOrder.getTotalFee());

        return m;
    }
}
