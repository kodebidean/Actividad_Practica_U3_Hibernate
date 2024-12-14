package com.imanol.exceptions;

import org.hibernate.Session;

public class ExceptionHandler {
    public static void handleException(Exception e) {
        // Aquí puedes definir qué hacer con la excepción
        System.err.println("Se produjo un error: " + e.getMessage());
        e.printStackTrace();
    }

    public static void handleHibernateException(Exception e, Session session) {
        // Si la sesión está activa, realiza un rollback
        if (session != null && session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        // Manejo general de la excepción
        handleException(e);
    }
}
