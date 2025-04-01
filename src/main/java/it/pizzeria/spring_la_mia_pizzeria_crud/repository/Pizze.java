package it.pizzeria.spring_la_mia_pizzeria_crud.repository;

import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pizze extends JpaRepository<Pizza,Integer> {
}
