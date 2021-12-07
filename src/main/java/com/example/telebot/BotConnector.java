package com.example.telebot;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class BotConnector
{
    public String sendTask(Task task, Long userId) throws IOException {
        URL url = new URL("http://localhost:8082/task");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("userId", userId.toString());
        connection.setDoOutput(true);

        String jsonOutput = task.toString();
        System.out.printf(jsonOutput);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonOutput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        return response.toString();
    }

}
