package com.alex.provider.entity;

import com.alex.provider.enums.UserSexEnum;

/**
 * @ClassName User
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/23 0023 下午 3:29
 * @Version 1.0
 **/
public class User {
    private Long id;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;


    public User(Long id, String userName, String passWord, UserSexEnum man, String nickName) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.userSex = man;
        this.nickName = nickName;
    }

    public User(String userName, String passWord, UserSexEnum userSex) {
        super();
        this.passWord = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

//    @Override
//    public String toString() {
//        // TODO Auto-generated method stub
//        return "userName " + this.userName + ", pasword " + this.passWord + "sex " + userSex.name();
//    }
}
