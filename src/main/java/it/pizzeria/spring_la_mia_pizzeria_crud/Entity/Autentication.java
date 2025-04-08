package it.pizzeria.spring_la_mia_pizzeria_crud.Entity;

import jakarta.persistence.*;

@Entity
public class Autentication {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column()
    private String name;

    @Column()
    private String surname;

    public Autentication (String email,String password, String name, String surname){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public Autentication(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
