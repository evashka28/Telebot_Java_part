package com.example.telebot.dao;
import java.io.Serializable;

public interface DAO  {
    public Serializable findById(int id);
    public Serializable save(Serializable entity);
    public Serializable update(Serializable entity);
    public void delete(Serializable entity);
}