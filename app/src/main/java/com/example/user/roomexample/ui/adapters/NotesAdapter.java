package com.example.user.roomexample.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.roomexample.R;
import com.example.user.roomexample.domain.model.Note;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements View.OnClickListener{
    private List<Note> dataset;
    private OnItemClickListener mListener;

    public NotesAdapter(List<Note> dataset, OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.mListener = onItemClickListener;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = dataset.get(position);
        holder.title.setText(note.getTitle());
        holder.body.setText(note.getBody().replaceAll("[\n]", ""));

        holder.delete.setOnClickListener(this);
        holder.delete.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if(mListener != null){
            int position = (int)v.getTag();
            if(position != RecyclerView.NO_POSITION){
                Note note = dataset.get(position);
                mListener.onItemDelete(note);
                dataset.remove(note);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataset.size());
            }
        }
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
        View mView;
        TextView title, body;
        ImageView delete;

        public ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            mView = view;
            title = view.findViewById(R.id.titleTextView);
            body = view.findViewById(R.id.bodyTextView);
            delete = view.findViewById(R.id.deleteImageView);
        }
    }

    public interface OnItemClickListener{
        void onItemDelete(Note note);
    }
}
