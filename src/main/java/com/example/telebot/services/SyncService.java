package com.example.telebot.services;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface SyncService {
    void sync(long userId) throws IOException, ParseException;
}
