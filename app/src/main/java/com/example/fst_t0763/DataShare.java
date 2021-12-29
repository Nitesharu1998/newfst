package com.example.fst_t0763;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DataShare extends AppCompatActivity {

    EditText sharedata;
    Button share;
    ImageView whatsapp,msg,mail;
    LayoutInflater inflater;
    View v;
    String contact = "+91 9594427824"; // use country code with your phone number
    String url = "https://api.whatsapp.com/send?phone=" + contact;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_share);

        sharedata=findViewById(R.id.sharedata_string);
       
        share=findViewById(R.id.shareButton);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                v=inflater.inflate(R.layout.shareview,null);
                final PopupWindow popup=new PopupWindow(v,
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true) ;

                whatsapp=v.findViewById(R.id.share_whatsapp);
                mail=v.findViewById(R.id.share_mail);
                msg=v.findViewById(R.id.share_message);

                msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();


                    }
                });

                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                        Intent intent= new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.setData(Uri.parse("mailto:jorgesys12@gmail.com"));
                        intent.putExtra(Intent.EXTRA_TEXT,checkText());
                        intent.setType("message/rfc882");
                        startActivity(intent);
                    }
                });


                whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       Intent intent=new Intent(Intent.ACTION_SEND);
                       intent.putExtra(Intent.EXTRA_TEXT,checkText());
                       try {
                           popup.dismiss();
                           PackageManager manager= DataShare.this.getPackageManager();
                           manager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
                           intent.setData(Uri.parse(url));
                           startActivity(intent);

                      }catch (Exception e){
                           popup.dismiss();
                           e.printStackTrace();
                       }

                    }
                });

                popup.showAtLocation(v, Gravity.CENTER,0,0);
            }
        });

    }
    private String checkText(){
        String test = sharedata.getText().toString();
        return test.isEmpty()? "sample Data": test;
    }
}