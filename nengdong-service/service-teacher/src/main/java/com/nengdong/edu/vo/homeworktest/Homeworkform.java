package com.nengdong.edu.vo.homeworktest;

import lombok.Data;

import java.util.List;

@Data
public class Homeworkform {
    private List<Danxuandomain> danxuandomain;
    private List<Duoxuandomain> duoxuandomain;
    private List<Wendadomain> wendadomain;
    private String message;
    private String courseid;

}
