package ir.bolive.app.jamisapp.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "faceArgs")
public class FaceArgs {
    //نوک انسیزال بالا و پایین
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fid")
    @NonNull
    private int fid;

    @ColumnInfo(name = "upper_central_ans")
    private float upper_central_ans;

    @ColumnInfo(name = "lower_central_ans")
    private float lower_central_ans;
    //نوک جینجوال بالا و پایین
    @ColumnInfo(name = "upper_ging")
    private float upper_ging;

    @ColumnInfo(name = "lower_ging")
    private float lower_ging;

    @ColumnInfo(name = "midLine")
    private float midLine=0;

    @ColumnInfo(name = "chinMode")
    //حالت چانه
    private  int chinMode;

    @ColumnInfo(name = "x_ear")
    // زاویه گوش
    private float x_ear;

    @ColumnInfo(name = "y_ear")
    private float y_ear;

    @ColumnInfo(name = "x_eye")
    //زاویه چشم
    private float x_eye;

    @ColumnInfo(name = "y_eye")
    private float y_eye;

    @ColumnInfo(name = "x_eyebrow")
    //زاویه ابرو
    private float x_eyebrow;

    @ColumnInfo(name = "y_eyebrow")
    private float y_eyebrow;

    @ColumnInfo(name = "x_ramus")
    //نوک استخوان راموس
    private float x_ramus;

    @ColumnInfo(name = "y_ramus")
    private float y_ramus;

    @ColumnInfo(name = "pid_fk")
    private long pid_fk;


    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getChinMode() {
        return chinMode;
    }

    public void setChinMode(int chinMode) {
        this.chinMode = chinMode;
    }

    public long getPid_fk() {
        return pid_fk;
    }

    public void setPid_fk(long pid_fk) {
        this.pid_fk = pid_fk;
    }

    public float getUpper_central_ans() {
        return upper_central_ans;
    }

    public void setUpper_central_ans(float upper_central_ans) {
        this.upper_central_ans = upper_central_ans;
    }

    public float getLower_central_ans() {
        return lower_central_ans;
    }

    public void setLower_central_ans(float lower_central_ans) {
        this.lower_central_ans = lower_central_ans;
    }

    public float getUpper_ging() {
        return upper_ging;
    }

    public void setUpper_ging(float upper_ging) {
        this.upper_ging = upper_ging;
    }

    public float getLower_ging() {
        return lower_ging;
    }

    public void setLower_ging(float lower_ging) {
        this.lower_ging = lower_ging;
    }

    public float getMidLine() {
        return midLine;
    }

    public void setMidLine(float midLine) {
        this.midLine = midLine;
    }

    public float getX_ear() {
        return x_ear;
    }

    public void setX_ear(float x_ear) {
        this.x_ear = x_ear;
    }

    public float getY_ear() {
        return y_ear;
    }

    public void setY_ear(float y_ear) {
        this.y_ear = y_ear;
    }

    public float getX_eye() {
        return x_eye;
    }

    public void setX_eye(float x_eye) {
        this.x_eye = x_eye;
    }

    public float getY_eye() {
        return y_eye;
    }

    public void setY_eye(float y_eye) {
        this.y_eye = y_eye;
    }

    public float getX_eyebrow() {
        return x_eyebrow;
    }

    public void setX_eyebrow(float x_eyebrow) {
        this.x_eyebrow = x_eyebrow;
    }

    public float getY_eyebrow() {
        return y_eyebrow;
    }

    public void setY_eyebrow(float y_eyebrow) {
        this.y_eyebrow = y_eyebrow;
    }

    public float getX_ramus() {
        return x_ramus;
    }

    public void setX_ramus(float x_ramus) {
        this.x_ramus = x_ramus;
    }

    public float getY_ramus() {
        return y_ramus;
    }

    public void setY_ramus(float y_ramus) {
        this.y_ramus = y_ramus;
    }

}
