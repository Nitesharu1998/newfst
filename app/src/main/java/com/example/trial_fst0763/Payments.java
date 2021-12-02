package com.example.trial_fst0763;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fst_t0763.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.trial_fst0763.MainActivity.PREFERENCE;

public class Payments extends AppCompatActivity implements PaymentResultListener {
    Button pay;
    ArrayList<Integer> Tickets = new ArrayList<>();
    ImageView movie_image;
    TextView movie_name,movie_tickets,movie_pay;
    int temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        pay = findViewById(R.id.payment);
        movie_image=findViewById(R.id.payment_movie_image);
        movie_name=findViewById(R.id.payment_movie_name);
        movie_tickets=findViewById(R.id.payment_movie_seats);
        movie_pay=findViewById(R.id.payment_movie_pay);


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("TicketData");
        Tickets = (ArrayList<Integer>) args.getSerializable("ARRAYLIST");
        String tickets= TextUtils.join(" ,",Tickets);
        temp=Tickets.size()*30000;

        movie_pay.setText("Total is "+temp/100);
        movie_tickets.setText("Your seats: "+tickets);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        Integer position = Integer.valueOf(sharedPreferences.getString("position", null));
        if (position != null) {
            switch (position){

                case 0:
                    movie_image.setImageResource(R.drawable.m1);
                    movie_name.setText("Phir Hera Pheri");
                    break;

                case 1:
                    movie_image.setImageResource(R.drawable.m2);
                    movie_name.setText("Golmaal");
                    break;

                case 2:
                    movie_image.setImageResource(R.drawable.m3);
                    movie_name.setText("The Dictator");
                    break;

                case 3:
                    movie_image.setImageResource(R.drawable.m4);
                    movie_name.setText("Ace Ventura");
                    break;
                case 4:
                    movie_image.setImageResource(R.drawable.m5);
                    movie_name.setText("Inception");
                    break;




            }

        }


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_2ZOfwHd33o5AYD");
        /**
         * Instantiate Checkout
         */


        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.rzp_name_logo);

        /**
         * Reference to current activity
         */

        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            /*   options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.*/
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", temp);//pass amount in currency subunits
            options.put("prefill.email", "Nitesh.aru@gmail.com");
            options.put("prefill.contact", "8451079482");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Payments.this, "Payment successful", Toast.LENGTH_SHORT).show();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("8451079482", null, "Yout payment of ₹ "+temp/100+" was successful", null, null);




    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Payments.this, "Payment not completed", Toast.LENGTH_LONG).show();

    }
}