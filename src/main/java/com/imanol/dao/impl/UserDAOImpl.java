package com.imanol.dao.impl;

import com.imanol.dao.UserDAO;
import com.imanol.models.User;
import com.imanol.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl extends GenericDAOImpl<User,Integer> implements UserDAO {
    public UserDAOImpl() {
        super(User.class); // Pasamos la clase User a GenericDAOImpl
    }

    @Override
    public List<Object[]> getAllTicketsByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
                    SELECT t.id, t.attractionName, t.price 
                    FROM Ticket t 
                    WHERE t.user.id = :userId
                    """;
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getAverageSpendingByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
                    SELECT AVG(t.price)
                    FROM Ticket t 
                    WHERE t.user.id = :userId
                    """;
            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejamos excepciones mostrando el error en consola
        }
    }
}
