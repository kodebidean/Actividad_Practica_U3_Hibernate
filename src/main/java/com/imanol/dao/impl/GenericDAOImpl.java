package com.imanol.dao.impl;

import com.imanol.exceptions.CustomException;
import com.imanol.exceptions.ExceptionHandler;
import com.imanol.models.Ticket;
import com.imanol.models.User;
import com.imanol.util.HibernateUtil;
import com.imanol.dao.GenericDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GenericDAOImpl <T,ID> implements GenericDAO<T,ID> {
    private final Class<T> entityType; // Tipo de la entidad

    public GenericDAOImpl(Class<T> entityType) {
        this.entityType = entityType; // Pasar el tipo de entidad/clase al constructor de _DAOImpl
    }

    private String getUniqueConstraintCondition(T entity) {
        if (entity instanceof User) {
            return "name = :name";
        } else if (entity instanceof Ticket) {
            return "attractionName = :name AND user.id = :userId AND price = :price";
        }
        throw new IllegalArgumentException("Entidad desconocida para la verificación de duplicados.");
    }

    private void setUniqueConstraintParameters(Query<T> query, T entity) {
        if (entity instanceof User user) {
            query.setParameter("name", user.getName());
        } else if (entity instanceof Ticket ticket) {
            query.setParameter("name", ticket.getAttractionName());
            query.setParameter("userId", ticket.getUser().getId());
            query.setParameter("price", ticket.getPrice());
        } else {
            throw new IllegalArgumentException("Entidad desconocida para la verificación de duplicados.");
        }
    }



    @Override
    public void create(T entity) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            // Verificar si ya existe la entidad (basado en algún criterio único)
            String hql = String.format("FROM %s WHERE %s",
                    entityType.getSimpleName(),
                    getUniqueConstraintCondition(entity));
            Query<T> query = session.createQuery(hql, entityType);
            setUniqueConstraintParameters(query, entity);

            // Solo crear si no existe
            if (query.uniqueResult() == null) {
                session.persist(entity);
                session.getTransaction().commit();
                System.out.println("Entidad creada: " + entity);
            } else {
                System.out.println("Entidad duplicada: " + entity);
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            throw new CustomException("Error al crear la entidad: " + entity, e);
        }
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
            String hql = "FROM " + entityType.getSimpleName(); // Ejemplo-> "FROM User"
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
            session.remove(entity); // Eliminamos la entidad/clase
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(ID id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            T entity = session.find(entityType, id); // Usa entityType aquí
            if (entity != null) {
                session.remove(entity);
            } else {
                throw new CustomException("No se encontró la entidad con ID: " + id);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CustomException("Error al eliminar la entidad con ID: " + id, e);
        }
    }

    public T findByName(String name) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM " + entityType.getName() + " e WHERE e.name = :name";
            Query<T> query = session.createQuery(hql, entityType);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new CustomException("Error al buscar entidad por nombre: " + name, e);
        }
    }


}
