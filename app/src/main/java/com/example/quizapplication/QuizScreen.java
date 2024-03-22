package com.example.quizapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class QuizScreen extends AppCompatActivity {

    private List<QuestionModel> questionList;
    private TextView question, score, questionNumber;
    private RadioGroup radioGroup;
    private RadioButton op1, op2, op3, op4;
    private Button btnNext;

    int totalQuestion, qCounter = 0, correctAns = 0;

    private QuestionModel currentQuestion;

    ColorStateList rbColor;
    boolean answered;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_screen);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        TextView textView = findViewById(R.id.textView3);
        textView.setText("Welcome " + name);

        questionList = new ArrayList<>();
        question = findViewById(R.id.textView5);
        score = findViewById(R.id.textView6);
        radioGroup = findViewById(R.id.radioGroup);
        op1 = findViewById(R.id.radioButton1);
        op2 = findViewById(R.id.radioButton2);
        op3 = findViewById(R.id.radioButton3);
        op4 = findViewById(R.id.radioButton4);
        btnNext = findViewById(R.id.button2);
        questionNumber = findViewById(R.id.textView4);
        progressBar = findViewById(R.id.progressBar2);

        rbColor = op1.getTextColors();

        addQuestion();
        totalQuestion = questionList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered == false){
                    if(op1.isChecked() || op2.isChecked() || op3.isChecked() || op4.isChecked()){
                        checkAnswer();
                    }
                    else{
                        Toast.makeText(QuizScreen.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    showNextQuestion();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showNextQuestion(){

        radioGroup.clearCheck();
        op1.setTextColor(rbColor);
        op2.setTextColor(rbColor);
        op3.setTextColor(rbColor);
        op4.setTextColor(rbColor);

        if(qCounter < totalQuestion){
            currentQuestion = questionList.get(qCounter);
            question.setText(currentQuestion.getQuestion());
            op1.setText(currentQuestion.getOption1());
            op2.setText(currentQuestion.getOption2());
            op3.setText(currentQuestion.getOption3());
            op4.setText(currentQuestion.getOption4());
            qCounter++;

            btnNext.setText("Submit");
            questionNumber.setText("Question " + qCounter + "/" + totalQuestion);
            progressBar.setProgress(qCounter);
            answered = false;
        } else {
            Intent intent = getIntent();
            Bundle extras = new Bundle();

            String name = intent.getStringExtra("Name");

            Intent newIntent = new Intent(this, Result.class);
            extras.putString("Name", name);
            extras.putString("CorrectAns", String.valueOf(correctAns));
            extras.putString("TotalQuestion", String.valueOf(totalQuestion));
            newIntent.putExtras(extras);
            startActivity(newIntent);
        }
    }

    public void checkAnswer(){
        answered = true;
        RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
        int ansNo = radioGroup.indexOfChild(selected) + 1;
        if(ansNo == currentQuestion.getCorrectAnsNo()){
            correctAns++;
            score.setText("Score: " + correctAns + "/" + totalQuestion);
        }
        op1.setTextColor(Color.RED);
        op2.setTextColor(Color.RED);
        op3.setTextColor(Color.RED);
        op4.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()){
            case 1:
                op1.setTextColor(Color.GREEN);
                break;
            case 2:
                op2.setTextColor(Color.GREEN);
                break;
            case 3:
                op3.setTextColor(Color.GREEN);
                break;
            case 4:
                op4.setTextColor(Color.GREEN);
                break;
        }
        if(qCounter < totalQuestion){
            btnNext.setText("Next");
        } else {
            btnNext.setText("Finish");
        }
    }

    public void addQuestion(){
        questionList.add(new QuestionModel("What is capital of Australia?", "Sydney", "Melbourne", "Canberra", "Brisbane", 3));
        questionList.add(new QuestionModel("What is capital of India?", "Delhi", "Gujarat", "Maharashtra", "Goa", 1));
        questionList.add(new QuestionModel("Which brand is most famous?", "Samsung", "Apple", "Realme", "Google", 2));
        questionList.add(new QuestionModel("What is the chemical symbol for Gold?", "Gd", "Go", "Ag", "Au", 4));
        questionList.add(new QuestionModel("In what year was the first iPhone released?", "2005", "2007", "2008", "2010", 2));
        questionList.add(new QuestionModel("What is the tallest mountain in the world?", "K2", "Mount Everest", "Mount Kilimanjaro", "Denali", 2));
        questionList.add(new QuestionModel("Who painted the \"Mona Lisa\"?", "Leonardo da Vinci", "Michelangelo", "Raphael", "Caravaggio", 1));
        questionList.add(new QuestionModel("Who discovered electricity?", "Isaac Newton", "Nikola Tesla", "Michael Faraday", "Benjamin Franklin", 4));
        questionList.add(new QuestionModel("What is the world's largest ocean?", "Atlantic Ocean ", "Indian Ocean", "Pacific Ocean", "Southern Ocean", 3));
        questionList.add(new QuestionModel("What is the hottest planet in the solar system?", "Mercury", "Mars", "Jupiter", "Venus", 4));
    }

}