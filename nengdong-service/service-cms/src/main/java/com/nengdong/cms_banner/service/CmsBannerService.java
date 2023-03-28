package com.nengdong.cms_banner.service;

import com.nengdong.cms_banner.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-25
 */
public interface CmsBannerService extends IService<CmsBanner> {

    List<CmsBanner> getAllBanner();
}
