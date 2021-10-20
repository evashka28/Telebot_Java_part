package com.example.telebot;

import java.sql.*;

public class DBConnector {
    private final String DB_DRIVER = "org.postgresql.Driver";
    private final String DB_URL    = "jdbc:postgresql://rc1c-msi3tmzl59knuhp4.mdb.yandexcloud.net:6432/BotData?&targetServerType=master&ssl=true&sslmode=verify-full";
    private final String DB_USER   = "user1";
    private final String DB_PASS   = "netcracker";

    public void connectToDB()
    {
        //Class.forName();
    }
}
