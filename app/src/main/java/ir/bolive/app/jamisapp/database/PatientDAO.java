package ir.bolive.app.jamisapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import ir.bolive.app.jamisapp.models.Patient;

@Dao
public interface PatientDAO {
    @Query("Select * from tbl_patient")
    List<Patient> getAll();
    @Query("Select * from tbl_patient where pid=:pid")
    Patient getById(long pid);
    @Query("Select * from tbl_patient where nationalcode=:ncode")
    List<Patient> getbyNationalCode(String ncode);

    @Query("Select * from tbl_patient where fullname like :name")
    List<Patient> getbyName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPatient(Patient patient);

    @Delete
    void deletePatient(Patient patient);


}
