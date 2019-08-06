package ir.bolive.app.jamisapp.models;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_Gallery")
public class Gallery {

    @PrimaryKey(autoGenerate = true)
    private int gid;

    private long pid_fk;
    private String title;
    private int imgMode;

    public int getImgMode() {
        return imgMode;
    }

    public void setImgMode(int imgMode) {
        this.imgMode = imgMode;
    }

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public long getPid_fk() {
        return pid_fk;
    }

    public void setPid_fk(long pid_fk) {
        this.pid_fk = pid_fk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
