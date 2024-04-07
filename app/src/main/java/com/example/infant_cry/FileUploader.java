package com.example.infant_cry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

public class FileUploader {
    public static String uploadFileAndGetAnswer(final String filePath, final String url) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .userAgent("infant cry")
                .data("file", ".mp3",
                        new FileInputStream(new File(filePath)))
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();
        return response.body();
    }
}