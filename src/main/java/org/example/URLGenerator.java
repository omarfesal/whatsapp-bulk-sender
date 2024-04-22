package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class URLGenerator {
    public static void main(String[] args) throws IOException {

        BufferedReader messageReader = new BufferedReader(new FileReader("messaage.txt"));
        StringBuilder messageBuilder = new StringBuilder();
        String line;
        while ((line = messageReader.readLine()) != null) {
            messageBuilder.append(line);
        }
        messageReader.close();
        String message = messageBuilder.toString();
        List<String> numbers = new ArrayList<>();
        BufferedReader numbersReader = new BufferedReader(new FileReader("numbers"));
        while ((line = numbersReader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                numbers.add(line);
            }
        }
        numbersReader.close();
        int totalNumber = numbers.size();
        for (int idx = 0; idx < totalNumber; idx++) {
            String number = numbers.get(idx).trim();
            if (number.isEmpty()) {
                continue;
            }
            String url = String.format("https://web.whatsapp.com/send?phone=%s&text=%s", number, message);
            System.out.println(url);

        }
    }
}