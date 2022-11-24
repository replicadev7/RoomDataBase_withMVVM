package com.rahul.roomdatabasemvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rahul.roomdatabasemvvm.model.UserModal;

import java.util.List;

@androidx.room.Dao
public interface UserDao {
    @Insert
    void insert(UserModal model);
    @Update
    void update(UserModal model);
    @Delete
    void delete(UserModal model);
    @Query("DELETE FROM user_table")
    void deleteAllUser();
    @Query("SELECT * FROM user_table ORDER BY userDate DESC")
    LiveData<List<UserModal>> getAllUsers();
}
