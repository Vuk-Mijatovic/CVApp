package com.example.cvapp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionsDao {

    @Query("SELECT * FROM questions_table")
    LiveData<List<Questions>> getAllQuestions();

    @Insert
    void insert(Questions questions);
}



