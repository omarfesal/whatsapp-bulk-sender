package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        WebDriver driver = new ChromeDriver();

        BufferedReader messageReader = new BufferedReader(new FileReader("message.txt"));
        StringBuilder messageBuilder = new StringBuilder();
        String line;
        while ((line = messageReader.readLine()) != null) {
            messageBuilder.append(line);
        }
        messageReader.close();
        String message = messageBuilder.toString();

        System.out.print("\033[33m"); // Yellow color
        System.out.println("\nThis is your message-");
        System.out.print("\033[32m"); // Green color
        System.out.println(message);
        System.out.print("\n\033[0m"); // Reset color

        List<String> numbers = new ArrayList<>();
        BufferedReader numbersReader = new BufferedReader(new FileReader("numbers.txt"));
        while ((line = numbersReader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                numbers.add(line);
            }
        }
        numbersReader.close();
        int totalNumber = numbers.size();
        System.out.print("\033[31m"); // Red color
        System.out.println("We found " + totalNumber + " numbers in the file");
        System.out.print("\033[0m"); // Reset color


        driver.get("https://web.whatsapp.com");
        System.out.print("\033[35m"); // Magenta color
        System.out.println("AFTER logging into Whatsapp Web is complete and your chats are visible, press ENTER...");
        System.in.read();
        System.out.print("\033[0m"); // Reset color

        for (int idx = 0; idx < totalNumber; idx++) {
            String number = numbers.get(idx).trim();
            if (number.isEmpty()) {
                continue;
            }
            System.out.print("\033[33m"); // Yellow color
            System.out.println((idx + 1) + "/" + totalNumber + " => Sending message to " + number + ".");

            System.out.print("\033[0m"); // Reset color
            try {
                WebElement clickBtn = null;
                String url = String.format("https://web.whatsapp.com/send?phone=%s&text=%s", number, message);
                System.out.println("Url should be " + url);

                boolean sent = false;
                for (int i = 0; i < 3; i++) {
                    if (!sent) {
                        driver.get(url);

                        try {
                            System.out.println("Waiting for search button");
                            if (i == 0) continue;
                            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(1));
                            webDriverWait
                                    .until(
                                            ExpectedConditions.or(
                                                    ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Send']")),
                                                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Phone number shared"))


                                    );


                            clickBtn = driver.findElement(By.cssSelector("button[aria-label='Send']"));
                            System.out.println("Waiting for search button finished");
                            Thread.sleep(1000);
                            System.out.println("button found ? " + clickBtn);
                            clickBtn.click();
                            sent = true;
                            Thread.sleep(3000);
                            System.out.print("\033[32m"); // Green color
                            System.out.println("Message sent to: " + number);
                            System.out.print("\033[0m"); // Reset color

                        } catch (Exception e) {
                            System.out.print("\033[31m"); // Red color
                            System.out.println("\nFailed to send message to: " + number + ", retry (" + (i + 1) + "/3)");
                            System.out.println("Make sure your phone and computer is connected to the internet.");
                            System.out.println("If there is an alert, please dismiss it.");
                            System.out.print("\033[0m"); // Reset color
                        }
                    }
                }
            } catch (Exception e) {
                System.out.print("\033[31m"); // Red color
                System.out.println("Failed to send message to " + number + e);
                System.out.print("\033[0m"); // Reset color
            }
        }

        driver.quit();
    }

    private static boolean isTextPresent(WebDriver driver, String text) {
        WebElement body = driver.findElement(By.tagName("body"));
        return body.getText().contains(text);
    }
}