package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final DrugService service;

    @Autowired
    public DrugController(final DrugService service) {
        this.service = service;
    }

    /**
     * Allow a user to get a list of all drugs in the system.
     * @return tThe list of all the drugs.
     */
    @GetMapping(path = "getDrugs")
    public List<Drug> getDrugs(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(service.getDrugs());
    }

    /**
     * Allow a user to add a new drug to the system.
     * @param formula The drugs formula.
     * @param name The drugs name.
     * @param description The drugs description.
     */
    @PostMapping(path = "add")
    public void addDrug(final String formula, final String name, final String description){
        service.add(formula,name,description);
    }

    /**
     * Allow a user to delete a drug.
     * @param formula The formula of the drug to delete.
     */
    @DeleteMapping(path = "{delete}")
    public void deleteDrug(@PathVariable("delete") final String formula){
        service.delete(formula);
    }

    /**
     * Allow a user to change a formula.
     * @param oldFormula The drugs old formula.
     * @param newFormula The drugs new formula.
     */
    public void changeFormula(final String oldFormula, final String newFormula){
        service.changeFormula(oldFormula,newFormula);
    }

    /**
     * Allow a user to change a drugs name.
     * @param formula The drugs formula.
     * @param name The drugs new name.
     */
    public void changeName(final String formula, final String name){
        service.changeName(formula,name);
    }

    /**
     * Allow a user to change a drugs description.
     * @param formula The drugs formula.
     * @param description The drugs new description.
     */
    public void changeDescription(final String formula, final String description){
        service.changeDescription(formula,description);
    }
}
