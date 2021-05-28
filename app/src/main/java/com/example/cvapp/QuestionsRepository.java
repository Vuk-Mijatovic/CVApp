package com.example.cvapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsRepository {

    private QuestionsDao questionsDao;
    private LiveData<List<Questions>> allQuestions;

    public QuestionsRepository(Application application) {
        QuestionsDatabase db = QuestionsDatabase.getInstance(application);
        questionsDao = db.questionsDao();
        allQuestions = questionsDao.getAllQuestions();
    }

    public LiveData<List<Questions>> getAllQuestions(){
        return allQuestions;
    }
}
