package com.rahul.roomdatabasemvvm.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rahul.roomdatabasemvvm.R;
import com.rahul.roomdatabasemvvm.databinding.ActivityPermissionBinding;

import java.util.Arrays;

public class PermissionAct extends AppCompatActivity {

    public int percnt = 0;
    ActivityPermissionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        percnt = 0;

    }

    public void PermissionClick(View view) {
        switch (view.getId()) {
            case R.id.grant_access:
                if (permissionAlreadyGranted()) {
                    IntentMAin();
                    return;
                }
                requestPermission();
                break;
        }
    }

    public void IntentMAin() {
        startActivity(new Intent(PermissionAct.this, MainActivity.class));
        finish();
    }


    private boolean permissionAlreadyGranted() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

    }

    public void permissiondialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Required Permission");
        builder.setCancelable(false);
        builder.setMessage("Give permission to access storage audio file")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (permissionAlreadyGranted()) {
                            return;
                        }
                        requestPermission();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Required Permission");
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 0) {
                return;
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted successfully", Toast.LENGTH_SHORT).show();
                IntentMAin();
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
                if (Build.VERSION.SDK_INT >= 30) {
                    if (percnt >= 1) {
                        openSettingsDialog();
                    } else {
                        requestPermission();
                    }
                    percnt++;
                } else {
                    if (!showRationale && !showRationale1) {
                        openSettingsDialog();
                    } else {
                        permissiondialog();
                    }
                }
            }
        }
    }
    Dialog settingsDialog;

    private void openSettingsDialog() {
        settingsDialog = new Dialog(PermissionAct.this);
        settingsDialog.setContentView(R.layout.setting_dialog);
        settingsDialog.getWindow().setGravity(Gravity.CENTER);
        settingsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingsDialog.setCancelable(true);
        settingsDialog.setCanceledOnTouchOutside(false);
        settingsDialog.show();

        LinearLayout btn_cancel = settingsDialog.findViewById(R.id.btn_cancel);
        LinearLayout btn_setting = settingsDialog.findViewById(R.id.btn_setting);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
               finish();
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (permissionAlreadyGranted()) {
                IntentMAin();
                return;
            }
            requestPermission();
        }
    }
}