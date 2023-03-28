package com.nengdong.edu.service;

import com.nengdong.edu.entity.Videoandfile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.edu.vo.videoandfile.VideoVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
public interface VideoandfileService extends IService<Videoandfile> {

    void saveVideoInfo(VideoVo videoVo);

    VideoVo getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoVo videoVo);

    boolean removeVideoById(String id);
}
