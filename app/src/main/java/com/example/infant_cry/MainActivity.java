package com.example.infant_cry;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {


    MediaRecorder recorder;

    MediaPlayer player;

    String audioFile;
    TextView textView;

    private void startRecording() {
        textView = (TextView) findViewById(R.id.textView);
        File privateDir = getApplicationContext().getFilesDir();
        File recordingsDir = new File(privateDir, "recordings");
        if (!recordingsDir.exists()) {
            recordingsDir.mkdirs();
        }
        String filename = ".mp3";
        File outputFile = new File(recordingsDir, filename);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(outputFile.getAbsolutePath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        textView.setText("正在录音");
        audioFile = outputFile.getAbsolutePath();

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        textView.setText("录音完成,欢迎使用");
        new False2TrueThread(findViewById(R.id.play),
                findViewById(R.id.send)).run();
        recorder = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        初始化MediaPlayer
        player = new MediaPlayer();


        Button button = findViewById(R.id.button);
//        检查权限
        checkPermission();
//        长按录音的函数
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                startRecording();
                System.out.println("start pressing");
                return true;
            }
        });
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 在这里停止录音
                    stopRecording();
                    System.out.println("press finished");
                }
                return false;
            }
        });


//        播放的逻辑
        Button play = findViewById(R.id.play);

        /*
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setClickable(false);

                PlayThread thread = new PlayThread(audioFile, player, getApplicationContext());
                thread.start();

                player.reset();
                System.out.println("play播放完成！！！！！！！！！！！！！");

                play.setClickable(true);
            }
        });

         */

//        上传音频的逻辑
        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("upload thread");
                textView.setText("识别中loading....");
                PostThread thread = new PostThread(audioFile, new OnThreadCompleteListener() {
                    @Override
                    public void onThreadComplete(String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //在主线程中更新ui
                                textView.setText(result);
                            }
                        });

                    }
                });
                thread.start();

            }
        });


    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 200);
                    return;
                }
            }
        }
    }


    public void onClickPlay(View view) throws IOException {
        System.out.println("能过来吗？？？？？");
        FileInputStream fis = new FileInputStream(audioFile);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(fis.getFD());
        mediaPlayer.prepare();
        mediaPlayer.start();
        System.out.println("能播放了吧");
    }
}