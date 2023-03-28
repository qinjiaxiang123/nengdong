package com.nengdong.edu.service.impl;

import com.nengdong.edu.entity.Videoandfile;
import com.nengdong.edu.manage.NengdongException;
import com.nengdong.edu.mapper.VideoandfileMapper;
import com.nengdong.edu.service.VideoandfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.edu.vo.videoandfile.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Service
public class VideoandfileServiceImpl extends ServiceImpl<VideoandfileMapper, Videoandfile> implements VideoandfileService {
    @Override
    public void saveVideoInfo(VideoVo videoVo) {

        Videoandfile eduVideo = new Videoandfile();
        BeanUtils.copyProperties(videoVo, eduVideo);
        boolean result = this.save(eduVideo);

        if(!result){
            throw new NengdongException(20001, "课时信息保存失败");
        }
    }

    @Override
    public VideoVo getVideoInfoFormById(String id) {
        //从video表中取数据
        Videoandfile eduVideo = this.getById(id);
        if(eduVideo == null){
            throw new NengdongException(20001, "数据不存在");
        }

        //创建videoInfoForm对象
        VideoVo videoVo = new VideoVo();
        BeanUtils.copyProperties(eduVideo, videoVo);

        return videoVo;
    }

    @Override
    public void updateVideoInfoById(VideoVo videoVo) {
        //保存课时基本信息
        Videoandfile eduVideo = new Videoandfile();
        BeanUtils.copyProperties(videoVo, eduVideo);
        boolean result = this.updateById(eduVideo);
        if(!result){
            throw new NengdongException(20001, "课时信息保存失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {

        //删除视频资源 TODO

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }
}
