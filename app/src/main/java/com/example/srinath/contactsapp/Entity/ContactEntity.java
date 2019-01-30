package com.example.srinath.contactsapp.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "contacts")
public class ContactEntity {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "mobileno")
    String mobileno;

    @ColumnInfo(name = "emailid")
    String emailid;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "profileimage")
    String profileimage;

    @ColumnInfo(name = "timestamp")
    Date timestamp;

    public ContactEntity(String name, String mobileno, String emailid, String address, String profileimage) {
        this.name = name;
        this.mobileno = mobileno;
        this.emailid = emailid;
        this.address = address;
        this.profileimage = profileimage;
        this.timestamp = new Date();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
