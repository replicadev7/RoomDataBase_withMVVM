package com.rahul.roomdatabasemvvm.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.rahul.roomdatabasemvvm.model.UserModal;

import java.util.List;

public class UserRepository {
    private UserDao dao;
    private LiveData<List<UserModal>> allUser;


    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        dao = database.Dao();
        allUser = dao.getAllUsers();
    }

    public void insert(UserModal model) {
        new InsertUserAsyncTask(dao).execute(model);
    }

    public void update(UserModal model) {
        new UpdateUserAsyncTask(dao).execute(model);
    }

    public void delete(UserModal model) {
        new DeleteUserAsyncTask(dao).execute(model);
    }

    public void deleteAllUser() {
        new DeleteAllUserAsyncTask(dao).execute();
    }

    public LiveData<List<UserModal>> getAllUser() {
        return allUser;
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private UserDao dao;

        private InsertUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private UserDao dao;

        private UpdateUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private UserDao dao;

        private DeleteUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... models) {
            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao dao;

        private DeleteAllUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllUser();
            return null;
        }
    }
}