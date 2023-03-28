package com.nengdong.smsservice.Service;

/**
 * @author XuKe   Email:xuke598654158@126.com
 * @Description
 * @date 2021/3/16  18:42
 */
public interface SmsService {
    //boolean send(String phone, String templateCode, Map<String, Object> param);阿里云
    boolean send(String phone, String code);//山东鼎信
}
