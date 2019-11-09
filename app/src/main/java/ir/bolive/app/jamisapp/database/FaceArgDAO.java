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
    @Query("Select * from faceArgs order by pid_fk")
    List<FaceArgs> getAll();

    @Query("Select * from faceArgs where pid_fk=:pid")
    List<FaceArgs> getArgs(long pid);

    @Query("Select * from faceArgs where pid_fk=:pid and chinMode=:chinmode")
    FaceArgs getArgsByChin(long pid,int chinmode);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertFaceArgs(FaceArgs faceArgs);

    @Delete
    void deleteFaceArg(FaceArgs faceArgs);
}
