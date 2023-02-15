package com.kosavpa.first.boot.example.Utils;


import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;


@Service
public class MultipartFileHelper {
    private String[] expectedTypes = {"jpeg", "jpg", "png"};

    public String getFileType(String filename){
        return filename.split("\\.")[1];
    }

    public boolean imgFileTypeMatcher(String fileType){
        if( Arrays.binarySearch(expectedTypes, fileType) != -1){
            return true;
        }

        return false;
    }

    public String convertToBase64(String fileType, byte[] bytes){
        StringBuilder builder = new StringBuilder("data:image/");

        builder
                .append(fileType)
                .append(";base64,")
                .append(Base64.getEncoder().encodeToString(bytes));

        return builder.toString();
    }
}
