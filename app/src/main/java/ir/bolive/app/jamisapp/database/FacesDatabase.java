package ir.bolive.app.jamisapp.database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import ir.bolive.app.jamisapp.models.FaceArgs;
import ir.bolive.app.jamisapp.models.Gallery;
import ir.bolive.app.jamisapp.models.Patient;


public class FacesDatabase {

    Context context;

    private static FacesDatabase mInstance;
    private static DatabaseClient databaseClient;
    private static final String DB_NAME="doctorface.db";

    public FacesDatabase(Context context){
        this.context=context;
        databaseClient=Room.databaseBuilder(this.context,DatabaseClient.class,DB_NAME)
                .addCallback(DatabaseClient.sfaceDatabaseCallback)
                .build();
    }

    public static synchronized FacesDatabase getdatabase(final Context context){
        if (mInstance==null){
            mInstance=new FacesDatabase(context);
        }
        return mInstance;
    }
    public DatabaseClient getInstance(){
        return databaseClient;
    }

}
