package com.example.jjong.lab5_1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2;
    EditText editText;
    Button btn;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = (ImageView) findViewById(R.id.imageView);
        img2 = (ImageView) findViewById(R.id.imageView2);

        editText = (EditText) findViewById(R.id.editText);
        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set Custom Thread and start thread
                SpiderThread thread1 = new SpiderThread(0);
                thread1.start();
                SpiderThread thread2 = new SpiderThread(1);
                thread2.start();
            }
        });
    }

    //make thread
    class SpiderThread extends Thread {

        int stateIdx, spiderIdx;
        ArrayList<Integer> images = new ArrayList<Integer>();

        public SpiderThread(int index) {
            spiderIdx = index;
            images.add(R.drawable.heyevery1);
            images.add(R.drawable.heyevery2);
            images.add(R.drawable.heyevery3);
        }


        public void run() {
            stateIdx = 0;
            for (int i = 0; i < 9; i++) {
                final String msg = "spiderman #" + spiderIdx + " state: " + stateIdx;   //set string

                //post() execute run() in new Runnable()
                handler.post(new Runnable() {
                    public void run() {
                        editText.append(msg + "\n");    //show string
                        if (spiderIdx == 0) {
                            img1.setImageResource(images.get(stateIdx));
                        } else if (spiderIdx == 1) {
                            img2.setImageResource(images.get(stateIdx));
                        }
                    }
                });

                try {
                    int sleepTime = getRandomTime(500, 3000);
                    Thread.sleep(sleepTime);
                    //random run 0.5 sec ~ 3 sec
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                stateIdx++; //add
                if (stateIdx >= images.size()) {
                    stateIdx = 0;
                }
            }
        }
    }
    //create random time
    public int getRandomTime(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}