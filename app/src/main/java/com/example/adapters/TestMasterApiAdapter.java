package com.example.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fst_t0763.R;
import com.example.ModelClasses.TestMasterResponseModel;

import java.util.ArrayList;
import java.util.List;


public class TestMasterApiAdapter extends RecyclerView.Adapter<TestMasterApiAdapter.viewholder> {

    String sample = " ";

    List<TestMasterResponseModel.TestMaster> testMasArraylist;

    Context context;

    public TestMasterApiAdapter(List<TestMasterResponseModel.TestMaster> testMaster, Context context) {
        this.testMasArraylist = testMaster;

        this.context = context;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        TestMasterResponseModel.TestMaster tstratemasterDTO = testMasArraylist.get(position);
        //
        for (int c=0;c<=position;c++){
            holder.rclcount.setText(String.valueOf(position+1));
        }
       /* Toast.makeText(context, "count: "+getItemCount(), Toast.LENGTH_SHORT).show();*/

        //
        holder.fasting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] arr_size = new String[tstratemasterDTO.getSampltype().size()];

                List<String> trial = new ArrayList<>();
                for (int i = 0; i < arr_size.length; i++) {
                    for (int j = 0; j < arr_size.length; j++) {
                        trial.add(j, tstratemasterDTO.getSampltype().get(j).getsampleType());
                    }
                    sample = /*sample + */TextUtils.join(", ", trial);
                }
                Toast.makeText(context, " " + sample + " ", Toast.LENGTH_SHORT).show();
                sample = " ";


            }
        });

        holder.testtype.setText(tstratemasterDTO.getTesttype().toString());
        holder.fasting.setText(tstratemasterDTO.getFasting().toString());

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, tstratemasterDTO.getTestCode(), Toast.LENGTH_SHORT).show();
            }
        });


        holder.str_description = tstratemasterDTO.getDesc();

        if (!holder.str_description.equals("")) {

            holder.description.setText(holder.str_description);

        } else {
            //String trial=tstratemasterDTO.getTestCode();
            holder.description.setText(tstratemasterDTO.getTestCode());
        }


        holder.str_rate = tstratemasterDTO.getRate();
        holder.rate.setText(tstratemasterDTO.getRate());

    }

    @Override
    public int getItemCount() {

        return testMasArraylist.size();

    }


    public static class viewholder extends RecyclerView.ViewHolder {

        TextView testtype, fasting, description, rate, rclcount;
        String str_rate, str_description;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            // rate_table=itemView.findViewById(R.id.rcl_rate_table);
            testtype = itemView.findViewById(R.id.rcl_testtype);
            fasting = itemView.findViewById(R.id.rcl_fasting);
            description = itemView.findViewById(R.id.rcl_description);
            rate = itemView.findViewById(R.id.rcl_rate);
            rclcount = itemView.findViewById(R.id.rclcount);

        }
    }
}
