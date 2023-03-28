package com.nengdong.cms_notice.controller;


import com.nengdong.cms_notice.entity.CmsNotice;
import com.nengdong.cms_notice.service.CmsNoticeService;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-11
 */
@EnableSwagger2
@Api(description="前台notice展示")
@RestController
@RequestMapping("/cms_banner/cms-notice")
@CrossOrigin
public class CmsNoticeController {
    @Autowired
    private CmsNoticeService cmsNoticeService;

    @ApiOperation(value = "查询所有公告信息")
    @GetMapping("getAllNotice")
    public Result getAllBanner(){
        List<CmsNotice> bannerList = cmsNoticeService.getAllNotice();
        return Result.ok().data("cmsNoticeService",bannerList);
    }
}

