package it.pizzeria.spring_la_mia_pizzeria_crud.Controller;

import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Pizza;
import it.pizzeria.spring_la_mia_pizzeria_crud.repository.Pizze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class Controllers {
    @GetMapping("/")
    public String InitialPage(){
        return "redirect:/pizza";
    }

    @Autowired
    private Pizze pizzaRepository; //pizzaRepository è l'oggetto

    @GetMapping("/pizza")
    public String index(@RequestParam(name = "name" , required = false) String name, Model model){
        List<Pizza> pizze;
        if (name == null){
            pizze = pizzaRepository.findAll();
        }else {
            pizze = pizzaRepository.findByNameContainingIgnoreCase(name);
        }
        model.addAttribute("list", pizze);
        return "pizza/index";
    }

    @GetMapping("/pizza/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()){
            model.addAttribute("pizza",pizzaRepository.findById(id).get());
            return "pizza/show";
        }
        return "pizza/error";
    }
}
