package ir.bolive.app.jamisapp.models;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_Gallery")
public class Gallery {

    @PrimaryKey(autoGenerate = true)
    private int gid;

    private String pid_fk;
    private String title;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getPid_fk() {
        return pid_fk;
    }

    public void setPid_fk(String pid_fk) {
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
