package ru.skaridov.mynotes.classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ru.skaridov.mynotes.R;
import ru.skaridov.mynotes.interfaces.OnItemClickListener;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private ArrayList<TaskNote> data;
    private OnItemClickListener listener;


    public MyRecyclerAdapter(ArrayList<TaskNote> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        EditText textName;
        RelativeLayout relativeLayout;

        public MyViewHolder(View v) {
            super(v);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.recycler_item_relative_layout);
            textName = (EditText) v.findViewById(R.id.recycler_item_text_name);
        }

        public void bind(final TaskNote note, final OnItemClickListener listener) {
            textName.setText(note.getName());
            textName.setEnabled(false);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(note, textName);
                }
            });
//            textName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onNameClick(note, textName);
//                }
//            });
        }

        public void setEditTextDisabled() {

        }
    }
}
