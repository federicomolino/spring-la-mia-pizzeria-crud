package it.pizzeria.spring_la_mia_pizzeria_crud.Controller;

import it.pizzeria.spring_la_mia_pizzeria_crud.Entity.Autentication;
import it.pizzeria.spring_la_mia_pizzeria_crud.repository.AutenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Register {

    @Autowired
    private AutenticationRepository autenticationRepository;

    @GetMapping("/registrazione")
    public String ShowPageRegistrer(Model model){
        return "pizza/register";
    }

    @PostMapping("/registrazione")
    public String Register(String name, String surname, String email, String password, Model model){
        //Verifico che la mail non sia già presente
        Autentication userRegister = autenticationRepository.findByEmail(email);
        if (userRegister == null){
            Autentication newUser = new Autentication(email, password,name,surname);
            //mi salva a db il nuovo utente
            autenticationRepository.save(newUser);

            model.addAttribute("showSuccess", "Registrazione Effettuata");
            //return "redirect:/autenticazione";
        }else {
            model.addAttribute("showError", "Indirizzo Mail già presente nel sistema");
        }
        return "pizza/register";
    }

}
