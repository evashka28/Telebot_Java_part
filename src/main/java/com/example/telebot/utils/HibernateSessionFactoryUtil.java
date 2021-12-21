package com.example.telebot.utils;



import com.example.telebot.entities.Project;
import com.example.telebot.entities.Tag;
import com.example.telebot.entities.User;
import com.example.telebot.entities.Task;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import com.example.telebot.entities.TagRequest;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Task.class);
                configuration.addAnnotatedClass(Project.class);
                configuration.addAnnotatedClass(Tag.class);
                configuration.addAnnotatedClass(TagRequest.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                log.debug("Исключение! " + e);
            }
        }
        return sessionFactory;
    }
}