package com.example.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private TextView mShowCount;
    private Button button_count;
    private Button button_zero;
    public static final String EXTRA_MESSAGE = "com.example.hellotoast.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        button_count = (Button) findViewById(R.id.button_count);
        button_zero = (Button) findViewById(R.id.button_zero);
    }

    public void SayHello(View view) {
        Intent intent = new Intent(this, DisplayActivity.class);
        String number = mShowCount.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, number);
        startActivity(intent);

    }

    public void countUp(View view) {
        mCount++;
        if (mCount % 2 == 0) {
            button_count.setBackgroundColor(Color.RED);
            button_zero.setBackgroundColor(Color.YELLOW);
        }
        else {
            button_count.setBackgroundColor(Color.BLUE);
            button_zero.setBackgroundColor(Color.GREEN);
        }
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }

    public void zero(View view) {
        mCount = 0;
        button_zero.setBackgroundColor(Color.GRAY);
        button_count.setBackgroundColor(Color.RED);
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }
}