package com.imanol.dao;

import com.imanol.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GenericDAOImpl <T,ID> implements GenericDAO<T,ID> {
    private final Class<T> entityType; // Tipo de la entidad

    public GenericDAOImpl(Class<T> entityType) {
        this.entityType = entityType; // Pasamos el tipo de entidad (ej: User.class)
    }

    @Override
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(entity); // Persistimos la entidad
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al guardar la entidad: " + e.getMessage());
        }
    }

    @Override
    public T findById(ID id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(entityType, id); // Buscamos por ID
        } catch (Exception e) {
            System.err.println("Error al buscar la entidad por ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM " + entityType.getSimpleName(); // Ej: "FROM User"
            return session.createQuery(hql, entityType).list();
        } catch (Exception e) {
            System.err.println("Error al listar entidades: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(entity); // Actualizamos la entidad
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al actualizar la entidad: " + e.getMessage());
        }
    }

    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.remove(entity); // Eliminamos la entidad
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }
}
