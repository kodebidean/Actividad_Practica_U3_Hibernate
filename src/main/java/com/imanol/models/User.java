package com.imanol.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Relación Uno a Muchos con Ticket
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    // Constructor vacío
    public User() {
    }

    // Constructor con nombre
    public User(String name) {
        this.name = name;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    // Métodos para manejar la lista de tickets
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setUser(this); // Asignar la relación inversa
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setUser(null); // Romper la relación inversa
    }
}
