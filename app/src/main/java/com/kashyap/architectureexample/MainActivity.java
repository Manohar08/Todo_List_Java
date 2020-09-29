package com.kashyap.architectureexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    public static final int request_code1 = 1;
    public static final int request_code2=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.floatingbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setHasFixedSize(true);
        final adapt adapter = new adapt();
        recyclerView.setAdapter(adapter);


        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.notePos(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new adapt.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
            Intent intent=new Intent(MainActivity.this,AddNote.class);
            intent.putExtra(AddNote.id,note.getId());
            intent.putExtra(AddNote.titles,note.getTitle());
            intent.putExtra(AddNote.descriptions,note.getDescription());
            intent.putExtra(AddNote.prioritys,note.getPriority());
            startActivityForResult(intent,request_code2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_code1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNote.titles);
            String desc = data.getStringExtra(AddNote.descriptions);
            int priority = data.getIntExtra(AddNote.prioritys, 1);
            Note note = new Note(title, desc, priority);
            noteViewModel.insert(note);
        } else if(requestCode == request_code2 && resultCode == RESULT_OK){
            int id=data.getIntExtra(AddNote.id,-1);
            if(id==-1){
                Toast.makeText(this, "not updated", Toast.LENGTH_SHORT).show();
           return;
            }
            String title = data.getStringExtra(AddNote.titles);
            String desc = data.getStringExtra(AddNote.descriptions);
            int priority = data.getIntExtra(AddNote.prioritys, 1);
            Note note = new Note(title, desc, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "data updated", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, "node note saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletenote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deletenotes) {
            noteViewModel.deleteall();
            Toast.makeText(this, "all notes deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }
}