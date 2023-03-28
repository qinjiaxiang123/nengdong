package com.nengdong.edu.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R delVideo(String videoId) {
        return R.error().message("删除失败");
    }



    @Override
    public R delVideo(List<String> videoIdList) {
        return R.error().message("删除失败");
    }
}
