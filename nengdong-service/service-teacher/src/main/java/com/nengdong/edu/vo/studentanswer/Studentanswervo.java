package com.nengdong.edu.vo.studentanswer;


import com.nengdong.edu.vo.studentanswer.timu.Studentanswerdanxuanvo;
import com.nengdong.edu.vo.studentanswer.timu.Studentanswerduoxuanvo;
import com.nengdong.edu.vo.studentanswer.timu.Studentanswerwendavo;
import lombok.Data;

import java.util.List;

@Data
public class Studentanswervo {
    private List<Studentanswerdanxuanvo> studentanswerdanxuanvoList;
    private List<Studentanswerduoxuanvo> studentanswerduoxuanList;
    private List<Studentanswerwendavo> studentanswerwendavoList;
    private String title;

}
