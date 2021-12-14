package com.example.telebot.dao;

import com.example.telebot.Tag;
import com.example.telebot.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.telebot.quartz_try.payload.TagRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRequestDAO extends AbstractDAO<TagRequest>{
    public TagRequestDAO() { setClazz(TagRequest.class); }

    public TagRequest get(long tagId, String id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select sh from Tag u " +
                "inner join u.schedule sh " +
                "where u.id = :tagIdParam and " +
                "sh.id = :idParam");
        query.setParameter("tagIdParam", tagId);
        query.setParameter("idParam", id);
        query.setMaxResults(1);
        List<TagRequest> output = (List<TagRequest>) query.getResultList();


        if(output.size() == 0)
            return null;
        session.close();
        return output.get(0);
    }

    public List<TagRequest> getAllByTagId(long tagId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select u.schedule from Tag u where u.id = :tagIdParam");
        query.setParameter("tagIdParam", tagId);
        List<TagRequest> output = (List<TagRequest>) query.getResultList();

        session.close();
        return output;
    }


}
