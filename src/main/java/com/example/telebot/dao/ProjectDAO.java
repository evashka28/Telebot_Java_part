package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Repository
public class ProjectDAO extends AbstractDAO<Project>{
    public ProjectDAO()
    {
        setClazz(Project.class);
    }

    public List<Project> getAllByUserId(long userId)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select u.projects from User u where u.id = :userIdParam");
        query.setParameter("userIdParam", userId);
        List<Project> output = (List<Project>) query.getResultList();
        session.close();
        return output;
    }

    public Project getProjectByUserId(long userId, boolean favourite){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select u.projects from User u " +
                "inner join u.projects p " +
                "where u.id = :userIdParam and p.favourite = :favouriteParam");
        query.setParameter("userIdParam", userId);
        query.setParameter("favouriteParam", favourite);
        query.setMaxResults(1);
        Project output = (Project) query.getSingleResult();

        session.close();
        return output;
    }
}
