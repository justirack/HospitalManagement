/**
 * This class will help serve REST endpoints and perform CRUD operations
 * this is the "API layer" that the user interacts with
 * this class should be accessible by: 1.Management
 * @author - Justin Rackley
 */

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

    /**
     * allow a user to get a list of all drugs in the system
     * @return the list of all the drugs
     */
    @GetMapping(path = "getDrugs")
    public List<Drug> getDrugs(){
        return drugService.getDrugs();
    }

    /**
     * allow a user to add a new drug to the system
     * @param formula the drugs formula
     * @param name the drugs name
     * @param description the drugs description
     */
    @PostMapping(path = "addDrug")
    public void addDrug(@RequestParam String formula, @RequestParam String name, @RequestParam String description){
        drugService.addDrug(formula);
    }

    /**
     * allow a user to delete a drug
     * @param formula the formula of the drug to delete
     * @param name the name of the drug to delete
     * @param description the description of the drug to delete
     */
    @DeleteMapping(path = "deleteDrug")
    public void deleteDrug(@RequestParam String formula,
                           @RequestParam String name,
                           @RequestParam String description){
        drugService.deleteDrug(formula,name,description);
    }

    /**
     * allow a user to change a drugs formula name or description
     * @param oldFormula the old formula
     * @param newFormula the new formula
     * @param name the new name
     * @param description the new description
     */
    @PutMapping(path = "updateDrug")
    public void updateDrug(@RequestParam("updateDrug") String oldFormula,
                           @RequestParam String newFormula,
                           @RequestParam String name,
                           @RequestParam String description){
        drugService.changeFormula(oldFormula, newFormula);
        drugService.changeName(oldFormula, name);
        drugService.changeDescription(oldFormula, description);
    }
}
