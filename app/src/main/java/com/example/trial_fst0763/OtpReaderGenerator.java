package com.example.trial_fst0763;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fst_t0763.R;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.PasswordAuthentication;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


public class OtpReaderGenerator extends Fragment {
    private static final int NotificationID = 100;
    private static final int REQ_USER_CONSENT = 200;
    private static final String ChannleName = "FST_NOTIFICATION";

    SmsBroadCastReceiver smsBroadCastReceiver;


    TextInputEditText otpTextBox;
    Button generate_otp, verify_otp;
    String newgeneratedOTP, generatedOtp, message, lastOTP;
    Random random = new Random();
    public static int OTP_COUNTER = 0;
    public static int OTP_LIMIT = 11;
    Dexter dexter;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_otp_reader_generator, container, false);
        otpTextBox = v.findViewById(R.id.txtBoxOtpReader);
        generate_otp = v.findViewById(R.id.generate_otp);
        verify_otp = v.findViewById(R.id.verify_otp);
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        String phone = telephonyManager.getLine1Number();
        Toast.makeText(getContext(), "Phone number is "+phone, Toast.LENGTH_SHORT).show();
        smartUserConsent();


        generate_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OTP_COUNTER<OTP_LIMIT){
                GenerateOtp();
                smartUserConsent();
                OTP_COUNTER++;
                }else {
                    Toast.makeText(getActivity(), "You have reached the limit", Toast.LENGTH_SHORT).show();
                }

                if (OTP_COUNTER>=10){

                    generate_otp.setClickable(false);
                    generate_otp.setAlpha(0.5f);
                }
            }
        });


        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            verifyOtp();
            }
        });


        return v;


    }

    /*private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), ChannleName)
                .setSmallIcon(R.drawable.otp_icon)
                .setContentTitle("My notification")
                .setContentText("Some Text")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your OTP is: " + newgeneratedOTP))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(NotificationID, builder.build());

    }
*/

    public void verifyOtp() {
        String checker=otpTextBox.getText().toString();
        if (newgeneratedOTP == null) {
            Toast.makeText(getContext(), "No OTP Received", Toast.LENGTH_SHORT).show();
        } else if (checker.equals(newgeneratedOTP)) {
            Toast.makeText(getContext(), "OTP matched", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void smartUserConsent() {
        SmsRetrieverClient client= SmsRetriever.getClient(getContext());
        client.startSmsUserConsent(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode==REQ_USER_CONSENT){
            if ((resultCode==RESULT_OK) && (data !=null)){
                message=data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOTP(message);
            }
         }

    }

    private void getOTP(String message) {

        Pattern patternOfOtp= Pattern.compile("(|^)\\d{6}");
        Matcher matcher = patternOfOtp.matcher(message);
        if (matcher.find()){
            otpTextBox.setText(matcher.group(0));
            newgeneratedOTP=matcher.group(0);
        }
    }

    private void GenerateOtp() {
        generatedOtp = String.valueOf(random.nextInt(1000000));

        if (generatedOtp.length() == 6) {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("8451079482", null, "your OTP is  "+generatedOtp, null, null);

        }else {
            generatedOtp = String.valueOf(random.nextInt(1000000));
        }

    }


    private void registerBroadcast(){
        smsBroadCastReceiver = new SmsBroadCastReceiver();
        smsBroadCastReceiver.smsBroadcastinterface= new SmsBroadCastReceiver.SmsBroadcastinterface() {
            @Override
            public void onSuccess(Intent intent) {
            startActivityForResult(intent,REQ_USER_CONSENT);
            }

            @Override
            public void onFailure() {

            }

        };

        IntentFilter intentFilter=new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        getContext().registerReceiver(smsBroadCastReceiver,intentFilter);

    }

    @Override
    public void onStart() {
        super.onStart();
        registerBroadcast();
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(smsBroadCastReceiver);
    }
}
