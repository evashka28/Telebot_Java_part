package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.Task;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
}
