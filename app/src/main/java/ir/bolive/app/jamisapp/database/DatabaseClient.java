package ir.bolive.app.jamisapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class DatabaseClient {
    Context context;
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context){
        this.context=context;
        appDatabase= Room.databaseBuilder(context,AppDatabase.class,"DentalDb").build();
    }
    public static synchronized DatabaseClient getInstance(Context context){
        if (mInstance==null){
            mInstance=new DatabaseClient(context);
        }
        return mInstance;
    }
    public AppDatabase getAppDatabase(){
        return appDatabase;
    }

}
