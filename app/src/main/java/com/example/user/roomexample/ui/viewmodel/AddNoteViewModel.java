package com.example.user.roomexample.ui.viewmodel;

import android.app.Application;

import com.example.user.roomexample.data.entity.Note;
import com.example.user.roomexample.data.repository.NoteRepository;

import androidx.lifecycle.AndroidViewModel;

public class AddNoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;

    public AddNoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }
}
