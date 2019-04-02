package com.oasys.oalcfdemoweb.controller;

import com.oasys.oaapi.entity.Demo;
import com.oasys.oaapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/queryAll")
    public ModelAndView queryAll(Demo p){
        ModelAndView mv = new ModelAndView("/page/person_list");
        mv.addObject("list",personService.queryAll(p));
        return mv;
    }
}
