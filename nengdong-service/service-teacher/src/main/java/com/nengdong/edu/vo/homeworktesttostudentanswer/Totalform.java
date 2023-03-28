package com.nengdong.edu.vo.homeworktesttostudentanswer;


import lombok.Data;

import java.util.List;

@Data
public class Totalform {
    private List<Studentanswerdanxuan> danxuan;
    private List<Studentanswerduoxuan> duoxuan;
    private List<Studentanserwenda> wenda;
    private String nameid;
    private String courseid;
    private String studentid;

}
