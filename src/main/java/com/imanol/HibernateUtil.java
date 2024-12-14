package com.imanol;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Inicializar la fábrica de sesiones desde hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Error al inicializar Hibernate: " + ex.getMessage());
        }
    }

    //Obtener una nueva sesión de Hibernate
    public static Session getSession() {
        return sessionFactory.openSession();
    }


    //Cierra la sesión, manejando transacciones automáticamente
    public static void closeSessionWithTransaction(Session session) {
        if (session != null && session.isOpen()) {
            try {
                Transaction transaction = session.getTransaction();
                if (transaction != null && transaction.isActive()) {
                    transaction.commit(); // Confirmar la transacción si está activa
                }
            } catch (Exception e) {
                if (session.getTransaction() != null && session.getTransaction().isActive()) {
                    session.getTransaction().rollback(); // Revertir la transacción en caso de error
                }
                throw e; // Lanzar de nuevo la excepción para manejarla externamente
            } finally {
                session.close(); // Cierra la sesión
            }
        }
    }


    // Cerrar una sesión sin transacciones
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
