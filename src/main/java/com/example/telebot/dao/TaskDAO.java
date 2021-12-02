package com.example.telebot.dao;

import com.example.telebot.Task;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO extends AbstractDAO<Task>{
    public TaskDAO(){
        setClazz(Task.class);
    }

    public List<Task> getAll(long userId, boolean favourite){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select t from User u " +
                "inner join u.projects p " +
                "inner join p.tasks t " +
                "where u.id = :userIdParam " +
                "and t.favourite = :favouriteParam");
        query.setParameter("userIdParam", userId);
        query.setParameter("favouriteParam", favourite);
        List<Task> output = (List<Task>) query.getResultList();

        session.close();
        return output;
    }

    public List<Task> getAll(long userId){
        return getAll(userId, false);
    }

    public List<Task> getAllFavourites(long userId){
        return getAll(userId, true);
    }

    public Task getWithOldestLastAccess(long userId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select t from User u " +
                "inner join u.projects p " +
                "inner join p.tasks t " +
                "where u.id = :userIdParam " +
                "and t.favourite = :favouriteParam " +
                "order by t.lastAccessDatetime asc");
        query.setParameter("userIdParam", userId);
        query.setParameter("favouriteParam", false);
        query.setMaxResults(1);
        List<Task> output = (List<Task>) query.getResultList();

        session.close();

        if(output.size() == 0)
            return null;
        return output.get(0);
    }

    public List<Long> getAllTodoistIdsByUserId(long userId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select t.todoistId from User u " +
                "inner join u.projects p " +
                "inner join p.tasks t " +
                "where u.id = :userIdParam");
        query.setParameter("userIdParam", userId);
        List<Long> output = (List<Long>) query.getResultList();

        session.close();

        return output;
    }

    public List<Task> getAllByTagId(long tagId, long userId, boolean favourite){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select t from User u " +
                "inner join u.projects p " +
                "inner join p.tasks t " +
                "inner join t.tags tg " +
                "where u.id = :userIdParam and " +
                "tg.id = :tagIdParam and " +
                "t.favourite = :favouriteParam");
        query.setParameter("userIdParam", userId);
        query.setParameter("tagIdParam", tagId);
        query.setParameter("favouriteParam", favourite);
        List<Task> output = (List<Task>) query.getResultList();

        session.close();

        return output;
    }

    public Task getWithOldestLastAccessByTagId(long tagId, long userId, boolean favourite) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select t from User u " +
                "inner join u.projects p " +
                "inner join p.tasks t " +
                "inner join t.tags tg " +
                "where u.id = :userIdParam and " +
                "tg.id = :tagIdParam and " +
                "t.favourite = :favouriteParam " +
                "order by t.lastAccessDatetime asc");
        query.setParameter("userIdParam", userId);
        query.setParameter("tagIdParam", tagId);
        query.setParameter("favouriteParam", favourite);
        query.setMaxResults(1);
        List<Task> output = (List<Task>) query.getResultList();

        session.close();

        if(output.size() == 0)
            return null;
        return output.get(0);
    }

    public Task getByTodoistIdAndUserId(long userId, long todoistId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select t from User u " +
                "inner join u.projects p " +
                "inner join p.tasks t " +
                "where u.id = :userIdParam and " +
                "t.todoistId = :todoistIdParam");
        query.setParameter("userIdParam", userId);
        query.setParameter("todoistIdParam", todoistId);

        query.setMaxResults(1);
        List<Task> output = (List<Task>) query.getResultList();

        session.close();

        if(output.size() == 0)
            return null;
        return output.get(0);
    }
}
