package com.example.pickmybus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
Context context;
ArrayList<UserData> list;
OnNoteListener mOnNoteListener;
            public Myadapter(Context context, ArrayList<UserData> list,OnNoteListener onNoteListener) {
        this.context = context;
        this.mOnNoteListener=onNoteListener;
        //Log.e("Adapter", String.valueOf(list));
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         UserData userData= list.get(position);
         Log.e("from adapter", String.valueOf(list.get(position)));
        String TName=userData.getTname();
        String Fm=userData.getFrom();
        String Too=userData.getto();
        String Vehi= userData.getVehiclename();
        holder.Transportname.setText(TName);
        holder.to.setText(Too);
        holder.frm.setText(Fm);
        holder.Vehiclename.setText(Vehi);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView Transportname;
        private final TextView frm;
        private final TextView to;
        private final TextView Vehiclename;
        OnNoteListener onNoteListener;
        public MyViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            Transportname=itemView.findViewById(R.id.Transport);
            Vehiclename=itemView.findViewById(R.id.vehicle);
            frm=itemView.findViewById(R.id.from);
            to=itemView.findViewById(R.id.to);
            itemView.setOnClickListener(this);
            this.onNoteListener=onNoteListener;
        }
        @Override
        public void onClick(View view) {
                onNoteListener.onNoteClick(getAdapterPosition());

        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
