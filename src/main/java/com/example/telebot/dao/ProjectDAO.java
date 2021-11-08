package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDAO extends AbstractDAO<Project>{
    public ProjectDAO()
    {
        setClazz(Project.class);
    }

    public List<Project> getAllByUserId(long userId)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Project.class);
        ArrayList<Project> output = (ArrayList<Project>) criteria.add(Restrictions.eq("user_id", userId)).list();
        session.close();
        return output;
    }
}
