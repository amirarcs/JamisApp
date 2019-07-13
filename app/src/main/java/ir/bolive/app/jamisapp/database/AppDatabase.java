package ir.bolive.app.jamisapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ir.bolive.app.jamisapp.models.FaceArgs;
import ir.bolive.app.jamisapp.models.Gallery;
import ir.bolive.app.jamisapp.models.Patient;

@Database(entities = {Gallery.class, Patient.class, FaceArgs.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GalleryDAO galleryDAO();
    public abstract PatientDAO patientDAO();
    public abstract FaceArgDAO faceArgDAO();
}
