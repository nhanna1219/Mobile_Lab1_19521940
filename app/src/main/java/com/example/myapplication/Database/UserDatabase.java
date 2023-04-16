package com.example.myapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.DAO.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;
    public abstract UserDao userDao();
    public static synchronized UserDatabase getInstance(Context ctx){
        if (instance == null){
            instance = Room.databaseBuilder(ctx.getApplicationContext(), UserDatabase.class,
                            "UserManagement")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
