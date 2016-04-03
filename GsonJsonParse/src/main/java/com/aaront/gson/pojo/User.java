package com.aaront.gson.pojo;

/**
 * @author tonyhui
 * @since 16/4/3
 */
//public class User {
//    //省略其它
//    public String name;
//    public int age;
//    public String emailAddress;
//    public boolean isMarried;
//
//    public User(String name, int age){
//        this.name = name;
//        this.age = age;
//    }
//
//    public User(){}
//}


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * gson类库使用的是属性名来填充对象而不是getter和setter方法,即使属性名是private的
 */
public class User {
    //省略其它
    public String name;
    public int age;
    // 添加了注解之后不管是序列化还是反序列化都要中别名
    // 当上面的三个属性(email_address、email、emailAddress)都中出现任意一个时均可以得到正确的结果。
    // 注：当多种情况同时出时，以最后一个出现的值为准。
    @SerializedName(value = "emailAddress", alternate = {"email", "email_address"})
    public String emailAddress;
    public boolean isMarried;
    public Date birth;

    public User(String name, int age){
        this.name = name;
        this.age = age;
    }

    public User(){}

    public String getFirstName(){
        return name;
    }

    public void setFirstName(String firstName){
        name = firstName;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                ", isMarried=" + isMarried +
                '}';
    }
}