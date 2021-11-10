package com.example.telebot.dao;

import com.example.telebot.Project;
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

    public List<Task> getAllByProjectId(long projectId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Task.class);
        ArrayList<Task> output = (ArrayList<Task>) criteria.add(Restrictions.eq("projectId", projectId)).list();
        session.close();
        return output;
    }

    public Task getWithOldestLastAccessByProjectId(long projectId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Task.class);
        Task output = (Task) criteria.add(Restrictions.eq("projectId", projectId))
                .addOrder(Order.asc("lastAccessDatetime"))
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return output;
    }
}
