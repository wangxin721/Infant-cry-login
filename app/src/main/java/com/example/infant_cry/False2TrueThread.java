package com.example.infant_cry;

import android.widget.Button;

public class False2TrueThread extends Thread {

    Button play;
    Button send;

    public False2TrueThread(Button btn1, Button btn2) {
        play = btn1;
        send = btn2;
    }

    @Override
    public void run() {
        play.setClickable(true);
        send.setClickable(true);
        play.setText("播放");
        send.setText("点击开始识别");
    }
}
