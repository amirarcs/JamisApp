package ir.bolive.app.jamisapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ir.bolive.app.jamisapp.models.Patient;

@Dao
public interface PatientDAO {
    @Query("Select * from tbl_patient")
    List<Patient> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPatient(Patient patient);

    @Delete
    void deletePatient(Patient patient);


}
