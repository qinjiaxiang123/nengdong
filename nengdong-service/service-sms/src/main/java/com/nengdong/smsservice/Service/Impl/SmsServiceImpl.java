package com.nengdong.smsservice.Service.Impl;


import com.nengdong.smsservice.Service.SmsService;
import com.nengdong.utils.SMSUtils;
import org.springframework.stereotype.Service;

/**
 * @author XuKe   Email:xuke598654158@126.com
 * @Description
 * @date 2021/3/16  18:42
 */
@Service
class SmsServiceImpl implements SmsService {
    /**
     * 发送短信：山东鼎信
     */
    @Override
    public boolean send(String phone, String code) {
        try {
            SMSUtils.sendShortMessage(phone,code);
            System.out.println("phone = " + phone);
            System.out.println("code = " + code);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
