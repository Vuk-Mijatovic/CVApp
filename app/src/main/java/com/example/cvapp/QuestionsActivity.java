package com.example.cvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private QuestionsViewModel questionsViewModel;
    List<Questions> questionsList;
    TextView questionsView;
    Button optionA;
    Button optionB;
    Button optionC;
    Button optionD;
    Questions currentQuestion;

    int currentQuestionNumber = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        TextView questionNumbers = findViewById(R.id.questionsNumber);
        questionsView = findViewById(R.id.question_view);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);



        questionsViewModel = new ViewModelProvider(this).get(QuestionsViewModel.class);
        questionsViewModel.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                questionsList = questions;
                Collections.shuffle(questionsList);
                questionNumbers.setText("Question: " + currentQuestionNumber
                        + "/" + questionsList.size());
                setQuestion();

            }
        });

        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           

                };
            }

        });

    }

    private void setQuestion() {
        currentQuestion = questionsList.get(currentQuestionNumber);
        questionsView.setText(currentQuestion.getQuestion());
        optionA.setText(currentQuestion.getOptionA());
        optionB.setText(currentQuestion.getOptionB());
        optionC.setText(currentQuestion.getOptionC());
        optionD.setText(currentQuestion.getOptionD());
    }
}