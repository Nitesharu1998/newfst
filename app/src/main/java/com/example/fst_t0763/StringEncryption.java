package com.example.fst_t0763;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controllers.EncryptionUtil;
import com.google.android.material.textfield.TextInputEditText;

public class StringEncryption extends Fragment {
    TextInputEditText textData;
    Button encryptButton;
    TextView encryptedText;
    boolean checker=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_stringencryption, container, false);
        initView(v);
        initListner();
        return v;
    }

    private void initView(View v) {
    textData=v.findViewById(R.id.textData);
    encryptButton=v.findViewById(R.id.encryptButton);
    encryptedText=v.findViewById(R.id.encryptedText);
    }
    private void initListner() {
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enc_dec;
                String data= textData.getText().toString();
                if (!data.isEmpty()){
                    if (checker){
                        checker=false;
                        enc_dec=EncryptionUtil.encrypt(data);

                        System.out.println("Encrypted>>>>>>>>>>>>>>>"+EncryptionUtil.encrypt(data));
                    }else {
                        enc_dec=EncryptionUtil.decrypt(data);
                    }
                    encryptedText.setText("Encrypted"+ enc_dec);

                }else{
                    Toast.makeText(getContext(), "Enter Data to encrypt", Toast.LENGTH_SHORT).show(); 
                }
            }
        });

    }

}