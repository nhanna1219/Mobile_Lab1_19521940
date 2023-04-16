package com.example.myapplication.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.DAO.UserDao;
import com.example.myapplication.Database.UserDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    LiveData<List<User>> users;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.userDao();
        users = userDao.getAll();
    }

    public void insertUser(User user){
        executor.execute(new Runnable(){

            @Override
            public void run() {
                userDao.insert(user);
            }
        });
    }

    public void deleteUser(User user){
        executor.execute(new Runnable(){

            @Override
            public void run() {
                userDao.delete(user);
            }
        });
    }

    public void deleteAllUsers(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAll();
            }
        });
    }

    public LiveData<List<User>> getAll(User... user){
        return users;
    }

}
