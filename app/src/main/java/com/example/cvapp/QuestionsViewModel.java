package com.example.cvapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsViewModel extends AndroidViewModel {

    private QuestionsRepository repository;
    private LiveData<List<Questions>> allQuestions;

    public QuestionsViewModel(Application application) {
        super(application);
        repository = new QuestionsRepository(application);
        allQuestions = repository.getAllQuestions();
    }
    LiveData<List<Questions>> getAllQuestions() {
        return allQuestions;
    }
}
