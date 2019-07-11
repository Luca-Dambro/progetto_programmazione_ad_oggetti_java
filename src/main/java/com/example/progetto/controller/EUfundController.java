package com.example.progetto.controller;

import com.example.progetto.model.ProvaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.EUfundService;
import java.util.Vector;


@RestController

public class EUfundController {
    /*@Autowired*/
    public EUfundService EufundService;

    @GetMapping("/alldataset")
    public Vector<EUfund> retrieveEufunds() {
        return EufundService.getEufunds();
    }


}
