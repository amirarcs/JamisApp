package ir.bolive.app.jamisapp.database;

import android.media.FaceDetector;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ir.bolive.app.jamisapp.models.FaceArgs;

@Dao
public interface FaceArgDAO {
    @Query("Select * from tbl_faceArgs where pid_fk= :pid")
    FaceArgs getArgs(long pid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFaceArgs(FaceArgs faceArgs);

    @Delete
    void deleteFaceArg(FaceArgs faceArgs);
}
