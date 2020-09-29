package com.kashyap.architectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {
EditText title,desc;
NumberPicker numberPicker;
public static final String titles="titles";
public static final String id="id";
public static final String descriptions="desc";
public static final String prioritys="priority";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title=findViewById(R.id.edittext_title);
        desc=findViewById(R.id.edittext_desc);
        numberPicker=findViewById(R.id.numberpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent1=getIntent();
        if(intent1.hasExtra(id)){
            setTitle("edit note");
            title.setText(intent1.getStringExtra(titles));
            desc.setText(intent1.getStringExtra(descriptions));
            numberPicker.setValue(intent1.getIntExtra(prioritys,1));
        }
        else{
        setTitle("add note");}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.save1){
            saveNote();
            Toast.makeText(this, "item  saved", Toast.LENGTH_SHORT).show();
            return  true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String titless=title.getText().toString();
        String descc=desc.getText().toString();
        int priority=numberPicker.getValue();
        if(titless.trim().isEmpty() || descc.trim().isEmpty()){
            Toast.makeText(this, "insert a data ", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(titles,titless)
                .putExtra(descriptions,descc)
                .putExtra(prioritys,priority);
        int id=getIntent().getIntExtra(AddNote.id,-1);
        if(id!=-1){
            intent.putExtra(AddNote.id,id);
        }
        setResult(RESULT_OK,intent);
        finish();
    }
}