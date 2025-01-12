package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class UserEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // You can add other user properties like email, etc.
}
