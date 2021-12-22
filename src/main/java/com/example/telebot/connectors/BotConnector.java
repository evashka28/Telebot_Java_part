package com.example.telebot.connectors;

import com.example.telebot.entities.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class BotConnector {
    public String sendTask(Task task, Long userId) throws IOException {
        if (task == null)
            return "";
        URL url = new URL("http://localhost:8082/task");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("userId", userId.toString());
        connection.setDoOutput(true);

        String jsonOutput = task.toString();

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonOutput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        log.info(String.valueOf(response));

        return response.toString();
    }

}
