package com.example.user.roomexample.ui.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.roomexample.R;
import com.example.user.roomexample.data.database.AppDatabase;
import com.example.user.roomexample.domain.model.Note;
import com.example.user.roomexample.ui.adapters.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {

    private EditText titleEditText;
    private EditText bodyEditText;
    private Button   addButton;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private NotesAdapter notesAdapter;

    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreData();

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        bodyEditText  = (EditText) findViewById(R.id.bodyEditText);
        addButton     = (Button)   findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(titleEditText.getText().toString()) && !TextUtils.isEmpty(bodyEditText.getText().toString())){
                    AppDatabase.getAppDatabase(MainActivity.this).noteDao().insert(new Note(titleEditText.getText().toString(), bodyEditText.getText().toString()));
                    noteList = getNoteList();
                    notesAdapter.updateList(noteList);
                } else{
                    Toast.makeText(getApplicationContext(), "Empty note", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void restoreData() {
        //AppDatabase db = AppDatabase.getAppDatabase(this);
        //List<Note> noteList = db.noteDao().getAll();
        updateUI(getNoteList());
    }

    private void updateUI(final List<Note> noteList) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        notesAdapter = new NotesAdapter(noteList, MainActivity.this);
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemDelete(int position) {
                AppDatabase.getAppDatabase(MainActivity.this).noteDao().delete(noteList.get(position));
                noteList.remove(position);
                notesAdapter.updateListAfterRemoving(position);
            }
        });
    }

    private List<Note> getNoteList() {
        /*AppDatabase.getAppDatabase(this).noteDao().insertAll(
                createNote("Title 1", "Body 1"),
                createNote("Title 2", "Body 2"),
                createNote("Title 3", "Body 3")
        );*/
        return AppDatabase.getAppDatabase(this).noteDao().getAll();
    }
}
