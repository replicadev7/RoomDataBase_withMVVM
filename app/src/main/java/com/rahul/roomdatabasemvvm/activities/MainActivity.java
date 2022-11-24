package com.rahul.roomdatabasemvvm.activities;

import static com.rahul.roomdatabasemvvm.Constant.EXTRA_ID;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_IMAGE;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_USER_DATE;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_USER_NAME;
import static com.rahul.roomdatabasemvvm.Constant.EXTRA_USER_NUMBER;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rahul.roomdatabasemvvm.UserRVAdapter;
import com.rahul.roomdatabasemvvm.database.ViewModal;
import com.rahul.roomdatabasemvvm.databinding.ActivityMainBinding;
import com.rahul.roomdatabasemvvm.model.UserModal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModal viewmodal;
    UserRVAdapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

        binding.idRVCourses.setLayoutManager(new LinearLayoutManager(this));
        binding.idRVCourses.setHasFixedSize(true);
        adapter = new UserRVAdapter(this);
        binding.idRVCourses.setAdapter(adapter);
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

        adapter.setOnItemClickListener(new UserRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModal model) {
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                intent.putExtra(EXTRA_ID, model.getId());
                intent.putExtra(EXTRA_USER_NAME, model.getUserName());
                intent.putExtra(EXTRA_USER_NUMBER, model.getUserNumber());
                intent.putExtra(EXTRA_USER_DATE, model.getUserDate());
                intent.putExtra(EXTRA_IMAGE, model.getImage());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewmodal.getAllUsers().observe(this, new Observer<List<UserModal>>() {
            @Override
            public void onChanged(List<UserModal> models) {
                adapter.submitList(models);
                adapter.notifyDataSetChanged();


            }
        });
    }
}