package com.example.cvapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Locale;

public class QuestionsActivity extends AppCompatActivity {

    List<Questions> questionsList;
    TextView questionsView;
    TextView questionNumbers;
    TextView textViewCountDownTimer;
    Button optionA;
    Button optionB;
    Button optionC;
    Button optionD;
    Questions currentQuestion;
    TextView scoreView;
    String scoreBoard;
    AlertDialog dialog;
    int currentQuestionNumber = 1;
    int score = 0;
    private PlayAudio playAudio;
    private static final long COUNTDOWN = 30000;
    private static final int RED_ALERT = 10000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private boolean gameInProgress;
    private long countAfterPause;


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
        makeOptionsInvisible();
        scoreView = findViewById(R.id.score);
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);
        playAudio = new PlayAudio(this);

        QuestionsViewModel questionsViewModel = new ViewModelProvider(this).get(QuestionsViewModel.class);
        questionsViewModel.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                questionsList = questions;
                startNewGame();
            }
        });

        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                optionA.startAnimation(buttonClick);
                String answer = optionA.getText().toString();
                if (checkAnswer(answer)) {
                    setViewsRightAnswer(answer);
                    playAudio.playSound(R.raw.geek);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                            optionA.setBackground(getDrawable(R.drawable.default_option_bkg));
                        }
                    }, 1500);
                } else {
                    playAudio.playSound(R.raw.oh_no);
                    setViewsWrongAnswer(answer, optionA);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1500);
                }
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                optionB.startAnimation(buttonClick);
                String answer = optionB.getText().toString();
                if (checkAnswer(answer)) {
                    playAudio.playSound(R.raw.geek);
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                            optionB.setBackground(getDrawable(R.drawable.default_option_bkg));
                        }
                    }, 1500);
                } else {
                    playAudio.playSound(R.raw.oh_no);
                    setViewsWrongAnswer(answer, optionB);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1500);
                }
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                optionC.startAnimation(buttonClick);
                String answer = optionC.getText().toString();
                if (checkAnswer(answer)) {
                    playAudio.playSound(R.raw.geek);
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                            optionC.setBackground(getDrawable(R.drawable.default_option_bkg));

                        }
                    }, 1500);
                } else {
                    playAudio.playSound(R.raw.oh_no);
                    setViewsWrongAnswer(answer, optionC);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1500);
                }
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                optionD.startAnimation(buttonClick);
                String answer = optionD.getText().toString();
                if (checkAnswer(answer)) {
                    playAudio.playSound(R.raw.geek);
                    setViewsRightAnswer(answer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rightAnswer();
                            optionD.setBackground(getDrawable(R.drawable.default_option_bkg));
                        }
                    }, 1500);
                } else {
                    playAudio.playSound(R.raw.oh_no);
                    setViewsWrongAnswer(answer, optionD);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wrongAswer();
                        }
                    }, 1500);
                }
            }
        });
    }

    private void gameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.final_score_dialog, null);
        TextView finalScoreView = customLayout.findViewById(R.id.final_score_view);
        finalScoreView.setText("Your final score: " + score);
        builder.setView(customLayout);
        Button okButton = customLayout.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
        playAudio.stopPlayer();
        if (countDownTimer != null)
            countDownTimer.cancel();
        gameInProgress = false;
    }

    private void startNewGame() {
        Collections.shuffle(questionsList);
        currentQuestionNumber = 1;
        score = 0;
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);
        setQuestion();
    }

    private void setQuestion() {
        if (textViewCountDownTimer != null) {
            textViewCountDownTimer.setText("00:30");
            textViewCountDownTimer.setTextColor(getResources().getColor(android.R.color.white));
        }
        makeOptionsInvisible();
        timeLeftInMillis = COUNTDOWN;
        String text = getString(R.string.question_number,
                currentQuestionNumber, questionsList.size());
        questionNumbers.setText(text);
        currentQuestion = questionsList.get(currentQuestionNumber - 1);
        questionsView.setText(currentQuestion.getQuestion());
        optionA.setText(currentQuestion.getOptionA());
        optionB.setText(currentQuestion.getOptionB());
        optionC.setText(currentQuestion.getOptionC());
        optionD.setText(currentQuestion.getOptionD());
        makeOptionsVisible();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startCountDown();
            }
        }, 2000);
        gameInProgress = true;
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDownTimer = findViewById(R.id.text_timer);
        textViewCountDownTimer.setText(timeFormatted);
        if (timeLeftInMillis < RED_ALERT) {
            textViewCountDownTimer.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            playAudio.playSound(R.raw.tick_tock);
        } else textViewCountDownTimer.setTextColor(getResources().getColor(android.R.color.white));
        if (timeLeftInMillis == 0) {
            playAudio.stopPlayer();
            playAudio.playSound(R.raw.oh_my);
            if (countDownTimer != null)
                countDownTimer.cancel();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    timeIsUp();
                }
            }, 1500);
        }
    }

    private boolean checkAnswer(String answer) {
        if (answer.equals(currentQuestion.getAnswer()))
            return true;
        else return false;
    }

    private void rightAnswer() {
        currentQuestionNumber++;
        //for right answer player gets 5 points
        score = score + 5;
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);
        if (currentQuestionNumber < questionsList.size() + 1) {
            setQuestion();
        } else {
            gameOver();
        }
    }

    private void wrongAswer() {
        currentQuestionNumber++;
        //if answer is wrong player loses 2 points
        score = score - 2;
        scoreBoard = getString(R.string.score_board, score);
        scoreView.setText(scoreBoard);
        setDefaultBackground();
        if (currentQuestionNumber < questionsList.size() + 1) {
            setQuestion();
        } else {
            gameOver();
        }
    }

    private void timeIsUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.final_score_dialog, null);
        TextView titleView = customLayout.findViewById(R.id.finalDialogTitle);
        titleView.setText(R.string.time_is_up);
        TextView finalScoreView = customLayout.findViewById(R.id.final_score_view);
        finalScoreView.setText(getString(R.string.your_final_score, score));
        builder.setView(customLayout);
        Button okButton = customLayout.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
        playAudio.stopPlayer();
        if (countDownTimer != null)
            countDownTimer.cancel();
        gameInProgress = false;
    }

    private void makeOptionsVisible() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionA.setVisibility(View.VISIBLE);
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionB.setVisibility(View.VISIBLE);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionC.setVisibility(View.VISIBLE);
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionD.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    private void makeOptionsInvisible() {
        optionA.setVisibility(View.INVISIBLE);
        optionB.setVisibility(View.INVISIBLE);
        optionC.setVisibility(View.INVISIBLE);
        optionD.setVisibility(View.INVISIBLE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!playAudio.playerIsNull())
            playAudio.stopPlayer();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!playAudio.playerIsNull())
            playAudio.stopPlayer();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameInProgress) {
            if (countDownTimer != null)
                startCountDown();
        }
    }

}

