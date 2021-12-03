package com.example.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ModelClasses.NueclearGetResponseModel;
import com.example.fst_t0763.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class NueclearAPIAdapter extends RecyclerView.Adapter<NueclearAPIAdapter.ViewHolder> {
    ArrayList<NueclearGetResponseModel> serviceMaster;

    public NueclearAPIAdapter(ArrayList<NueclearGetResponseModel> serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    @NonNull
    @NotNull
    @Override
    public NueclearAPIAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_service, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NueclearAPIAdapter.ViewHolder holder, int position) {
        if (!serviceMaster.isEmpty()) {

            NueclearGetResponseModel nueclearGetResponseModel = serviceMaster.get(position);
            holder.single_service.setText(nueclearGetResponseModel.getServiceName());
        } else {
            Toast.makeText(holder.itemView.getContext(), "Data not found", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return serviceMaster.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView single_service;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            single_service = itemView.findViewById(R.id.single_service);
        }
    }
}
