package com.example.myapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.Utils.Models.User;

import java.util.List;

@Dao
public interface UserDao{
    @Query("SELECT * FROM TB_USER")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM TB_USER WHERE ID IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM TB_USER")
    void deleteAll();
}

