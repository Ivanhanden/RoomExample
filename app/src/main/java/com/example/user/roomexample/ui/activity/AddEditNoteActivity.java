package com.example.user.roomexample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.user.roomexample.R;
import com.example.user.roomexample.data.entity.Note;
import com.example.user.roomexample.ui.viewmodel.AddEditNoteViewModel;

import androidx.lifecycle.ViewModelProviders;

import static com.example.user.roomexample.ui.activity.MainActivity.EXTRA_DESCRIPTION;
import static com.example.user.roomexample.ui.activity.MainActivity.EXTRA_ID;
import static com.example.user.roomexample.ui.activity.MainActivity.EXTRA_PRIORITY;
import static com.example.user.roomexample.ui.activity.MainActivity.EXTRA_TITLE;

public class AddEditNoteActivity extends BaseActivity {
    private AddEditNoteViewModel addEditNoteViewModel;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        addEditNoteViewModel = ViewModelProviders.of(this).get(AddEditNoteViewModel.class);
        Note note = new Note(title, description, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            note.setId(id);
            addEditNoteViewModel.update(note);
        } else {
            addEditNoteViewModel.insert(note);
        }

        setResult(RESULT_OK, new Intent());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
