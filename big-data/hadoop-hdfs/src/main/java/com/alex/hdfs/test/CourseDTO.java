package com.alex.hdfs.test;

import java.util.Date;

/**
 * @ClassName CourseDTO
 * @Description 课程信息显示类
 * @Author AlexWang
 * @Date 2019/5/22 0022 15:25
 * @Version 1.0
 **/
public class CourseDTO {
    private Long id;
    private String courseName;
    private Long classId;
    private Long courseId;
    private Long storeId;
    private Integer leftClassHour;
    private Integer iseng;
    private Integer credits;
    private Integer status;
    private Integer totalAmount;
    private Integer haveClasstimes;
    private Integer totalClassHour;
    private Integer eduStage;
    private Date expireTime;
    private Date actionTime;

    public static void checkNull(CourseDTO courseDTO) {
        courseDTO.setId(courseDTO.getId() != null ? courseDTO.getId() : 0L);
        courseDTO.setClassId(courseDTO.getClassId() != null ? courseDTO.getClassId() : 0L);
        courseDTO.setStoreId(courseDTO.getStoreId() != null ? courseDTO.getStoreId() : 0L);
        courseDTO.setLeftClassHour(courseDTO.getLeftClassHour() != null ? courseDTO.getLeftClassHour() : 0);
        courseDTO.setIseng(courseDTO.getIseng() != null ? courseDTO.getIseng() : 0);
        courseDTO.setCredits(courseDTO.getCredits() != null ? courseDTO.getCredits() : 0);
        courseDTO.setStatus(courseDTO.getStatus() != null ? courseDTO.getStatus() : 0);
        courseDTO.setTotalAmount(courseDTO.getTotalAmount() != null ? courseDTO.getTotalAmount() : 0);
        courseDTO.setHaveClasstimes(courseDTO.getHaveClasstimes() != null ? courseDTO.getHaveClasstimes() : 0);
        courseDTO.setTotalClassHour(courseDTO.getTotalClassHour() != null ? courseDTO.getTotalClassHour() : 0);
        courseDTO.setEduStage(courseDTO.getEduStage() != null ? courseDTO.getEduStage() : 0);
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }


    public Integer getIseng() {
        return iseng;
    }

    public void setIseng(Integer iseng) {
        this.iseng = iseng;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getHaveClasstimes() {
        return haveClasstimes;
    }

    public void setHaveClasstimes(Integer haveClasstimes) {
        this.haveClasstimes = haveClasstimes;
    }

    public Integer getTotalClassHour() {
        return totalClassHour;
    }

    public void setTotalClassHour(Integer totalClassHour) {
        this.totalClassHour = totalClassHour;
    }

    public Integer getLeftClassHour() {
        return leftClassHour;
    }

    public void setLeftClassHour(Integer leftClassHour) {
        this.leftClassHour = leftClassHour;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEduStage() {
        return eduStage;
    }

    public void setEduStage(Integer eduStage) {
        this.eduStage = eduStage;
    }


}
