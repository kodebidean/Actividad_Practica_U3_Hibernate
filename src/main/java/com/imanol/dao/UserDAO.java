package com.imanol.dao;

import com.imanol.models.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User, Integer> {
    // Obtener todos los tickets de un usuario específico
    List<Object[]> getAllTicketsByUserId(int userId);

    // Gasto medio de un usuario específico en las atracciones
    Double getAverageSpendingByUserId(int userId);
}
