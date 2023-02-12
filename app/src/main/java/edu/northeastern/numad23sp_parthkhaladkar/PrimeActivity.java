package edu.northeastern.numad23sp_parthkhaladkar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class PrimeActivity extends AppCompatActivity {

    private Handler primeTextHandler = new Handler();
    private int pstart = 3;
    private int current_no = 3;
    private boolean stop_flag = false;
    private boolean f_flag = false;
    private boolean c_flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);
        updatePrimeTextView();
    }



    public void startPrimeGeneration(View view)
    {
        stop_flag = false;
        f_flag = true;
        startPrimeThread();
    }



    private void startPrimeThread() {
        PrimeNos task = new PrimeNos();
        Thread thread = new Thread(task);
        thread.start();
    }



    public void terminateOnClick(View view) {
        stop_flag = true;
        f_flag = false;
    }



    public void pacifierSwitchOnClick(View view)
    {
        c_flag = ((CheckBox) view).isChecked();
    }




    private void updatePrimeTextView()
    {
        TextView primeText = (TextView) findViewById(R.id.primeText);
        primeText.setText("current found prime: " + pstart);
    }



    // to do : class primenos

    class PrimeNos implements Runnable {

        private long timeupdated = 0;

        @Override
        public void run() {
            while (!stop_flag) {
                current_no += 2;
                if (isPrime(current_no))
                {
                    pstart = current_no;
                    long currentTime = System.currentTimeMillis() ;



                    if (currentTime - timeupdated > 100)
                    {
                        timeupdated = currentTime;
                        displayupdate();
                    }
                }
            }
        }



        private boolean isPrime(int no) {
            if (no <= 1)
                return false;

            for (int i = 2; i < no; i++)
                if (no % i == 0)
                    return false;

            return true;


        }





        private void displayupdate() {
            primeTextHandler.post(new Runnable()
            {
                @Override
                public void run() {
                    updatePrimeTextView();
                }
            });
        }


    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.back_pressed_dialog, null);
        builder.setMessage("Are you sure to quit?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PrimeActivity.super.onBackPressed();
            }
        });
        builder.show();
    }








    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        pstart = savedInstanceState.getInt("prime", 3);
        current_no = savedInstanceState.getInt("currentNum", 3);
        stop_flag = savedInstanceState.getBoolean("terminated", false);
        c_flag = savedInstanceState.getBoolean("checked", false);
        f_flag = savedInstanceState.getBoolean("finding", false);
        updatePrimeTextView();
        CheckBox pacifierSwitch = (CheckBox) findViewById(R.id.pacifierSwitch);
        pacifierSwitch.setChecked(c_flag);
        if (f_flag)
        {
            startPrimeThread();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt("prime", pstart);
        outState.putInt("currentNum", current_no);
        outState.putBoolean("terminated", stop_flag);
        outState.putBoolean("checked", c_flag);
        outState.putBoolean("finding", f_flag);
        super.onSaveInstanceState(outState);
    }





}