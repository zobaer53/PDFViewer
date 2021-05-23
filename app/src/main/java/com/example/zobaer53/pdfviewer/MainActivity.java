package com.example.zobaer53.pdfviewer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PDF_CODE = 1000;

    Button buttonAsset, buttonDevice, buttonLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new BaseMultiplePermissionsListener(){

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                super.onPermissionsChecked(report);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                super.onPermissionRationaleShouldBeShown(permissions, token);
            }
        }).check();

        buttonAsset = findViewById(R.id.viewAsset);
        buttonDevice = findViewById(R.id.buttonViewDevice);
        buttonLink = findViewById(R.id.buttonViewInternet);

        buttonAsset.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
            intent.putExtra("ViewType", "assets");
            startActivity(intent);
        });

        buttonDevice.setOnClickListener(v -> {
            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF, "Select PDF"), PICK_PDF_CODE);
        });

        buttonLink.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    intent.putExtra("ViewType", "internet");
                    startActivity(intent);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedPDF = data.getData();
            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
            intent.putExtra("ViewType", "storage");
            intent.putExtra("FileUri", selectedPDF.toString());
            startActivity(intent);
        }
    }
}