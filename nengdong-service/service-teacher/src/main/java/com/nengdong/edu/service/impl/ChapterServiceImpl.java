package com.nengdong.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.entity.Chapter;
import com.nengdong.edu.entity.Videoandfile;
import com.nengdong.edu.mapper.ChapterMapper;
import com.nengdong.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nengdong.edu.service.VideoandfileService;
import com.nengdong.edu.vo.chapter.ChapterVo;
import com.nengdong.edu.vo.videoandfile.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoandfileService videoService;

    //根据课程id查询章节、小节信息
    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        //1根据courseId查询章节集合信息
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<Chapter> chapterList = baseMapper.selectList(chapterWrapper);
        //2根据courseId查询小节集合信息
        QueryWrapper<Videoandfile> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<Videoandfile> videoList = videoService.list(videoWrapper);
        //3遍历章节信息进行封装
        List<ChapterVo> chapterVideoList = new ArrayList<>();
        for (int i = 0; i <chapterList.size() ; i++) {
            Chapter eduChapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVideoList.add(chapterVo);
            //4遍历和此章节关联小节信息进行封装
            List<VideoVo> videoVos = new ArrayList<>();
            for (int m = 0; m < videoList.size(); m++) {
                Videoandfile eduVideo = videoList.get(m);
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
                chapterVo.setChildren(videoVos);
            }

        }

        return chapterVideoList;
    }

}
