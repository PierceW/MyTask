package com.alex.hdfs.test;

/**
 * @ClassName TestDTO
 * @Description todo
 * @Author AlexWang
 * @Date 2019/6/13 0013 15:40
 * @Version 1.0
 **/

public class TestDTO {
    private String val;
    private String name;
    private Integer age;
    private Integer score;
    private Integer studentIds;
    private Integer gateNumber;
    private Integer answerNumber;
    private Integer sort;

    public TestDTO() {
        super();
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Integer studentIds) {
        this.studentIds = studentIds;
    }

    public Integer getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(Integer gateNumber) {
        this.gateNumber = gateNumber;
    }

    public Integer getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(Integer answerNumber) {
        this.answerNumber = answerNumber;
    }

    public String getGateAnswer() {
        return gateNumber + "_" + answerNumber;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
