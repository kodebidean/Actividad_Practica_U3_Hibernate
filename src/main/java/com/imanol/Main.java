package com.imanol;

import com.imanol.dao.TicketDAO;
import com.imanol.dao.UserDAO;
import com.imanol.dao.impl.TicketDAOImpl;
import com.imanol.dao.impl.UserDAOImpl;
import com.imanol.models.Ticket;
import com.imanol.models.User;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializamos los DAOs
        UserDAO userDAO = new UserDAOImpl();
        TicketDAO ticketDAO = new TicketDAOImpl();

        try {
            // Crear usuarios
            // Crear usuario "Sergio"
            User user1 = userDAO.findByName("Sergio");
            if (user1 == null) {
                user1 = new User("Sergio");
                userDAO.create(user1);
                System.out.println("Entidad creada: " + user1);
            } else {
                System.out.println("Entidad duplicada: " + user1);
            }

// Crear usuario "Imanol"
            User user2 = userDAO.findByName("Imanol");
            if (user2 == null) {
                user2 = new User("Imanol");
                userDAO.create(user2);
                System.out.println("Entidad creada: " + user2);
            } else {
                System.out.println("Entidad duplicada: " + user2);
            }

            System.out.println("Usuarios creados:");

            // Leer usuarios
            List<User> users = userDAO.findAll();
            users.forEach(user -> System.out.println(user.getId() + ": " + user.getName()));

            // Crear tickets
            Ticket ticket1 = new Ticket(user1, "Montaña Rusa", BigDecimal.valueOf(10.50));
            Ticket ticket2 = new Ticket(user1, "Casa del Terror", BigDecimal.valueOf(5.75));
            Ticket ticket3 = new Ticket(user2, "Carrusel", BigDecimal.valueOf(3.25));

            ticketDAO.create(ticket1);
            ticketDAO.create(ticket2);
            ticketDAO.create(ticket3);

            System.out.println("\nTickets creados para cada usuario:");

            // Leer tickets con usuarios cargados
            List<Ticket> tickets = ticketDAO.findAllWithUsers();
            tickets.forEach(ticket -> System.out.println(
                    ticket.getUser().getName() + ": " + ticket.getAttractionName() + " - " + ticket.getPrice()
            ));

            // Consultar entradas de un usuario
            System.out.println("\nEntradas de Sergio:");
            List<Ticket> ticketsUser1 = ticketDAO.getTicketsByUserId(user1.getId());
            ticketsUser1.forEach(ticket -> System.out.println(ticket.getAttractionName() + " - " + ticket.getPrice()));

            // Consultar entradas de una atracción determinada
            System.out.println("\nEntradas para 'Montaña Rusa':");
            List<Ticket> ticketsMontañaRusa = ticketDAO.getTicketsByAttractionName("Montaña Rusa");
            ticketsMontañaRusa.forEach(ticket -> System.out.println(ticket.getUser().getName() + " - " + ticket.getPrice()));

            // Consultar el gasto medio de un usuario
            System.out.println("\nGasto medio de Sergio:");
            Double avgSpending = ticketDAO.getAverageSpendingByUserId(user1.getId());
            System.out.println(avgSpending);

            // Actualizar usuario
            user1.setName("Sergio Actualizado");
            userDAO.update(user1);
            System.out.println("\nUsuario actualizado: " + userDAO.findById(user1.getId()).getName());

            // Eliminar un ticket
            System.out.println("\nEliminando el ticket 'Casa del Terror'...");
            ticketDAO.deleteById(ticket2.getId());

            System.out.println("\nTickets restantes:");
            tickets = ticketDAO.findAllWithUsers(); // Usar el método que carga los usuarios asociados
            tickets.forEach(ticket -> System.out.println(ticket.getUser().getName() + ": " + ticket.getAttractionName() + " - " + ticket.getPrice()));

        } catch (Exception e) {
            System.err.println("Se produjo un error en el programa:");
            e.printStackTrace();
        }
    }
}