package com.example.cvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private QuestionsViewModel questionsViewModel;
    List<Questions> questionsList;
    TextView questionsView;
    TextView questionNumbers;
    Button optionA;
    Button optionB;
    Button optionC;
    Button optionD;
    Questions currentQuestion;
    TextView scoreView;
    String scoreBoard;
    int currentQuestionNumber = 1;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questionNumbers = findViewById(R.id.questionsNumber);
        questionsView = findViewById(R.id.question_view);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        scoreView = findViewById(R.id.score);
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);


        questionsViewModel = new ViewModelProvider(this).get(QuestionsViewModel.class);
        questionsViewModel.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                questionsList = questions;
                Collections.shuffle(questionsList);
                setQuestion();

            }
        });

        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionA.startAnimation(buttonClick);
               String answer = optionA.getText().toString();
               checkAnswer(answer);
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionB.startAnimation(buttonClick);
                String answer = optionA.getText().toString();
                checkAnswer(answer);
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionC.startAnimation(buttonClick);
                String answer = optionA.getText().toString();
                checkAnswer(answer);
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionD.startAnimation(buttonClick);
                String answer = optionD.getText().toString();
                checkAnswer(answer);
            }
        });
    }

    private void gameOver() {
        View snackBarBaseView = findViewById(R.id.acivity_question);
        Snackbar.make(snackBarBaseView, "Game Over", BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Play again?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        }).show();
    }

    private void startNewGame() {
        Collections.shuffle(questionsList);
        currentQuestionNumber = 1;
        score = 0;
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);
        setQuestion();
        //todo finish game over logic
    }

    private void setQuestion() {
        String text = getString(R.string.question_number,
                currentQuestionNumber, questionsList.size());
        questionNumbers.setText(text);
        currentQuestion = questionsList.get(currentQuestionNumber - 1);
        questionsView.setText(currentQuestion.getQuestion());
        optionA.setText(currentQuestion.getOptionA());
        optionB.setText(currentQuestion.getOptionB());
        optionC.setText(currentQuestion.getOptionC());
        optionD.setText(currentQuestion.getOptionD());
    }

    private void checkAnswer(String answer) {
        if (answer.equals(currentQuestion.getAnswer())) {
            Toast.makeText(QuestionsActivity.this, "Great job!", Toast.LENGTH_SHORT).show();
            currentQuestionNumber++;
            score++;
            scoreBoard = getString(R.string.score_board, score);
            scoreView.setText(scoreBoard);
            if (currentQuestionNumber < questionsList.size() + 1) {
                setQuestion();
            } else {
                gameOver();
            }
        } else {
            Toast.makeText(QuestionsActivity.this, "That is wrong. Try again.", Toast.LENGTH_SHORT).show();
            //todo finish wrong answer logic
        }
    }
}
