package com.kashyap.architectureexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){

        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,
                    "note_databse")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;

    }
    private static RoomDatabase.Callback roomcallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulateAsynctask(instance).execute();
            super.onCreate(db);

        }
    };
    private static class PopulateAsynctask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        PopulateAsynctask(NoteDatabase db){
            noteDao=db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Manohar","kashyap",1));
            noteDao.insert(new Note("title 2","desc2",2));
            return null;
        }
    }
}
