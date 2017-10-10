package com.example.jock.jeim_main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class CamaraActivity extends AppCompatActivity implements BarcodeCallback{

    private CompoundBarcodeView barcodeView;
    static final int BarcodeResult = 0;
    static final int OrdinaryResult = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_camera);
        barcodeView = (CompoundBarcodeView) findViewById(R.id.Camara_barcode_scanner);
        barcodeView.decodeContinuous(this);
    }

    @Override
    public void onResume() {
        barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }

    //바코드 인식후 결과값 콜벡매소드
    @Override
    public void barcodeResult(BarcodeResult result) {
        barcodeView.pause();

        Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
        intent.putExtra("result",result.getText().toString());
        setResult(BarcodeResult,intent);
        finish();
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
        setResult(OrdinaryResult,intent);
        finish();
    }
}
