package com.rahul.roomdatabasemvvm.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.rahul.roomdatabasemvvm.BitmapManager;
import com.rahul.roomdatabasemvvm.R;
import com.rahul.roomdatabasemvvm.ViewModal;
import com.rahul.roomdatabasemvvm.model.UserModal;

import java.io.IOException;

public class NewUserActivity extends AppCompatActivity {

    private EditText courseNameEdt, courseDescEdt, courseDurationEdt;
    private Button courseBtn;
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_COURSE_NAME = "EXTRA_COURSE_NAME";
    public static final String EXTRA_DESCRIPTION = "EXTRA_COURSE_DESCRIPTION";
    public static final String EXTRA_DURATION = "EXTRA_COURSE_DURATION";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private ViewModal viewmodal;
    Bitmap bitmap;
    ImageView img_profile;
    private static final int SELECT_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        courseBtn = findViewById(R.id.idBtnSaveCourse);
        img_profile = findViewById(R.id.img_profile);

        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            courseNameEdt.setText(intent.getStringExtra(EXTRA_COURSE_NAME));
            courseDescEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            courseDurationEdt.setText(intent.getStringExtra(EXTRA_DURATION));

            byte[] image = intent.getByteArrayExtra(EXTRA_IMAGE);
           bitmap =  BitmapManager.byteToBitmap(image);
            img_profile.setImageBitmap(bitmap);
        }

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = courseNameEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                String courseDuration = courseDurationEdt.getText().toString();
                if (courseName.isEmpty() || courseDesc.isEmpty() || courseDuration.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bitmap == null){
                    Toast.makeText(NewUserActivity.this, "Please add Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] image = BitmapManager.bitmapToByte(bitmap);
                saveCourse(courseName, courseDesc, courseDuration,image);
            }
        });
    }

    private void saveCourse(String courseName, String courseDescription, String courseDuration, byte[] image) {
        UserModal model;
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            model = new UserModal(courseName, courseDescription, courseDuration,image);
            model.setId(id);
            viewmodal.update(model);
        }else {
            model = new UserModal(courseName, courseDescription, courseDuration,image);
            viewmodal.insert(model);
        }
        finish();
        Log.e("rrr", "saveCourse-----id---: "+id );

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    img_profile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}