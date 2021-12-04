package com.example.fst_t0763;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class barcode_scanner extends Fragment {


public static TextView scannedData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ImageView scan_icon;

        View v = inflater.inflate(R.layout.fragment_barcode_scanner, container, false);

        scannedData =(TextView) v.findViewById(R.id.showData);
        scan_icon = v.findViewById(R.id.barcode_scanner);


        scan_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),scan_handler.class));
            }
        });


        return v;


    }


}