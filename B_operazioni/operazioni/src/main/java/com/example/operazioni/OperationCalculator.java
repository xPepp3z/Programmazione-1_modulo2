package com.example.operazioni;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OperationCalculator {

    @RequestMapping(path = "/addizione/{n1}/{n2}")
    public @ResponseBody int addizione(@PathVariable int n1, @PathVariable int n2) {
        return n1+n2;
    }

    @RequestMapping(path = "/sottrazione/{n1}/{n2}")
    public @ResponseBody int sottrazione(@PathVariable int n1, @PathVariable int n2){
        return n1-n2;
    }

    @RequestMapping(path = "/moltiplicazione/{n1}/{n2}")
    public @ResponseBody int moltiplicazione(@PathVariable int n1, @PathVariable int n2){
        return n1*n2;
    }

    @RequestMapping(path = "/divisione/{n1}/{n2}")
    public @ResponseBody float divisione(@PathVariable float n1, @PathVariable float n2) {
     return n1 / n2;
    }
}







