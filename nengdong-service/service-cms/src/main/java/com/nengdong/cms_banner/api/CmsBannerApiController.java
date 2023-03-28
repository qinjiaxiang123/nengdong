package com.nengdong.cms_banner.api;


import com.nengdong.cms_banner.entity.CmsBanner;
import com.nengdong.cms_banner.service.CmsBannerService;
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

@EnableSwagger2
@Api(description="前台banner展示")
@RestController
@RequestMapping("/cms_banner/cms-banner")
@CrossOrigin
public class CmsBannerApiController {
    @Autowired
    private CmsBannerService cmsBannerService;

    @ApiOperation(value = "查询所有banner信息")
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        List<CmsBanner> bannerList = cmsBannerService.getAllBanner();
        return Result.ok().data("bannerList",bannerList);
    }
}
