package com.rahul.roomdatabasemvvm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rahul.roomdatabasemvvm.CourseRVAdapter;
import com.rahul.roomdatabasemvvm.R;
import com.rahul.roomdatabasemvvm.ViewModal;
import com.rahul.roomdatabasemvvm.model.UserModal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView coursesRV;
    private ViewModal viewmodal;
    CourseRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesRV = findViewById(R.id.idRVCourses);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);
        adapter = new CourseRVAdapter();
        coursesRV.setAdapter(adapter);
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(MainActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(coursesRV);
        adapter.setOnItemClickListener(new CourseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModal model) {
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                intent.putExtra(NewUserActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewUserActivity.EXTRA_COURSE_NAME, model.getCourseName());
                intent.putExtra(NewUserActivity.EXTRA_DESCRIPTION, model.getCourseDescription());
                intent.putExtra(NewUserActivity.EXTRA_DURATION, model.getCourseDuration());
                intent.putExtra(NewUserActivity.EXTRA_IMAGE, model.getImage());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewmodal.getAllCourses().observe(this, new Observer<List<UserModal>>() {
            @Override
            public void onChanged(List<UserModal> models) {
                adapter.submitList(models);
                adapter.notifyDataSetChanged();


            }
        });
    }
}