package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class acts as an in-between for drugController and DrugRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public final class DrugService {

    //create a permanent reference to the drug repository
    private final DrugRepository repository;

    @Autowired
    public DrugService(final DrugRepository repository) {
        this.repository = repository;
    }

    /**
     * A method that will return a list of all the drugs in the database.
     * @return The list of all drugs.
     */
    public List<Drug> getDrugs(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Allow a user to add a drug to the database.
     * @param formula The drugs formula.
     * @param name The drugs name.
     * @param description The drugs description.
     */
    public void add(final String formula, final String name, final String description){
        Drug drug = create(formula,name,description);
        repository.save(drug);
    }

    /**
     * Allow a user to delete a drug from the database.
     * @param formula The drugs formula.
     */
    public void delete(final String formula){
        //make sure the drug exists, will throw an exception if not
        find(formula);
        //delete the drug from the database
        repository.deleteById(formula);
    }

    /**
     * Allow a user to change the formula of a drug.
     * @param oldFormula The old formula.
     * @param newFormula The new formula.
     */
    public void changeFormula(final String oldFormula,final String newFormula){
        Drug drug = find(oldFormula);
        if (oldFormula != null && oldFormula.length() > 0 && !Objects.equals(oldFormula,newFormula)){
            drug.setFormula(newFormula);
        }
    }

    /**
     * Allow a user to change the name of a drug.
     * @param formula The drugs formula.
     * @param name The drugs name.
     */
    public void changeName(final String formula, final String name){
        Drug drug = find(formula);
        if (name != null && name.length() > 0 && !Objects.equals(name, drug.getName())){
            drug.setFormula(name);
        }
    }

    /**
     * Allow a user to change the description of a drug
     * @param formula The drugs formula.
     * @param description The drugs description.
     */
    public void changeDescription(final String formula, final String description){
        Drug drug = find(formula);
        if (description != null && description.length() > 0 && !Objects.equals(description, drug.getName())){
            drug.setFormula(description);
        }
    }

    //a helper method to create a drug
    private Drug create(final String formula, final String name, final String description){
        return new Drug(formula, name, description);
    }

    //a helper method to find a drug in the database
    private Drug find(final String formula){
        return repository.findById(formula).orElseThrow(() -> new IllegalStateException(
                "Formula with formula  " + formula + " not found."));
    }

}
