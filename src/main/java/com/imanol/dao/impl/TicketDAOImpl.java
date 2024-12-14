package com.imanol.dao.impl;

import com.imanol.dao.TicketDAO;
import com.imanol.exceptions.CustomException;
import com.imanol.models.Ticket;
import com.imanol.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TicketDAOImpl extends GenericDAOImpl<Ticket, Integer> implements TicketDAO {

    public TicketDAOImpl() {
        super(Ticket.class); // Pasar clase Ticket al constructor de TicketDAOImpl
    }

    @Override
    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT t FROM Ticket t 
            JOIN FETCH t.user 
            WHERE t.user.id = :userId
        """;
            Query<Ticket> query = session.createQuery(hql, Ticket.class);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
            throw new CustomException("Error al obtener entradas del usuario con ID: " + userId, e);
        }
    }


    @Override
    public List<Ticket> getTicketsByAttractionName(String attractionName) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT t FROM Ticket t 
            JOIN FETCH t.user 
            WHERE t.attractionName = :attractionName
        """;
            Query<Ticket> query = session.createQuery(hql, Ticket.class);
            query.setParameter("attractionName", attractionName);
            return query.list();
        } catch (Exception e) {
            throw new CustomException("Error al obtener entradas para la atracción: " + attractionName, e);
        }
    }


    @Override
    public Double getAverageSpendingByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT AVG(price) FROM Ticket WHERE user.id = :userId", Double.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new CustomException("Error al calcular el gasto medio del usuario con ID: " + userId, e);
        }
    }


    @Override
    public List<Ticket> findAllWithUsers() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT t FROM Ticket t JOIN FETCH t.user";
            return session.createQuery(hql, Ticket.class).list();
        } catch (Exception e) {
            throw new CustomException("Error al cargar los tickets con usuarios.", e);
        }
    }

}
