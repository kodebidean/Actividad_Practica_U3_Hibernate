package com.imanol.dao;

import java.util.List;

public interface GenericDAO <T,ID> {
    void save(T entity);            // Guardar una entidad
    T findById(ID id);              // Buscar por ID
    List<T> findAll();              // Listar todas las entidades
    void update(T entity);          // Actualizar una entidad
    void delete(T entity);          // Eliminar una entidad
}
