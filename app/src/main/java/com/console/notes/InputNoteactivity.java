package com.console.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.HashMap;
import java.util.HashSet;

public class InputNoteactivity extends AppCompatActivity {
    EditText editText;
    int noteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_noteactivity);

        editText = findViewById(R.id.newnote);

        Intent i =getIntent();
        noteid = i.getIntExtra("NoteId", -1);

        if(noteid != -1)
        {
         editText.setText(MainActivity.notes.get(noteid));
        }else {
            MainActivity.notes.add("");
            noteid=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 MainActivity.notes.set(noteid, String.valueOf(s));
                 MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.console.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}