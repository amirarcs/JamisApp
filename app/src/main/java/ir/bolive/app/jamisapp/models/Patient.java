package ir.bolive.app.jamisapp.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="patient")
public class Patient {

    @PrimaryKey(autoGenerate =true)
    @ColumnInfo(name = "pid")
    @NonNull
    private int pid;

    @ColumnInfo(name="fullname")
    private String fullname;

    @ColumnInfo(name="nationalcode")
    private String nationalcode;

    @ColumnInfo(name="phone")
    private String phone;

    @ColumnInfo(name="refdate")
    private String refdate;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNationalcode() {
        return nationalcode;
    }

    public void setNationalcode(String nationalcode) {
        this.nationalcode = nationalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefdate() {
        return refdate;
    }

    public void setRefdate(String refdate) {
        this.refdate = refdate;
    }
}
