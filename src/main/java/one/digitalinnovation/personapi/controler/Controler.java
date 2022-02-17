package one.digitalinnovation.personapi.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controler{
    @RequestMapping("/")
    public String index(){
        return "My first Spring Boot";
    }
}