package ir.bolive.app.jamisapp.models;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="tbl_patient")
public class Patient {

    @PrimaryKey(autoGenerate =true)
    int pid;

    String fullname;
    String nationalcode;
    String phone;
    String refdate;

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
