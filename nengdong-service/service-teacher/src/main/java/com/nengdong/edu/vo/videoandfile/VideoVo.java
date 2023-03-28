package com.nengdong.edu.vo.videoandfile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo {

    @ApiModelProperty(value = "视频或课件ID")
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;


    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "点击或播放次数")
    private Long playCount;

    @ApiModelProperty(value = "是否可以试听：0收费 1免费")
    private Boolean isFree;

    @ApiModelProperty(value = "文件还是视频：0文件 1视频")
    private Boolean flieorvideo;


    @ApiModelProperty(value = "视频源文件大小（字节）")
    private Long size;





}
