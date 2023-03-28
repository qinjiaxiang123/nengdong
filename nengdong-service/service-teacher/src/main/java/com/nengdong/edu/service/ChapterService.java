package com.nengdong.edu.service;

import com.nengdong.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nengdong.edu.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoById(String courseId);
}
