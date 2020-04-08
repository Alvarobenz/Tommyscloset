package com.example.tommyscloset;

public class item {
    public double age;
    public String gender;
    public String userName;

    //required default constructor
    public item() {
    }

    public item(double age, String gender, String userName) {
        this.age = age;
        this.gender = gender;
        this.userName = userName;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
