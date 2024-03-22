package com.example.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {

    private TextView txtScore, txtName;
    private Button btnFinish, btnNewQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();

        String name = extras.getString("Name");
        String correctAns = extras.getString("CorrectAns");
        String totalQuestion = extras.getString("TotalQuestion");

        txtScore = findViewById(R.id.textView9);
        txtName = findViewById(R.id.textView7);
        btnFinish = findViewById(R.id.button3);
        btnNewQuiz = findViewById(R.id.button4);

        txtName.setText("Congratulations to " + name);
        txtScore.setText( correctAns + "/" + totalQuestion);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(Result.this, MainActivity.class);
              startActivity(intent);
            }
        });

        btnNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, QuizScreen.class);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}