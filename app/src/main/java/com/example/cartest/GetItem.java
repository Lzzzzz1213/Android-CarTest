package com.example.cartest;

/***
 * @author CodeLee
 * @data 2020年6月12日
 * @description 返回所选答案
 */
public class GetItem {
    private final String item1 = "1";
    private final String item2 = "2";
    private final String item3 = "3";
    private final String item4 = "4";
    private final String item5 = "7";
    private final String item6 = "8";
    private final String item7 = "9";
    private final String item8 = "10";
    private final String item9 = "11";
    private final String item10 = "12";
    private final String item11 = "13";
    private final String item12 = "14";
    private final String item13 = "15";
    private final String item14 = "16";
    private final String item15 = "17";

    public String returnAnswer(String answer) {
        if (answer.equals(item1)) {
            return "A";
        } else if (answer.equals(item2)) {
            return "B";
        } else if (answer.equals(item3)) {
            return "C";
        } else if (answer.equals(item4)) {
            return "D";
        } else if (answer.equals(item5)) {
            return "AB";
        } else if (answer.equals(item6)) {
            return "AC";
        } else if (answer.equals(item7)) {
            return "AD";
        } else if (answer.equals(item8)) {
            return "BC";
        } else if (answer.equals(item9)) {
            return "BD";
        } else if (answer.equals(item10)) {
            return "CD";
        } else if (answer.equals(item11)) {
            return "ABC";
        } else if (answer.equals(item12)) {
            return "ABD";
        } else if (answer.equals(item13)) {
            return "ACD";
        } else if (answer.equals(item14)) {
            return "BCD";
        } else if (answer.equals(item15)) {
            return "ABCD";
        } else {
            return "error";
        }

    }

}
