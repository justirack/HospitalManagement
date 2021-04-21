package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DrugController {
    //create a permanent reference to drugService
    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping(path = "getDrugs")
    public List<Drug> getDrugs(){
        return drugService.getDrugs();
    }

    @PostMapping(path = "addDrug")
    public void addDrug(@RequestParam String formula, @RequestParam String name, @RequestParam String description){
        Drug drug = createDrug(formula,name,description);
        drugService.addDrug(formula,name,description);
    }

    @DeleteMapping(path = "deleteDrug")
    public void deleteDrug(@RequestParam String formula,
                           @RequestParam String name,
                           @RequestParam String description){
        Drug drug = createDrug(formula,name,description);
        drugService.deleteDrug(formula,name,description);
    }

    @PutMapping(path = "updateDrug")
    public void updateDrug(@RequestParam("updateDrug") String formula,
                           @RequestParam String name,
                           @RequestParam String description){
        Drug drug = createDrug(formula,name,description);
        drugService.changeFormula(formula);
        drugService.changeName(formula, name);
        drugService.changeDescription(formula, description);
    }

    private Drug createDrug(String formula, String name, String description){
        return new Drug(formula,name,description);
    }
}
