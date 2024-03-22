package com.example.infant_cry;

import java.io.IOException;

public class PostThread extends Thread {
    final String url = "http://10.241.151.186:12888/upload";

    public String file;

    public PostThread(String audioFile) {
        this.file = audioFile;
    }


    @Override
    public void run() {
        String result;
        try {
            result = FileUploader.uploadFileAndGetAnswer(this.file, url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file = result;
    }
}
