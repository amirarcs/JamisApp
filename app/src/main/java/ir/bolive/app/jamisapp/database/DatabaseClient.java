package ir.bolive.app.jamisapp.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ir.bolive.app.jamisapp.models.FaceArgs;
import ir.bolive.app.jamisapp.models.Gallery;
import ir.bolive.app.jamisapp.models.Patient;

@Database(entities = {Gallery.class, Patient.class, FaceArgs.class},version = 1)
public abstract class DatabaseClient extends RoomDatabase {
    public abstract GalleryDAO galleryDAO();
    public abstract PatientDAO patientDAO();
    public abstract FaceArgDAO faceArgDAO();

    public static RoomDatabase.Callback sfaceDatabaseCallback=new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
