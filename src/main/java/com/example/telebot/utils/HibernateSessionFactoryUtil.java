package com.example.telebot.utils;



import com.example.telebot.Project;
import com.example.telebot.Tag;
import com.example.telebot.User;
import com.example.telebot.Task;
import org.hibernate.SessionFactory;
import com.example.telebot.quartz_try.payload.TagRequest;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}