package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNum, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnNext;

    private List<QuestionModel> questionList;

    private QuestionModel currentQuestion;
    int totalQuestion;
    int qCounter = 0;
    int score ;

    ColorStateList dfrColor;
    boolean answered;

    CountDownTimer countTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionList = new ArrayList<>();
        tvScore = findViewById(R.id.textScore);
        tvQuestion = findViewById(R.id.textQuestion);
        tvQuestionNum = findViewById(R.id.textQuestionNum);
        tvTimer = findViewById(R.id.textTimer);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        btnNext = findViewById(R.id.btnNext);

        dfrColor = rb1.getTextColors();
        addQuestions();
        totalQuestion = questionList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                        countTimer.cancel();
                    }else{
                        Toast.makeText(QuizActivity.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestion();
                }
            }

            
        });
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNum = radioGroup.indexOfChild(rbSelected) + 1;
        if(answerNum == currentQuestion.getCorrectAnsNum()){
            score++;
            tvScore.setText("Score: "+score);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNum()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if(qCounter < totalQuestion){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }
    }

    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfrColor);
        rb2.setTextColor(dfrColor);
        rb3.setTextColor(dfrColor);
        if(qCounter < totalQuestion){
            timer();
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            qCounter++;

            btnNext.setText("Submit");
            tvQuestionNum.setText("Question: "+qCounter+"/"+totalQuestion);
            answered = false;
        }else {
            finish();
        }
    }

    private void timer() {
        countTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(""+millisUntilFinished/ 1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }

    private void addQuestions() {
        questionList.add(new QuestionModel("A is correct?", "A","B","C",1));
        questionList.add(new QuestionModel("B is correct?", "A","B","C",2));
        questionList.add(new QuestionModel("C is correct?", "A","B","C",3));
    }
}