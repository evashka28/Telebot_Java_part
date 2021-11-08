//package com.example.telebot.services;
//
//import com.example.telebot.dao.AbstractDAO;
//import java.io.Serializable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ServiceBD {
//
//    private AbstractDAO Dao = new AbstractDAO();
//
//
//    public Serializable find(int id) {
//        return Dao.findById(id);
//    }
//
//    public Serializable save(Serializable entity) {
//        return Dao.save(entity);
//    }
//
//    public void delete(Serializable entity) {
//        Dao.delete(entity);
//    }
//
//    public Serializable update(Serializable entity) {
//        return Dao.update(entity);
//    }
//
//}