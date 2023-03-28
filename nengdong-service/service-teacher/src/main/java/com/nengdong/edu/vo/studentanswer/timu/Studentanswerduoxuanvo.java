package com.nengdong.edu.vo.studentanswer.timu;

import lombok.Data;

import java.util.List;

@Data
public class Studentanswerduoxuanvo {
    private String question;
    private String score;
    private String valuea;
    private String valueb;
    private String valuec;
    private String valued;
    private List answer;
    private String no;
    private String iscorrect;
    private String studentscore;
    private List studentanswer;
}
