package com.kashyap.architectureexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private  NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository=new NoteRepository(application);
        allNotes=noteRepository.getAllnotes();
    }
    public void insert(Note note){
        noteRepository.insertdata(note);
    }
    public void update(Note note){
        noteRepository.updatedata(note);
    }
    public void delete(Note note){
        noteRepository.delete(note);
    }
    public void deleteall(){
        noteRepository.deleteAll();
    }
   public  LiveData<List<Note>> getAllNotes(){
        return allNotes;
   }
}
