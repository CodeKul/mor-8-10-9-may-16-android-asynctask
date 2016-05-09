package com.codekul.asynctaskdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textInfo);
        findViewById(R.id.btnOkay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MyTask().execute(/*new Integer[]{}*/);
                /*
                worker threads can not touch UI

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i = 0 ; i<10; i++){

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            textView.setText(""+i);
                        }
                    }

                }).start();*/

                /*
                it was laging the UI and not working as expectedly ...
                for(int i = 0 ; i<10; i++){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    textView.setText(""+i);
                }*/
            }
        });
    }

    private class MyTask extends AsyncTask<Integer,Integer,String> {

        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,"Counting","Generating numbers");
            // UI thread

            //(1).
        }

        @Override
        protected String doInBackground(Integer... params/*parameter given to execute method */) {
            // worker thread

            //(2)
            for(int i = 0 ; i<10; i++){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(new Integer[]{i});
            }
            return "loop executed successfully";
        }

        @Override
        protected void onPostExecute(String s) {
            // UI therad
            super.onPostExecute(s);
            //(3)

            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // ui thread
            super.onProgressUpdate(values);
            // publisgProgress will call this method

            ((TextView)findViewById(R.id.textInfo)).setText(""+values[0]);
        }
    }
}
