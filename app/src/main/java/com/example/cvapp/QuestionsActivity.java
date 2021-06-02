package com.example.cvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
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


        QuestionsViewModel questionsViewModel = new ViewModelProvider(this).get(QuestionsViewModel.class);
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
                if (checkAnswer(answer)) {
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                            optionA.setBackground(getDrawable(R.drawable.default_option_bkg));
                        }
                    }, 1000);
                } else {
                    setViewsWrongAnswer(answer, optionA);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1000);
                }
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionB.startAnimation(buttonClick);
                String answer = optionB.getText().toString();
                if (checkAnswer(answer)) {
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                            optionB.setBackground(getDrawable(R.drawable.default_option_bkg));
                        }
                    }, 1000);
                } else {
                    setViewsWrongAnswer(answer, optionB);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1000);
                }
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionC.startAnimation(buttonClick);
                String answer = optionC.getText().toString();
                if (checkAnswer(answer)) {
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                        }
                    }, 1000);
                } else {
                    setViewsWrongAnswer(answer, optionC);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1000);
                }
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionD.startAnimation(buttonClick);
                String answer = optionD.getText().toString();
                if (checkAnswer(answer)) {
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                        }
                    }, 1000);
                } else {
                    setViewsWrongAnswer(answer, optionD);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1000);
                }
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
        makeOptionsVisible();
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

    private boolean checkAnswer(String answer) {
        if (answer.equals(currentQuestion.getAnswer()))
            return true;
        else return false;
    }

    private void rightAnswer() {
        currentQuestionNumber++;
        score++;
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);
        if (currentQuestionNumber < questionsList.size() + 1) {
            setQuestion();
            makeOptionsVisible();
        } else {
            gameOver();
        }
    }

    private void wrongAswer() {
        currentQuestionNumber++;
        setDefaultBackground();
        if (currentQuestionNumber < questionsList.size() + 1) {
            setQuestion();
            makeOptionsVisible();
        } else {
            gameOver();
        }
    }

    private void makeOptionsVisible() {
        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);
        optionD.setVisibility(View.VISIBLE);
    }

    private void setDefaultBackground() {
        Button[] buttons = {optionA, optionB, optionC, optionD};
        for (Button b : buttons) {
            b.setBackground(getDrawable(R.drawable.default_option_bkg));
        }
    }

    private void setViewsRightAnswer(String answer) {
        Button[] buttons = {optionA, optionB, optionC, optionD};
        for (Button b : buttons) {
            if (b.getText().toString().equals(answer)) {
                b.setBackground(getDrawable(R.drawable.right_answer_bkg));
            } else {
                b.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setViewsWrongAnswer(String answer, Button current) {
        Button[] buttons = {optionA, optionB, optionC, optionD};
        for (Button b : buttons) {
            if (b == current) b.setBackground(getDrawable(R.drawable.wrong_answer_bkg));
            else if (b.getText().toString().equals(currentQuestion.getAnswer()))
                b.setBackground(getDrawable(R.drawable.right_answer_bkg));
            else b.setVisibility(View.INVISIBLE);
        }
    }

}
