package it.pizzeria.spring_la_mia_pizzeria_crud.repository;

import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Autentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutenticationRepository extends JpaRepository<Autentication, Integer> {
    @Query("SELECT a FROM Autentication a WHERE a.email = :email AND a.password = :password")
    Autentication findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    Autentication findByEmail(String email);
}
