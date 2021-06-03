package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * Allow a user to get a single drug from the database.
     * @param id The id of the drug.
     * @return The drug.
     */
    @GetMapping(path = "getDrug/{id}")
    public Drug getDrug(@PathVariable final long id){
        return service.getDrug(id);
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
    @PutMapping(path = "changeFormula/{id}/{newFormula}")
    public HttpStatus changeFormula(@PathVariable final long id, @PathVariable final String newFormula){
        service.changeFormula(id,newFormula);

        if (service.getDrug(id).getFormula().equals(newFormula)) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    /**
     * Allow a user to change a drugs name.
     * @param id The drugs id.
     * @param name The drugs new name.
     */
    @PutMapping(path = "changeName/{id}/{name}")
    public HttpStatus changeName(@PathVariable final long id, @PathVariable final String name){
        service.changeName(id,name);

        if (service.getDrug(id).getName().equals(name)){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    /**
     * Allow a user to change a drugs description.
     * @param id The drugs id.
     * @param description The drugs new description.
     */
    @PutMapping(path = "changeDescription/{id}/{description}")
    public HttpStatus changeDescription(@PathVariable final long id, @PathVariable final String description){
        service.changeDescription(id,description);

        if (service.getDrug(id).getDescription().equals(description)){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
