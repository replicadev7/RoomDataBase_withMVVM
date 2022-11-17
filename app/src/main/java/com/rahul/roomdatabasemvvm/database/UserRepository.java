package com.rahul.roomdatabasemvvm.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.rahul.roomdatabasemvvm.model.UserModal;

import java.util.List;

public class UserRepository {
    private UserDao dao;
    private LiveData<List<UserModal>> allCourses;


    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        dao = database.Dao();
        allCourses = dao.getAllCourses();
    }

    public void insert(UserModal model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    public void update(UserModal model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    public void delete(UserModal model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    public LiveData<List<UserModal>> getAllCourses() {
        return allCourses;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private UserDao dao;

        private InsertCourseAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private UserDao dao;

        private UpdateCourseAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private UserDao dao;

        private DeleteCourseAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... models) {
            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao dao;
        private DeleteAllCoursesAsyncTask(UserDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCourses();
            return null;
        }
    }
}