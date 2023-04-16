package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Database.UserDatabase;
import com.example.myapplication.Repository.UserRepository;
import com.example.myapplication.Utils.Models.User;

import java.util.List;

public class UserVM extends AndroidViewModel {
    private UserDatabase db;
    private UserRepository userRepo;
    public UserVM(@NonNull Application application) {
        super(application);
        userRepo = new UserRepository(application);
    }

    public void insertUser(User user){
        userRepo.insertUser(user);
    }

    public void deleteUser(User user){
        userRepo.deleteUser(user);
    }

    public void deleteAllUser(){
        userRepo.deleteAllUsers();
    }
    public LiveData<List<User>> getAllUsers(){
        return userRepo.getAll();
    }


}
