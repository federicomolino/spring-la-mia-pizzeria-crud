package it.pizzeria.spring_la_mia_pizzeria_crud.Controller;

import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Autentication;
import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Pizza;
import it.pizzeria.spring_la_mia_pizzeria_crud.repository.AutenticationRepository;
import it.pizzeria.spring_la_mia_pizzeria_crud.repository.Pizze;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class Controllers {

    @Autowired
    private AutenticationRepository autenticationRepository;

    //root a pagina autenticazione
    @GetMapping("/")
    public String PageLogin(){
        return "redirect:/autenticazione";
    }

    @GetMapping("/autenticazione")
    public String ShowPageAutentication(Model model){
        return "pizza/autenticazione";
    }

    @PostMapping("/autenticazione")
    public String Autenticazione(String email, String password, Model model, HttpSession session){
        if (email == null || email.isEmpty()) {
            // Se l'email è nulla o vuota, ritorna alla pagina di login
            return "pizza/autenticazione";
        }

//        verifco se l'utente ha stessa mail e passw
        Autentication user = autenticationRepository.findByEmailAndPassword(email,password);
        if (user != null){
            //salviamo l'utente una volta autenticato
            session.setAttribute("user",user);
            return "redirect:/pizza";
        }else {
            model.addAttribute("errorMessage", "Credenziali Errate");
        }
        return "pizza/autenticazione";
    }

    @Autowired
    private Pizze pizzaRepository; //pizzaRepository è l'oggetto

    @GetMapping("/pizza")
    public String index(@RequestParam(name = "name" , required = false) String name, Model model, HttpSession session){
        //verifico se l'utente si è autenticato
        Autentication user = (Autentication) session.getAttribute("user");
        if (user == null){
            return "redirect:/autenticazione";
        }
        //vista bottone per disconettersi
        model.addAttribute("showButton",true);
        List<Pizza> pizze;
        if (name == null){
            pizze = pizzaRepository.findAll();
        }else {
            pizze = pizzaRepository.findByNameContainingIgnoreCase(name);
            if (pizze.isEmpty()){
                model.addAttribute("errorMessage", "Pizza non trovata");
            }
        }
        model.addAttribute("list", pizze);
        return "pizza/index";
    }

    @GetMapping("/pizza/{id}")
    public String show(@PathVariable("id") Integer id, Model model, HttpSession session){
        Autentication user = (Autentication) session.getAttribute("user");

        if (user ==null){
            return "redirect:/autenticazione";
        }
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()){
            model.addAttribute("pizza",pizzaRepository.findById(id).get());
            return "pizza/show";
        }
        return "pizza/error";
    }

    //Logout
    @GetMapping("/logout")
    public String Lougout(HttpSession session){
        //termino la sessione
        session.invalidate();
        return "redirect:/autenticazione";
    }

    //Aggiungere Pizza
    @GetMapping("/addPizza")
    public String ShowPageRegistrer(Model model, HttpSession session){
        Autentication user = (Autentication) session.getAttribute("user");
        if (session == null){
            return "redirect:/autenticazione";
        }
        model.addAttribute("formAdd", new Pizza());
        return "pizza/addPizza";
    }

    @PostMapping("/addPizza")
    public String addPizza(@Valid @ModelAttribute("formAdd") Pizza pizzaForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "pizza/addPizza";
        } else if (pizzaRepository.existsByName(pizzaForm.getName())) {
            bindingResult.rejectValue("name", "messageError",
                    "Nome pizza già presente nel sistema");
            return "pizza/addPizza";
        }
        //Salvo a db e torno sull'index
        pizzaRepository.save(pizzaForm);
        redirectAttributes.addFlashAttribute("success", "La pizza è stata aggiunta");
        return "redirect:/pizza";
    }
}
