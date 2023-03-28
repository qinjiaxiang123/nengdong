package com.nengdong.cms_banner.service.impl;

import com.nengdong.cms_banner.entity.CmsBanner;
import com.nengdong.cms_banner.mapper.CmsBannerMapper;
import com.nengdong.cms_banner.service.CmsBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-25
 */
@Service
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerMapper, CmsBanner> implements CmsBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CmsBanner> getAllBanner() {
        List<CmsBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}
