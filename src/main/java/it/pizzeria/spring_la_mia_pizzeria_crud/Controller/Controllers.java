package it.pizzeria.spring_la_mia_pizzeria_crud.Controller;

import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Pizza;
import it.pizzeria.spring_la_mia_pizzeria_crud.repository.Pizze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class Controllers {

    @Autowired
    private Pizze pizzaRepository; //pizzaRepository è l'oggetto

    @GetMapping("/pizze")
    public String listPizze(Model model){
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("list", pizze);
        return "pizza/index";
    }
}
