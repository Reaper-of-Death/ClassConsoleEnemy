package org.example.dao;

import org.example.Walk;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class WalkDao {

    public void save(Walk walk) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(walk);
            tx.commit();
        }
    }

    public Walk findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Walk.class, id);
        }
    }

    public void deleteById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Walk walk = session.get(Walk.class, id);
            if (walk != null) {
                session.delete(walk);
            }
            tx.commit();
        }
    }

    public List<Walk> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Walk", Walk.class).list();
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }
}
