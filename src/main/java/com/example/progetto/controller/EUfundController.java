package com.example.progetto.controller;

import com.example.progetto.model.ProvaClass;
import org.springframework.web.bind.annotation.*;


@RestController

public class EUfundController {
    @GetMapping ("/prova")
    public ProvaClass myMethod(@RequestParam(name="parametro1", defaultValue="DEFAULT!") String parametro1) {

        return new ProvaClass("Michele", "Zurlo");

    }

    @PostMapping("/prova")
    public ProvaClass myMethod2(@RequestBody ProvaClass body) {
        return body;
    }

}
