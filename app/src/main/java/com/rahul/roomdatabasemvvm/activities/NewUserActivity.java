package com.rahul.roomdatabasemvvm.activities;

import static com.rahul.roomdatabasemvvm.Constant.EXTRA_ID;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_IMAGE;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_USER_DATE;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_USER_NAME;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_USER_NUMBER;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.rahul.roomdatabasemvvm.BitmapManager;
import com.rahul.roomdatabasemvvm.database.ViewModal;
import com.rahul.roomdatabasemvvm.databinding.ActivityNewUserBinding;
import com.rahul.roomdatabasemvvm.model.UserModal;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewUserActivity extends AppCompatActivity {

    private ViewModal viewmodal;
    Bitmap bitmap;
    private static final int SELECT_PICTURE = 1;
    private ActivityNewUserBinding binding;
    String userDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            binding.idEdtUserName.setText(intent.getStringExtra(EXTRA_USER_NAME));
            binding.idEdtUserNumber.setText(intent.getStringExtra(EXTRA_USER_NUMBER));
            binding.idEdtUserDate.setText(intent.getStringExtra(EXTRA_USER_DATE));

            byte[] image = intent.getByteArrayExtra(EXTRA_IMAGE);
            bitmap = BitmapManager.byteToBitmap(image);
            binding.imgProfile.setImageBitmap(bitmap);
        }

        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        binding.idEdtUserDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.idEdtUserDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        binding.idBtnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String username = binding.idEdtUserName.getText().toString();
                String userNumber = binding.idEdtUserNumber.getText().toString();
                userDate = binding.idEdtUserDate.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, "Please add user name !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userNumber.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, "Please add user Number !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userDate.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, "Please add user Date !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userNumber.length() < 10) {
                    Toast.makeText(NewUserActivity.this, "Please add 10 digit Number !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap == null) {
                    Toast.makeText(NewUserActivity.this, "Please add Image !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidDate(userDate)) {
                    Toast.makeText(NewUserActivity.this, "Please add Valid Date !", Toast.LENGTH_SHORT).show();
                    return;
                }

                byte[] image = BitmapManager.bitmapToByte(bitmap);
                saveCourse(username, userNumber, userDate, image);
            }
        });
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private void saveCourse(String username, String userNumber, String userDate, byte[] image) {
        UserModal model;
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            model = new UserModal(username, userNumber, userDate, image);
            model.setId(id);
            viewmodal.update(model);
        } else {
            model = new UserModal(username, userNumber, userDate, image);
            viewmodal.insert(model);
        }
        finish();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    binding.imgProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}