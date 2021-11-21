package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.Tag;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TagDAO extends AbstractDAO<Tag>{
    public TagDAO() { setClazz(Tag.class); }

    public List<Tag> getAllByUserId(long userId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select u.tags from User u where u.id = :userIdParam");
        query.setParameter("userIdParam", userId);
        List<Tag> output = (List<Tag>) query.getResultList();

        session.close();
        return output;
    }

    public List<Tag> getMultipleById(List<Long> ids){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Tag where id in :idsParam");
        query.setParameter("idsParam", ids);
        List<Tag> output = (List<Tag>) query.getResultList();

        session.close();
        return output;
    }
}
