package com.imanol.dao;

import com.imanol.models.Ticket;
import java.util.List;

public interface TicketDAO extends GenericDAO <Ticket, Integer> {

    // Obtener todas las entradas de un usuario
    List<Ticket> getTicketsByUserId(int userId);

    // Obtener todas las entradas de una atracción
    List<Ticket> getTicketsByAttractionName(String attractionName);

    // Calcular el gasto medio de un usuario determinado
    Double getAverageSpendingByUserId(int userId);

    // Obtener todos los tickets con sus usuarios
    List<Ticket> findAllWithUsers();


}
