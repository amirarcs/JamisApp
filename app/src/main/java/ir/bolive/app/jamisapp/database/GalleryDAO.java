package ir.bolive.app.jamisapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.bolive.app.jamisapp.models.Gallery;

@Dao
public interface GalleryDAO {
    @Query("Select * from tbl_Gallery where pid_fk = :id")
    List<Gallery> getAll(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGallery(Gallery gallery);

    @Delete
    void delete(Gallery gallery);

}
