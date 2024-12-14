package com.imanol.dao.impl;

import com.imanol.dao.UserDAO;
import com.imanol.exceptions.ExceptionHandler;
import com.imanol.models.User;
import com.imanol.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO {

    public UserDAOImpl() {
        super(User.class); // Pasar la Clase User al constructor de UserDAOImpl
    }

    @Override
    public List<Object[]> getAllTicketsByUserId(int userId) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            String hql = """
                    SELECT t.id, t.attractionName, t.price 
                    FROM Ticket t 
                    WHERE t.user.id = :userId
                    """;
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            ExceptionHandler.handleHibernateException(e, session);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User findByName(String name) {
        return super.findByName(name); // Reutiliza el método del genérico
    }

}
