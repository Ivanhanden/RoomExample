package com.example.user.roomexample.ui.adapters;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.roomexample.R;
import com.example.user.roomexample.domain.model.Note;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> dataset;
    private Context context;

    public NotesAdapter(List<Note> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note film = dataset.get(position);
        holder.title.setText(film.getTitle());
        holder.body.setText(film.getBody().replaceAll("[\n]", ""));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void updateList(List<Note> updatedList) {
        dataset = updatedList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            body = view.findViewById(R.id.bodyTextView);

        }
    }
}