package com.example.infant_cry;

import java.io.IOException;


public class PostThread extends Thread {
    final String url = "http://10.236.76.223:12888/upload";

    public String file;
    private OnThreadCompleteListener listener;

    public PostThread(String audioFile, OnThreadCompleteListener postListener) {
        this.file = audioFile;
        this.listener = postListener;
    }


    @Override
    public void run() {
        String result;
        try {
            result = FileUploader.uploadFileAndGetAnswer(this.file, url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (listener != null) {
            listener.onThreadComplete(result);
        }
    }
}
