package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fst_t0763.R;

import java.util.ArrayList;

public class SeatBooking_Adapter extends BaseAdapter {
    Integer seat;
    Integer pos;

    public SeatBooking_Adapter(Integer seat) {
        this.seat = seat;
    }

    @Override
    public int getCount() {
                return 50 ;

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_seat,parent,false);



        return v;
    }
}




   /* public SeatBooking_Adapter(Context context) {

        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_seat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.seats.setImageResource(R.drawable.after_booking);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView seats;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seats = itemView.findViewById(R.id.one_seat);


        }
    }*/

