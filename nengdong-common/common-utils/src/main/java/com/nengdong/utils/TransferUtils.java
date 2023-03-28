package com.nengdong.utils;

public class TransferUtils {
    public static String numbertostring(String number){
        if(number.equals("1")){
            return new String("A");
        }else if(number.equals("2")){
            return new String("B");
        }else if(number.equals("3")) {
            return new String("C");
        }else if(number.equals("4")){
            return new String("D");
        }else {
            return null;
        }
    }

    public static String numberlisttostringlist(String number){
        char[] chars = number.toCharArray();
        String s=new String();
        for (int i = 0; i <chars.length ; i++) {
            if(String.valueOf(chars[i]).equals("1")){
                s+="A";
            }else if(String.valueOf(chars[i]).equals("2")){
                s+="B";
            }else if(String.valueOf(chars[i]).equals("3")){
                s+="C";
            }else if(String.valueOf(chars[i]).equals("4")){
                s+="D";
            }
        }
        return s;
    }
}
