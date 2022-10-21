package com.api.version.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin(origins = "*")
public class Cadastro {
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
