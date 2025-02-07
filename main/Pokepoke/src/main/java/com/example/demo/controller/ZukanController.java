package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.ZukanRepository;

@Controller
@RequestMapping("/zukan")
public class ZukanController {
    @Autowired
    private ZukanRepository zukanRepository;
    //private ZukanRepository zukanRepository = new ZukanRepository();
    @GetMapping
    public String listZukans(Model model) {
    	
        model.addAttribute("items", zukanRepository.findAllByOrderByIdAsc());
        //model.addAttribute("items", zukanRepository.findAll());
        
        //System.out.println(model);
        //return "zukan/zukan.html";
        return "zukans";
    }

}
