package com.nengdong.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nengdong.edu.entity.Comment;
import com.nengdong.edu.entity.Course;
import com.nengdong.edu.service.CommentService;
import com.nengdong.edu.vo.comment.Commentvo;
import com.nengdong.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-11
 */
@Api(description="评论管理")
@RestController
@RequestMapping("/edu/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation(value = "查询所有评论信息")
    @GetMapping("getCommentInfo")
//TODO 实现带条件、带分页查询
    public Result getCourseInfo() {
        QueryWrapper<Comment> commentQueryWrapperone = new QueryWrapper<>();
        commentQueryWrapperone.eq("comment_parent_id", 0);
        //commentQueryWrapperone.eq("course_id",courseid);
        List<Comment> list = commentService.list(commentQueryWrapperone);



        List<Commentvo> list1 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Commentvo vo = new Commentvo();
            Comment c = list.get(i);
            vo.setAvatar(c.getAvatar());
            vo.setCommentParentId(c.getCommentParentId());
            vo.setContent(c.getContent());
            vo.setCourseId(c.getCourseId());
            vo.setGmtCreate(c.getGmtCreate());
            vo.setId(c.getId());
            vo.setMemberId(c.getMemberId());
            vo.setName(c.getName());

            String commentId = c.getId();

            QueryWrapper<Comment> commentQueryWrappertwo = new QueryWrapper<>();
            commentQueryWrappertwo.eq("comment_parent_id", commentId);
            List<Comment> listtwo = commentService.list(commentQueryWrappertwo);

            vo.setReply(listtwo);

            list1.add(vo);
        }

        return Result.ok().data("list", list1);
    }

    @ApiOperation(value = "根据id查询评论信息")
    @GetMapping("getCommentInfoById/{courseid}")
    public Result getCommentInfoById(@PathVariable String courseid) {
        QueryWrapper<Comment> commentQueryWrapperone = new QueryWrapper<>();
        QueryWrapper<Comment> commentQueryWrapperonecount = new QueryWrapper<>();

        commentQueryWrapperone.eq("comment_parent_id", 0);
        commentQueryWrapperone.eq("course_id", courseid);
        commentQueryWrapperone.orderByDesc("gmt_create");

        commentQueryWrapperonecount.eq("course_id", courseid);
        //commentQueryWrapperone.eq("course_id",courseid);
        List<Comment> list = commentService.list(commentQueryWrapperone);

        String count = commentService.count(commentQueryWrapperonecount)+"";
        List<Commentvo> list1 = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            Commentvo vo = new Commentvo();
            Comment c = list.get(i);
            vo.setAvatar(c.getAvatar());
            vo.setCommentParentId(c.getCommentParentId());
            vo.setContent(c.getContent());
            vo.setCourseId(c.getCourseId());
            vo.setGmtCreate(c.getGmtCreate());
            vo.setId(c.getId());
            vo.setMemberId(c.getMemberId());
            vo.setName(c.getName());

            String commentId = c.getId();

            QueryWrapper<Comment> commentQueryWrappertwo = new QueryWrapper<>();
            commentQueryWrappertwo.eq("comment_parent_id", commentId);
            List<Comment> listtwo = commentService.list(commentQueryWrappertwo);

            vo.setReply(listtwo);

            list1.add(vo);

        }
        return Result.ok().data("list", list1).data("count",count);
    }

    @ApiOperation(value = "添加评论信息")
    @PostMapping("addCommentInfo")
    public Result addCommentInfo(@RequestBody Comment comment){
        boolean save = commentService.save(comment);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

