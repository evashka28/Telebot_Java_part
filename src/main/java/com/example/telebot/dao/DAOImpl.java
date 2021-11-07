package com.example.telebot.dao;


import  com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public class DAOImpl<T> implements DAO{
    private Class<T> clazz;

    public Serializable findById(int id) {
        return (Serializable) HibernateSessionFactoryUtil.getSessionFactory().openSession().get(clazz, id);
    }

    @Override
    public Serializable save(Serializable entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(entity);
        tx1.commit();
        session.close();
        return entity;
    }

    @Override
    public Serializable update(Serializable entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(entity);
        tx1.commit();
        session.close();
        return entity;
    }

    @Override
    public void delete(Serializable entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(entity);
        tx1.commit();
        session.close();
    }

}