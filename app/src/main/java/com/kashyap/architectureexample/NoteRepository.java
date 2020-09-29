package com.kashyap.architectureexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allnotes;

    NoteRepository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();
        allnotes=noteDao.selectAllData();
    }
    public void insertdata(Note note){
      new InsertAsynctask(noteDao).execute(note);
    }
    public void updatedata(Note note){
   new UpdateAsynctask(noteDao).execute(note);
    }
    public void delete(Note note){
    new DeleteAsynctask(noteDao).execute(note);
    }
    public void deleteAll(){
    new DeleteAllAsynctask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllnotes(){
        return allnotes;
    }
    public static  class InsertAsynctask extends AsyncTask<Note,Void ,Void>{
      private NoteDao noteDao;
      InsertAsynctask(NoteDao noteDao){
          this.noteDao=noteDao;
      }
        @Override
        protected Void doInBackground(Note... notes) {
          noteDao.insert(notes[0]);
            return null;
        }
    }
    public static  class UpdateAsynctask extends AsyncTask<Note,Void ,Void>{
        private NoteDao noteDao;
        UpdateAsynctask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    public static  class DeleteAsynctask extends AsyncTask<Note,Void ,Void>{
        private NoteDao noteDao;
        DeleteAsynctask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    public static  class DeleteAllAsynctask extends AsyncTask<Void,Void ,Void>{
        private NoteDao noteDao;
        DeleteAllAsynctask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deletAllnote();
            return null;
        }
    }


}
