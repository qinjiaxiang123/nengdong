package com.nengdong.cms_notice.service;

import com.nengdong.cms_notice.entity.CmsNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-11
 */
public interface CmsNoticeService extends IService<CmsNotice> {

    List<CmsNotice> getAllNotice();
}
