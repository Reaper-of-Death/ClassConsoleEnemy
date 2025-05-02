package org.example.dao;

import org.example.Fly;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FlyDao {
    public void save(Fly fly) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(fly);
            tx.commit();
        }
    }

    public Fly findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Fly.class, id);
        }
    }

    public void deleteById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Fly fly = session.get(Fly.class, id);
            if (fly != null) session.delete(fly);
            tx.commit();
        }
    }

    public List<Fly> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Fly", Fly.class).list();
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }
}