package com.nengdong.edu.vo.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nengdong.edu.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author testjava
 * @since 2022-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_comment")
@ApiModel(value="Comment对象", description="评论")
public class Commentvo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "评论数量")
    private String count;

    @ApiModelProperty(value = "评论父级ID")
    private String commentParentId;

    @ApiModelProperty(value = "会员昵称")
    private String name;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "回复内容")
    private List<Comment> reply;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;


}
