package com.example.fst_t0763;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/*import com.example.adapters.Grid_Adapter;*/
import com.example.adapters.Movies_Adapter;
import com.example.fst_t0763.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.*;


public class BookMovie extends Fragment {

    RecyclerView recyclerView;
    public ArrayList<String> MovieTitles = new ArrayList<String>(Arrays.asList("Phir Hera Pheri", "Golmaal", "The Dictator", "Ace Ventura", "Inception"));
    Button changeLayout;
    List<Integer> MoviesIcons= Arrays.asList(R.drawable.m1,
            R.drawable.m2,
            R.drawable.m3,
            R.drawable.m4,
            R.drawable.m5
    );
    Boolean switcher=false;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_movie, container, false);
        recyclerView=v.findViewById(R.id.movie_recyclerView);
        changeLayout=v.findViewById(R.id.changeLayout);



        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(new Movies_Adapter(getContext(),MovieTitles,MoviesIcons));
        recyclerView.setLayoutManager(linearLayoutManager);
        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!switcher){
                    switcher=true;
                    recyclerView.setAdapter(new Movies_Adapter(getContext(),MovieTitles,MoviesIcons));
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
                else {
                    switcher=false;

                    recyclerView.setAdapter(new Movies_Adapter(getContext(),MovieTitles,MoviesIcons));
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);





                }


            }
        });



       setAdapter();

        return v;

    }

    public void setAdapter(){
        recyclerView.setAdapter(new Movies_Adapter(getContext(),MovieTitles,MoviesIcons));

    }
}