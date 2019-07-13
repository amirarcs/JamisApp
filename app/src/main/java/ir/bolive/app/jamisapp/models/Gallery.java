package ir.bolive.app.jamisapp.models;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_Gallery")
public class Gallery {

    @PrimaryKey(autoGenerate = true)
    int gid;

    String pid_fk;
    String title;
    String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
