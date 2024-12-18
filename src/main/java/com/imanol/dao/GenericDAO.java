package com.imanol.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO <T,ID> {
    void save(T entity);            // Guardar una entidad
    T findById(ID id);              // Buscar por ID
    List<T> findAll();              // Listar todas las entidades
    void create(T entity);          // Crear una nueva instancia
    void update(T entity);          // Actualizar una entidad
    void delete(T entity);          // Eliminar una entidad
    void deleteById(ID id);         // Eliminar una entidad por el ID

}
