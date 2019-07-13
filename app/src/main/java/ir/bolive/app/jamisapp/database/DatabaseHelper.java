package ir.bolive.app.jamisapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import ir.bolive.app.jamisapp.models.Patient;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context,DB_Constants.DB_NAME,null,DB_Constants.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_Constants.CREATE_TABLE_PATIENT);
        db.execSQL(DB_Constants.CREATE_TABLE_GALLERY);
        db.execSQL(DB_Constants.CREATE_TABLE_FACE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DB_Constants.DROP_TABLE_PATIENT);
        db.execSQL(DB_Constants.DROP_TABLE_GALLERY);
        db.execSQL(DB_Constants.DROP_TABLE_FACE);

        onCreate(db);
    }

    //PATIENT CRUD
    public List<Patient> getAllPatients(){
        SQLiteDatabase db=this.getWritableDatabase();
        List<Patient> patientList=new ArrayList<Patient>();
        String query = "SELECT * FROM " + DB_Constants.TABLE_PATIENT + " ORDER BY " + DB_Constants.FULLNAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Patient patient=new Patient();
                patient.setPid(cursor.getInt(cursor.getColumnIndex(DB_Constants.PATIENT_ID)));
                patient.setFullname(cursor.getString(cursor.getColumnIndex(DB_Constants.FULLNAME)));
                patient.setNationalcode(cursor.getString(cursor.getColumnIndex(DB_Constants.NATIONAL_CODE)));
                patient.setPhone(cursor.getString(cursor.getColumnIndex(DB_Constants.PHONE)));
                patient.setRefdate(cursor.getString(cursor.getColumnIndex(DB_Constants.REF_DATE)));
                patientList.add(patient);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return patientList;
    }
    public Patient getPatient(String nationalCode,String fullname){
        SQLiteDatabase db=this.getReadableDatabase();
        Patient patient=new Patient();
        Cursor cursor=null;
        if(nationalCode!=null){
             cursor=db.query(DB_Constants.TABLE_PATIENT,new String[]{DB_Constants.PATIENT_ID,DB_Constants.FULLNAME,
                            DB_Constants.NATIONAL_CODE,DB_Constants.PHONE,DB_Constants.REF_DATE},DB_Constants.NATIONAL_CODE+"=?",
                    new String[]{nationalCode},null,null,null);
        }
        else if(fullname!=null){
             cursor=db.query(DB_Constants.TABLE_PATIENT,new String[]{DB_Constants.PATIENT_ID,DB_Constants.FULLNAME,
                            DB_Constants.NATIONAL_CODE,DB_Constants.PHONE,DB_Constants.REF_DATE},DB_Constants.NATIONAL_CODE+"=?",
                    new String[]{fullname},null,null,null);
        }

        if (cursor!=null){
            cursor.moveToFirst();
            patient.setPid(cursor.getInt(cursor.getColumnIndex(DB_Constants.PATIENT_ID)));
            patient.setFullname(cursor.getString(cursor.getColumnIndex(DB_Constants.FULLNAME)));
            patient.setNationalcode(cursor.getString(cursor.getColumnIndex(DB_Constants.NATIONAL_CODE)));
            patient.setPhone(cursor.getString(cursor.getColumnIndex(DB_Constants.PHONE)));
            patient.setRefdate(cursor.getString(cursor.getColumnIndex(DB_Constants.REF_DATE)));
            cursor.close();
        }
        return patient;
    }
    public boolean insetPatient(Patient patient){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_Constants.FULLNAME, patient.getFullname());
        values.put(DB_Constants.PHONE, patient.getPhone());
        values.put(DB_Constants.NATIONAL_CODE, patient.getNationalcode());
        values.put(DB_Constants.REF_DATE, patient.getRefdate());
        long rowId=db.insert(DB_Constants.TABLE_PATIENT,null,values);
        if (rowId>0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean updatePatient(Patient patient){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_Constants.FULLNAME, patient.getFullname());
        values.put(DB_Constants.PHONE, patient.getPhone());
        values.put(DB_Constants.NATIONAL_CODE, patient.getNationalcode());
        values.put(DB_Constants.REF_DATE, patient.getRefdate());
        int affected=db.update(DB_Constants.TABLE_PATIENT,values,DB_Constants.PATIENT_ID+"=?",
                new String[]{String.valueOf(patient.getPid())});
        if (affected>0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deletePatient(int pid){
        SQLiteDatabase db=this.getWritableDatabase();
        int affected =db.delete(DB_Constants.TABLE_PATIENT,DB_Constants.PATIENT_ID+"=?",
                new String[]{String.valueOf(pid)});
        if (affected>0){
            return true;
        }
        else{
            return false;
        }
    }
    //GALLERY CRUD

    //FACE CRUD
}
