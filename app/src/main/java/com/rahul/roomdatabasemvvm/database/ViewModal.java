package com.rahul.roomdatabasemvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rahul.roomdatabasemvvm.database.UserRepository;
import com.rahul.roomdatabasemvvm.model.UserModal;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<UserModal>> allCourses;
    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allCourses = repository.getAllCourses();
    }

    public void insert(UserModal model) {
        repository.insert(model);
    }

    public void update(UserModal model) {
        repository.update(model);
    }

    public void delete(UserModal model) {
        repository.delete(model);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }
    public LiveData<List<UserModal>> getAllCourses() {
        return allCourses;
    }
}