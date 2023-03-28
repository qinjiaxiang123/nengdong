package com.nengdong.edu.vo.homeworktesttostudentanswer;

import lombok.Data;

import java.util.List;

@Data
public class Studentanswerduoxuan {
    private String question;
    private String score;
    private String valuea;
    private String valueb;
    private String valuec;
    private String valued;
    private List answer;
    private String no;
    private List studentanswer;
}
