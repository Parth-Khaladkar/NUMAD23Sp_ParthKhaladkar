package edu.northeastern.numad23sp_parthkhaladkar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if(view.getId()==R.id.about){
            displaydetails();
        }
        else if(view.getId()==R.id.second){
            Intent i = new Intent(this,SecondActivity.class);
            startActivity(i);
        }

    }

    public void displaydetails(){

        String TAG = "In If  ";

        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);
        if(name.getText() == ""){

            name.setText(R.string.convert_name);
            email.setText(R.string.convert_email);
        }else{
            name.setText("");
            email.setText("");
        }
    }

}