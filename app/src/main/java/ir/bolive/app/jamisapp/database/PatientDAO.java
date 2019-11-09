package ir.bolive.app.jamisapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import ir.bolive.app.jamisapp.models.Patient;

@Dao
public interface PatientDAO {
    @Query("select * from patient")
    List<Patient> getAll();


    @Query("select * from patient where pid=:pid")
    Patient getById(long pid);

    @Query("select * from patient where nationalcode=:ncode")
    List<Patient> getbyNationalCode(String ncode);

    @Query("select * from patient where fullname like :name")
    List<Patient> getbyName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPatient(Patient patient);

    @Delete
    void deletePatient(Patient patient);
    @Update
    void updatePatient(Patient patient);


}
