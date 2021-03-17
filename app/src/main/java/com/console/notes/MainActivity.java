package com.console.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add;
    ListView note;



   static ArrayList<String> notes = new ArrayList<>();
   static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        note = findViewById(R.id.note);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.console.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);



        if(set == null){
            notes.add("Example note");
        }else {
            notes = new ArrayList(set);
        }




         arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        note.setAdapter(arrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,InputNoteactivity.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "Button is Clicked"   ,Toast.LENGTH_SHORT).show();
            }
        });

       note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent i = new Intent(MainActivity.this,InputNoteactivity.class);
               i.putExtra("NoteId",position);
               startActivity(i);
           }
       });

       note.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

               final int ItemToDelete = position;

               new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you sure")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               notes.remove(ItemToDelete);
                               arrayAdapter.notifyDataSetChanged();

                               SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.console.notes", Context.MODE_PRIVATE);
                               HashSet<String> set = new HashSet<>(MainActivity.notes);
                               sharedPreferences.edit().putStringSet("notes",set).apply();
                           }
                       }

                       )
                       .setNegativeButton("No", null)
                       .show();

               return true;
           }
       });

    }
}