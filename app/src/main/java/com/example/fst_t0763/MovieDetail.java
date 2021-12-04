package com.example.fst_t0763;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;


public class MovieDetail extends Fragment {
    FrameLayout frameLayout;
    Integer positionID;
    ImageView Movieimage;
    TextView MovieTitle,MovieDescription;
    Button BookTicket;
    Integer slide=1;
    String ticketNumbers;

    public MovieDetail(Integer positionID) {
        this.positionID = positionID;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Movieimage=v.findViewById(R.id.Movie_icon);
        MovieTitle=v.findViewById(R.id.text_movie_title);
        MovieDescription=v.findViewById(R.id.text_movie_description);
        BookTicket=v.findViewById(R.id.book_ticket_button);
        frameLayout=v.findViewById(R.id.movie_detail_frame);
        setData(positionID);
        AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();

        BookTicket.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
              /*  frameLayout.removeAllViews();
                NumberOfTicket numberOfTicket=new NumberOfTicket();*/

                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.popup_ticketbook);
                AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();
                appCompatActivity.overridePendingTransition(android.R.style.Widget_DeviceDefault_Light_PopupWindow,android.R.id.accessibilityActionScrollDown);

                Slider sliderRef=bottomSheetDialog.findViewById(R.id.ticket_slider);
                ImageView imageView=bottomSheetDialog.findViewById(R.id.ticket_image);
                Button bookTicket=bottomSheetDialog.findViewById(R.id.book_tickets);

                TextView nooftickets=bottomSheetDialog.findViewById(R.id.nooftickets);

                sliderRef.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                    @Override
                    public void onStartTrackingTouch(@NonNull Slider slider) {
                    }

                    @Override
                    public void onStopTrackingTouch(@NonNull Slider slider) {
                        switch (Math.round(slider.getValue())){

                            case 1:
                                imageView.setImageResource(R.drawable.cycle);
                                break;
                            case 2:
                                imageView.setImageResource(R.drawable.bike);
                                break;
                            case 3:
                                imageView.setImageResource(R.drawable.rickshaw);
                                break;
                            case 4:
                                imageView.setImageResource(R.drawable.car);
                                break;
                            case 5:
                                imageView.setImageResource(R.drawable.truck);
                                break;
                        }
                        nooftickets.setText("No of Tickets are "+String.valueOf(Math.round(slider.getValue())));
                            slide=Math.round(slider.getValue());
                    }
                });


                bookTicket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    ticketNumbers=nooftickets.getText().toString();
                    Integer totalTickets=slide;

                        SeatBooking seatBooking = new SeatBooking(totalTickets);
                        frameLayout.removeAllViews();
                        AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_frame, seatBooking).commit();
                        Toast.makeText(getContext(), String.valueOf(totalTickets)  , Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();

                  /*  else   {
                        Toast.makeText(getContext(), "Please select at least one ticket", Toast.LENGTH_SHORT).show();

                    }*/
                    }
                });


                bottomSheetDialog.show();





            }
        });

        return v;
    }




    private void setData(Integer positionID) {

        switch (positionID){

            case 0:
                Movieimage.setImageResource(R.drawable.m1);
                MovieTitle.setText("Phir Hera Pheri");
                MovieDescription.setText(getString(R.string.m1_description));
                break;

            case 1:
                Movieimage.setImageResource(R.drawable.m2);
                MovieTitle.setText("Golmaal");
                MovieDescription.setText(getString(R.string.m2_description));
                break;
            case 2:
                Movieimage.setImageResource(R.drawable.m3);
                MovieTitle.setText("The Dictator");
                MovieDescription.setText(getString(R.string.m3_description));
                break;

            case 3:
                Movieimage.setImageResource(R.drawable.m4);
                MovieTitle.setText("Ace Ventura");
                MovieDescription.setText(getString(R.string.m4_description));
                break;
            case 4:
                Movieimage.setImageResource(R.drawable.m5);
                MovieTitle.setText("Inception");
                MovieDescription.setText(getString(R.string.m5_description));
                break;

        }

    }


}