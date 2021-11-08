package com.example.telebot.dao;


import  com.example.telebot.utils.HibernateSessionFactoryUtil;
import com.google.common.base.Preconditions;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public abstract class AbstractDAO<T extends Serializable>{
    private Class<T> clazz;

    public final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }

    public T findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(clazz, id);
    }

    public T save(T entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(entity);
        tx1.commit();
        session.close();
        return entity;
    }

    public T update(T entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(entity);
        tx1.commit();
        session.close();
        return entity;
    }

    public void delete(T entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(entity);
        tx1.commit();
        session.close();
    }

}