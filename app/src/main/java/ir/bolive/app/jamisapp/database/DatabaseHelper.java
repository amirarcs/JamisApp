package ir.bolive.app.jamisapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    //GALLERY CRUD

    //FACE CRUD
}
