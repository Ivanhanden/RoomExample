package com.example.user.roomexample.ui.activities;


import android.os.Bundle;
import android.view.View;

import com.example.user.roomexample.R;
import com.example.user.roomexample.data.database.AppDatabase;
import com.example.user.roomexample.domain.model.Note;
import com.example.user.roomexample.ui.adapters.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreData();
    }

    private void restoreData() {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        List<Note> noteList = db.noteDao().getAll();
        updateUI(getNoteList());
    }

    private void updateUI(List<Note> noteList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        NotesAdapter notesAdapter = new NotesAdapter(noteList, MainActivity.this);
        recyclerView.setAdapter(notesAdapter);
    }

    private List<Note> getNoteList() {
        List<Note> list = new ArrayList<>();
        list.add(createNote("Title 1", "Body 1"));
        list.add(createNote("Title 2", "Body 2"));
        list.add(createNote("Title 3", "Body 3"));
        return list;
    }

    private Note createNote(String title, String body) {
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        return note;
    }



}
