package com.example.infant_cry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class FileUploader {
    public static String uploadFileAndGetAnswer(String filePath, String url) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .userAgent("my application")
                .data("file", "myrecording.mp3",
                        new FileInputStream(
                                new File(filePath)
                        ))
                .method(org.jsoup.Connection.Method.POST)
                .ignoreContentType(true)
                .execute();
        return response.body();

    }
}
