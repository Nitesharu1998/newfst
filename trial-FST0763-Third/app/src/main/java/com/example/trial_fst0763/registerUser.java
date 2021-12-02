package com.example.trial_fst0763;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fst_t0763.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.transition.MaterialElevationScale;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class registerUser extends AppCompatActivity {
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private static int SELECT_PICTURE = 2;
    private static int PICK_PICTURE = 1;
    TextInputEditText fname, lname, uname, email, pass, phone;
    Button regUser;
    String strmail, strpass, struname, strfname, strlname, strphone, timestamp, imageFilename, photoPath;
    CircleImageView profilepic;
    Uri SelectedImage;
//    Intent intent;
    TextView registerr;
    byte[] img;
    /*File file;
    Bitmap bitmap;
    ProgressDialog progressDialog;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        fname = findViewById(R.id.regFirstName);
        lname = findViewById(R.id.regLastName);
        uname = findViewById(R.id.regUserName);
        email = findViewById(R.id.regEmail);
        pass = findViewById(R.id.regPass);
        phone = findViewById(R.id.regPhone);
        registerr = findViewById(R.id.regText);
        profilepic = findViewById(R.id.profile_image);
        DBHelper helper = new DBHelper(this);
        regUser = findViewById(R.id.regButton);
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!checkmail() | !checkpass() | !checkphone() | !uname() | !lname() | !fname()) {
                    Toast.makeText(registerUser.this, "check the fields again", Toast.LENGTH_SHORT).show();

                } else {


                    Boolean checkUname = helper.checkUname(struname);
                    Boolean checkUphone = helper.checkUphone(strphone);

                    if (checkUname || checkUphone) {
                        Toast.makeText(registerUser.this, "user exist, please login", Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        Boolean insert = helper.regInsertUser(strfname, strlname, struname, strpass, strmail, strphone, img);
                        if (insert) {
                            try {


                                Log.i("data", strfname + strlname + struname + strmail + strpass + strphone);
                                if (insert) {
                                    Log.i("data", strfname + strlname + struname + strmail + strpass + strphone);
                                    Toast.makeText(registerUser.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(registerUser.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(registerUser.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.i("db error", e.getLocalizedMessage());
                            }
                        } else {
                            Toast.makeText(registerUser.this, "Inserting data failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        });


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ImagePicker.Companion.with(registerUser.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .cropOval()				//Allow dimmed layer to have a circle inside
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();*/
                selectImage();
            }
        });
    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(registerUser.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Dexter.withContext(registerUser.this).withPermission(Manifest.permission.CAMERA)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                        /*// Create the File where the photo should go
                                        File photoFile = null;
                                        photoFile = createImageFile();
                                        // Continue only if the File was successfully created
                                        if (photoFile != null) {*/
                                           // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                            startActivityForResult(cameraIntent, PICK_PICTURE);
                                        }
                                    }


                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                    dialog.dismiss();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }
                            }).check();


                } else if (options[item].equals("Choose from Gallery")) {

                    Dexter.withContext(registerUser.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                    startActivityForResult(i, SELECT_PICTURE);


                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                    profilepic.setImageResource(R.drawable.def_profle);
                                    return;
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }
                            }).check();

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == PICK_PICTURE) {
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bmp= (Bitmap) data.getExtras().get("data");

                    /*progressDialog = new ProgressDialog(getApplicationContext());
                    progressDialog.show();
                    progressDialog.setTitle("please wait");*/


                        profilepic.setImageBitmap(bmp);
                        convertToByte ctb=new convertToByte();
                       img=ctb.getBytes(bmp);


                }
            } else if (resultCode == Activity.RESULT_OK) {

                if (requestCode == SELECT_PICTURE) {
                    try {
                        if (resultCode== Activity.RESULT_OK) {
                           SelectedImage=data.getData();
                            Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), SelectedImage);
                            convertToByte ctb = new convertToByte();
                            img = ctb.getBytes(bmp);
                            profilepic.setImageURI(SelectedImage);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                }
            }




    public void tologin(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }


    public boolean checkmail() {
        strmail = email.getText().toString().replaceAll("//s", null).trim();
        email.setText(strmail);

        if (Patterns.EMAIL_ADDRESS.matcher(strmail).matches() & strmail != null) {
            email.setError(null);
            return true;
        } else {
            email.setError("Invalid mail id");
            email.requestFocus();

            return false;
        }
    }

    public boolean checkpass() {
        strpass = pass.getText().toString().replaceAll("//s", "").trim();
        pass.setText(strpass);
        if (strpass.length() >= 8) {
            pass.setError(null);

            return true;
        } else {
            pass.setError("password is too short");
            pass.requestFocus();
            return false;
        }
    }

    public boolean uname() {
        struname = uname.getText().toString().replaceAll("//s", "").trim();
        uname.setText(struname);
        if (struname.length() > 2 && struname.length() < 10) {
            uname.setError(null);
            return true;
        } else {
            uname.setError("username should be longer than 2 chars");
            pass.requestFocus();
            return false;
        }
    }

    public boolean fname() {


        strfname = fname.getText().toString().replaceAll("//s", "").trim();
        fname.setText(strfname);

        if (!strfname.isEmpty() && strfname.length() > 2 && strfname.length() < 10) {
            fname.setError(null);
            return true;
        } else {
            fname.setError("First name should be longer than 2 chars");
            fname.requestFocus();
            return false;
        }
    }

    public boolean lname() {
        strlname = lname.getText().toString().replaceAll("//s", null).trim();
        lname.setText(strlname);


        if (!strlname.isEmpty() & strlname.length() > 2 & strlname.length() < 10) {
            lname.setError(null);
            return true;
        } else {
            lname.setError("username should be longer than 2 chars");
            lname.requestFocus();

            return false;
        }
    }


    public boolean checkphone() {
        strphone = phone.getText().toString();
        Pattern pattern = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$");
        Matcher match = pattern.matcher(strphone);

        if (match.find() && match.group().equals(strphone) && strphone != null) {
            phone.setError(null);
            return true;
        } else {
            phone.setError("please check phone number");
            phone.requestFocus();
            return false;
        }
    }


}
