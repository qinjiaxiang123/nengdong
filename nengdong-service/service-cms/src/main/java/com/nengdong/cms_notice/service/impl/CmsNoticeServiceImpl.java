package com.nengdong.cms_notice.service.impl;

import com.nengdong.cms_banner.entity.CmsBanner;
import com.nengdong.cms_notice.entity.CmsNotice;
import com.nengdong.cms_notice.mapper.CmsNoticeMapper;
import com.nengdong.cms_notice.service.CmsNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-11
 */
@Service
public class CmsNoticeServiceImpl extends ServiceImpl<CmsNoticeMapper, CmsNotice> implements CmsNoticeService {

    @Override
    public List<CmsNotice> getAllNotice() {
        List<CmsNotice> NoticeList = baseMapper.selectList(null);
        return NoticeList;

    }
}
