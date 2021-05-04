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
     * @param id The id of the drug to delete.
     */
    @DeleteMapping(path = "delete/{id}")
    public void deleteDrug(@PathVariable final long id){
        service.delete(id);
    }

    /**
     * Allow a user to change a formula.
     * @param id The drugs id.
     * @param newFormula The drugs new formula.
     */
    @PutMapping(path = "changeFormula")
    public void changeFormula(final long id, final String newFormula){
        service.changeFormula(id,newFormula);
    }

    /**
     * Allow a user to change a drugs name.
     * @param id The drugs id.
     * @param name The drugs new name.
     */
    @PutMapping(path = "changeName")
    public void changeName(final long id, final String name){
        service.changeName(id,name);
    }

    /**
     * Allow a user to change a drugs description.
     * @param id The drugs id.
     * @param description The drugs new description.
     */
    @PutMapping(path = "changeDescription")
    public void changeDescription(final long id, final String description){
        service.changeDescription(id,description);
    }
}
