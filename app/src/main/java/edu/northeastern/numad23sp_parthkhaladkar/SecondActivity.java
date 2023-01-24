package edu.northeastern.numad23sp_parthkhaladkar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.widget.TextView;
import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity {
    private TextView screendisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        //screendisp.setText("In second Activity");
        screendisp = findViewById(R.id.textViewPressed);

        screendisp.setText("");

    }

    public void onClick(View view)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Pressed: ");
        sb.append(((Button) view).getText().toString());
        screendisp.setText(sb.toString());
    }
}