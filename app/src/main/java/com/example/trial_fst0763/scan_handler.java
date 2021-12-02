package com.example.trial_fst0763;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scan_handler extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scan_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        scan_handler = new ZXingScannerView(this);
        setContentView(scan_handler);
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA)
        .withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                scan_handler.startCamera();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                scan_handler.stopCamera();
                finish();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        String sample = rawResult.getText();
        barcode_scanner.scannedData.setText(sample);
        onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scan_handler.stopCamera();

    }

    @Override
    protected void onResume() {
        super.onResume();
        scan_handler.setResultHandler(this);
        scan_handler.startCamera();

    }
}