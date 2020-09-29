package com.kashyap.architectureexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapt extends RecyclerView.Adapter<adapt.viewh> {

private List<Note> al1=new ArrayList<>();
private OnItemClickListener listener;

    @NonNull
    @Override
    public viewh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);

        return new viewh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewh holder, int position) {
      Note note=al1.get(position);
      holder.tvpriority.setText(String.valueOf(note.getPriority()));
      holder.tvtitle.setText(note.getTitle());
      holder.tvdesc.setText(note.getDescription());

    }
    public void setNotes(List<Note> notes){
        this.al1=notes;
        notifyDataSetChanged();
    }
    public Note notePos(int position){
        return al1.get(position);
    }

    @Override
    public int getItemCount() {
        return al1.size();
    }

    public class viewh extends RecyclerView.ViewHolder{
        TextView tvdesc;
        TextView tvtitle;
        TextView  tvpriority;

        public viewh(@NonNull View itemView) {
            super(itemView);
                  tvpriority=itemView.findViewById(R.id.tvpriority);
           tvtitle=itemView.findViewById(R.id.tvtext);
          tvdesc=itemView.findViewById(R.id.tvdesc);
          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  int position=getAdapterPosition();
                  if(listener!=null && position!=RecyclerView.NO_POSITION)
                  listener.onItemClick(al1.get(position));
              }
          });

        }
    }
    public interface OnItemClickListener{
             void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

}
