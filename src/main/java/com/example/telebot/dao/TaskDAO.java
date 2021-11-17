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
        Query query = session.createQuery("from Task where projectId = :projectIdParam " +
                "and favourite = :favouriteParam");
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
        Query query = session.createQuery("from Task where projectId = :projectIdParam " +
                "and favourite = :favouriteParam " +
                "and lastAccessDatetime = " +
                "(SELECT min(lastAccessDatetime) from Task where projectId = :projectIdParam " +
                "and favourite = :favouriteParam)");
        query.setParameter("projectIdParam", projectId);
        query.setParameter("favouriteParam", false);
        query.setMaxResults(1);
        Task output = (Task) query.getSingleResult();

        session.close();

        return output;
    }
}
