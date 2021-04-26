package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * This class will help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that the user interacts with.
 * This class should be accessible by: 1.Management.
 * @author - Justin Rackley
 */
@RestController
@RequestMapping("drug")
public final class DrugController {
    //create a permanent reference to drugService
    private final DrugService drugService;

    @Autowired
    public DrugController(final DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * Allow a user to get a list of all drugs in the system.
     * @return tThe list of all the drugs.
     */
    @GetMapping(path = "getDrugs")
    public List<Drug> getDrugs(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(drugService.getDrugs());
    }

    /**
     * Allow a user to add a new drug to the system.
     * @param formula The drugs formula.
     * @param name The drugs name.
     * @param description The drugs description.
     */
    @PostMapping(path = "addDrug")
    public void addDrug(@RequestParam final String formula, @RequestParam final String name,
                        @RequestParam final String description){
        drugService.add(formula,name,description);
    }

    /**
     * Allow a user to delete a drug.
     * @param formula The formula of the drug to delete.
     */
    @DeleteMapping(path = "deleteDrug")
    public void deleteDrug(@RequestParam final String formula){
        drugService.delete(formula);
    }

    /**
     * Allow a user to change a drugs formula name or description.
     * @param oldFormula The old formula.
     * @param newFormula The new formula.
     * @param name The new name.
     * @param description The new description.
     */
    @PutMapping(path = "updateDrug")
    public void updateDrug(@RequestParam("updateDrug") final String oldFormula,
                           @RequestParam final String newFormula,
                           @RequestParam final String name,
                           @RequestParam final String description){
        drugService.changeFormula(oldFormula, newFormula);
        drugService.changeName(oldFormula, name);
        drugService.changeDescription(oldFormula, description);
    }
}
