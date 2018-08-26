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
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {

    private EditText titleEditText;
    private EditText bodyEditText;

    private NotesAdapter notesAdapter;

    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreData();

        titleEditText = findViewById(R.id.titleEditText);
        bodyEditText  = findViewById(R.id.bodyEditText);
        Button addButton = findViewById(R.id.button);
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
        updateUI(getNoteList());
    }

    private void updateUI(List<Note> noteList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        notesAdapter = new NotesAdapter(noteList, MainActivity.this);
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemDelete(int position) {
                deleteItem(position);
            }
        });
    }

    private void deleteItem(int position){
        AppDatabase.getAppDatabase(MainActivity.this).noteDao().delete(noteList.get(position));
        noteList.remove(position);
        notesAdapter.updateListAfterRemoving(position);
    }

    private List<Note> getNoteList() {
        return AppDatabase.getAppDatabase(this).noteDao().getAll();
    }
}
