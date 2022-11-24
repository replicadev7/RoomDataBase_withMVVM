package com.rahul.roomdatabasemvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserModal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String UserName;
    private String userNumber;
    private String userDate;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public UserModal(String courseName, String userNumber, String userDate, byte[] image) {
        this.UserName = courseName;
        this.userNumber = userNumber;
        this.userDate = userDate;
        this.image=image;
    }


    public UserModal(){

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String courseName) {
        this.UserName = courseName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}