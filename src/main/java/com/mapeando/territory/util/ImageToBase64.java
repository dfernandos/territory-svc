package com.mapeando.territory.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageToBase64 {
    public static void main(String[] args) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get("/Users/danieloliveira/Documents/territory/src/main/java/com/mapeando/territory/util/quilombo_1.png"));
            String imagemBase64 = Base64.getEncoder().encodeToString(fileContent);
            System.out.println(imagemBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}