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
        Criteria criteria = session.createCriteria(Project.class);
        ArrayList<Project> output = (ArrayList<Project>) criteria.add(Restrictions.eq("userId", userId)).list();
        session.close();
        return output;
    }

    public Project getProjectByUserId(long userId, boolean favourite){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> root = criteriaQuery.from(Project.class);

        Predicate[] predicates = new Predicate[2];
        predicates[0] = criteriaBuilder.equal(root.get("userId"), userId);
        predicates[1] = criteriaBuilder.equal(root.get("favourite"), favourite);

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates));

        Query<Project> query = session.createQuery(criteriaQuery);

        Project output = query.getSingleResult();

        session.close();
        return output;
    }
}
