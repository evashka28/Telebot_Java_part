package com.example.telebot.dao;

import com.example.telebot.entities.Project;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDAO extends AbstractDAO<Project> {
    public ProjectDAO() {
        setClazz(Project.class);
    }

    public List<Project> getAllByUserId(long userId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select u.projects from User u where u.id = :userIdParam");
        query.setParameter("userIdParam", userId);
        List<Project> output = (List<Project>) query.getResultList();
        session.close();
        return output;
    }

    public Project getProjectByUserId(long userId, boolean favourite) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select u.projects from User u " +
                "inner join u.projects p " +
                "where u.id = :userIdParam and p.favourite = :favouriteParam");
        query.setParameter("userIdParam", userId);
        query.setParameter("favouriteParam", favourite);
        query.setMaxResults(1);
        List<Project> output = (List<Project>) query.getResultList();

        session.close();

        if (output.size() == 0)
            return null;
        return output.get(0);
    }

    public List<Long> getAllTodoistIdsByUserId(long userId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select p.todoistId from User u " +
                "inner join u.projects p " +
                "where u.id = :userIdParam");
        query.setParameter("userIdParam", userId);
        List<Long> output = (List<Long>) query.getResultList();

        session.close();

        return output;
    }

    public Project getByTodoistIdAndUserId(long userId, long todoistId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select p from User u " +
                "inner join u.projects p " +
                "where u.id = :userIdParam and " +
                "p.todoistId = :todoistIdParam");
        query.setParameter("userIdParam", userId);
        query.setParameter("todoistIdParam", todoistId);
        query.setMaxResults(1);

        Project output = (Project) query.getSingleResult();

        session.close();

        return output;
    }

}
