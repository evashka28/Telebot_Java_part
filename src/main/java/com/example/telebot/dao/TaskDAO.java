package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.Tag;
import com.example.telebot.Task;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDAO extends AbstractDAO<Task>{
    public TaskDAO(){
        setClazz(Task.class);
    }

    public List<Task> getAllByProjectId(long projectId, boolean favourite){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select p.tasks from Project p " +
                "inner join p.tasks t " +
                "where p.id = :projectIdParam " +
                "and t.favourite = :favouriteParam");
        query.setParameter("projectIdParam", projectId);
        query.setParameter("favouriteParam", favourite);
        List<Task> output = (List<Task>) query.getResultList();

        session.close();
        return output;
    }

    public List<Task> getAllByProjectId(long projectId){
        return getAllByProjectId(projectId, false);
    }

    public Task getWithOldestLastAccessByProjectId(long projectId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select t from Project p " +
                "inner join p.tasks t " +
                "where p.id = :projectIdParam " +
                "and t.favourite = :favouriteParam " +
                "and t.lastAccessDatetime = (select min(lastAccessDatetime) from t " +
                "where p.id = :projectIdParam " +
                "and t.favourite = :favouriteParam)");
        query.setParameter("projectIdParam", projectId);
        query.setParameter("favouriteParam", false);
        query.setMaxResults(1);
        Task output = (Task) query.getSingleResult();

        session.close();

        return output;
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
                "and t.lastAccessDatetime = (select min(tt.lastAccessDatetime) from User uu " +
                "inner join uu.projects pp " +
                "inner join pp.tasks tt " +
                "inner join tt.tags tgtg " +
                "where uu.id = :userIdParam and " +
                "tgtg.id = :tagIdParam and " +
                "tt.favourite = :favouriteParam " +
                ")");
        query.setParameter("userIdParam", userId);
        query.setParameter("tagIdParam", tagId);
        query.setParameter("favouriteParam", favourite);
        query.setMaxResults(1);

        Task output = (Task) query.getSingleResult();

        session.close();

        return output;
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

        Task output = (Task) query.getResultList();

        session.close();

        return output;
    }
}
