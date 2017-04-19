/*
 * Copyright (c) 2012 by VeriFone, Inc.
 * All Rights Reserved.
 *
 * THIS FILE CONTAINS PROPRIETARY AND CONFIDENTIAL INFORMATION
 * AND REMAINS THE UNPUBLISHED PROPERTY OF VERIFONE, INC.
 *
 * Use, disclosure, or reproduction is prohibited
 * without prior written approval from VeriFone, Inc.
 */
package by.autobazar.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static HibernateUtil util = null;
    private static Logger log = Logger.getLogger(HibernateUtil.class);
    private SessionFactory sessionFactory = null;
    private final ThreadLocal sessions = new ThreadLocal();

    private HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure()
                    .setNamingStrategy(new CustomNamingStrategy());
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession () {
        Session session = (Session) sessions.get();
        if (session == null) {
            session = sessionFactory.openSession();
            sessions.set(session);

        }
        return session;
    }

    public void closeSession (Session session) {
        sessions.set(null);
        if (session != null) {
            session.close();
        }
    }

    public static synchronized HibernateUtil getHibernateUtil(){
        if (util == null){
            util = new HibernateUtil();
        }
        return util;
    }

}
