package com.example.fst_t0763;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapters.SeatBooking_Adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class SeatBooking extends Fragment {
    int TotalSeats, checker = 1;

    public SeatBooking(Integer totalSeats) {
        this.TotalSeats = totalSeats;
    }

    GridView seatGrid;
    Button pay;
    boolean SELECTOR = true;
    FrameLayout seat_frame;


    //String[] selected_seat=new String[TotalSeats];
//    Integer selected_seat [];
    ArrayList<Integer> selected_seat = new ArrayList<>(TotalSeats);
    ArrayList<Integer> validator = new ArrayList<>();
    ArrayList<Integer> endseats =
            new ArrayList<>(Arrays.asList(5, 11, 17, 23, 29, 35, 41, 47, 48, 49));


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_seat_booking, container, false);
        seatGrid = v.findViewById(R.id.seatGrid);



        seatGrid.setAdapter(new SeatBooking_Adapter(TotalSeats));
        seat_frame = v.findViewById(R.id.seatBooking_frame);
        pay = v.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_seat.size() < TotalSeats | selected_seat.size() > TotalSeats) {
                    Toast.makeText(getContext(), "Please select proper number of  seats", Toast.LENGTH_SHORT).show();

                } else {

                    for (int i = 0; i < selected_seat.size(); i++) {
                        validator.add(selected_seat.get(i));
                    }
                    Intent intent = new Intent(getContext(), Payments.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) validator);
                    intent.putExtra("TicketData", args);
                    startActivity(intent);
                    //Toast.makeText(getContext(), validator.toString(), Toast.LENGTH_SHORT).show();


                }
            }
        });

        seatGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView img = view.findViewById(R.id.one_seat);
/*
                if (checker < TotalSeats) {

                    if (endseats.contains(position)) {
                        img.setImageResource(R.drawable.after_booking);
                        for (int i = 0; i < TotalSeats; i++) {

                            img.setImageResource(R.drawable.after_booking);

                            selected_seat.add(position);
                            position--;

                            checker++;
                        }


                        Toast.makeText(getContext(), "selected seats are  " + selected_seat, Toast.LENGTH_LONG).show();
                    } else {

                        for (int i = 0; i < TotalSeats; i++) {

                            selected_seat.add(position);
                            position++;
                            checker++;
                        }
                        Toast.makeText(getContext(), "selected seats are  " + selected_seat, Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Booking Completed", Toast.LENGTH_SHORT).show();
                }*/

                if (selected_seat.contains(position)) {
                    checker--;
                    Toast.makeText(getContext(), " Select more ", Toast.LENGTH_SHORT).show();
                    img.setImageResource(R.drawable.before_booking);
                    selected_seat.remove(selected_seat.indexOf(position));
                } else {
                    if (checker - 1 == TotalSeats) {
                        Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
                    } else {
                        checker++;
                        selected_seat.add(position);
                        img.setImageResource(R.drawable.after_booking);
                    }

                }
                //don't delete //working code

            }
        });


        return v;
    }
}
