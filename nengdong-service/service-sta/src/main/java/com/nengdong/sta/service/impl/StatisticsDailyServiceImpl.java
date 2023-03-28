package com.nengdong.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.sta.client.EduClient;
import com.nengdong.sta.client.OrderClient;
import com.nengdong.sta.client.UcenterClient;
import com.nengdong.sta.entity.StatisticsDaily;
import com.nengdong.sta.mapper.StatisticsDailyMapper;
import com.nengdong.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.utils.Result;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-28
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduClient eduClient;
    @Autowired
    private OrderClient orderClient;

    //生成统计数据
    @Override
    public void createStaDaily(String day) {
        System.out.println(day);
        //1删除数据
        QueryWrapper<StatisticsDaily> queryWrapperdel = new QueryWrapper<>();
        queryWrapperdel.eq("date_calculated",day);
        baseMapper.delete(queryWrapperdel);
        //2统计数据
        Result r = ucenterClient.countRegister(day);
        Result r1 = eduClient.countTeacher(day);
        Result r2 = eduClient.countCourse(day);
        Result r3 = orderClient.countOrder(day);
        Integer registerNum = (Integer)r.getData().get("countRegister");
        Integer teacherNum = (Integer)r1.getData().get("countTeacher");
        Integer courseNum =(Integer)r2.getData().get("countCourse");
        Integer orderNum = (Integer)r3.getData().get("countOrder");
        //3封装数据，入库
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterStudentNum(registerNum);//学生注册人数 到时候要改
        daily.setRegisterTeacherNum(teacherNum);
        daily.setCourseNum(courseNum);
        daily.setOrderNum(orderNum);
        daily.setDateCalculated(day);
        baseMapper.insert(daily);

    }

    //查询统计数据
    @Override
    public Map<String, Object> getStaDaily(String type, String begin, String end) {
        //1查询数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        queryWrapper.select("date_calculated",type);
        List<StatisticsDaily> dailieList = baseMapper.selectList(queryWrapper);
        //2 遍历查询结果
        Map<String, Object> staDailyMap = new HashMap<>();
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> dataList =  new ArrayList<>();

        System.out.println(type);

        for (int i = 0; i < dailieList.size(); i++) {
            StatisticsDaily daily = dailieList.get(i);
            //3 封装X轴数据
            dateCalculatedList.add(daily.getDateCalculated());
            //4 封装Y轴数据
            switch (type){
                case "register_student_num":
                    dataList.add(daily.getRegisterStudentNum());//学生注册人数 到时候要改
                    break;
                case "register_teacher_num":
                    dataList.add(daily.getRegisterTeacherNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                case "order_num":
                    dataList.add(daily.getOrderNum());
                    break;
                default:
                    break;
            }
        }
        staDailyMap.put("dateCalculatedList",dateCalculatedList);
        staDailyMap.put("dataList",dataList);


        return staDailyMap;
    }
}
