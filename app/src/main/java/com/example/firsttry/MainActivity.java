package com.example.firsttry;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Button mReloadButton;
    private GetJokesTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mReloadButton = (Button) findViewById(R.id.reload_button);
        mReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.count);
                String temp = editText.getText().toString();
                int i = 0;
                if(!"".equals(temp)){
                    i = Integer.parseInt(temp);
                }



                task = new GetJokesTask();
                task.execute(i);
                TextView textView = (TextView) findViewById(R.id.jokes);
                try {
                    textView.setText(task.get());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    class GetJokesTask extends AsyncTask<Integer, Void, String> {
        private Integer count;


            protected String doInBackground (Integer... params){
                int count = params[0];
                String result = "Somthing wrong";


            GetJokes gJ = new GetJokes(count);
            try {

                result = gJ.showJokes(gJ.getJokes());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

            @Override
            protected void onPostExecute (String s){
            super.onPostExecute(s);
        }
        }


    }



