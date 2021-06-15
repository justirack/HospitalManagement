package com.example.demo.drug;

import com.example.demo.exception.CustomException.FailedRequestException;
import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * Allow a user to get a single drug from the database.
     * @param id The id of the drug.
     * @return The drug.
     */
    public Drug getDrug(final long id){
        return find(id);
    }

    /**
     * Allow a user to add a drug to the database.
     * @param formula The drugs formula.
     * @param name The drugs name.
     * @param description The drugs description.
     */
    public void add(final String formula, final String name, final String description){
        repository.save(new Drug(formula,name,description));
    }

    /**
     * Allow a user to delete a drug from the database.
     * @param id The id of the drug to delete.
     */
    public HttpStatus delete(final long id){
        //make sure the drug exists, will throw an exception if not
        find(id);
        //delete the drug from the database
        repository.deleteById(id);

        try {
            //try to find the drug in the database, it should not be there
            repository.findDrugByFormula(id);
        }
        //catch the exception that should be thrown
        catch (InvalidIdException e){
            //return OK since the drug is no longer there
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The drug could not be deleted from the database." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * Allow a user to change the formula of a drug.
     * @param id The old id.
     * @param newFormula The new formula.
     */
    public HttpStatus changeFormula(final long id, final String newFormula) {
        //make sure the drug exists in the database
        final Drug drug = find(id);

        //make sure the formula is not null, not a blank string and not the same as the current formula
        if (newFormula != null && !newFormula.isEmpty() && !Objects.equals(drug.getFormula(), newFormula)) {
            drug.setFormula(newFormula);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The drugs formula could not be changed." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * Allow a user to change the name of a drug.
     * @param id The drugs id.
     * @param name The drugs name.
     */
    public HttpStatus changeName(final long id, final String name){
        //make sure the drug exists in the database
        final Drug drug = find(id);

        //make sure the name is not null, not a blank string and not the same as the current name
        if (name != null && !name.isEmpty() && !Objects.equals(name, drug.getName())){
            drug.setFormula(name);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The drugs name could not be changed." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * Allow a user to change the description of a drug
     * @param id The drugs id.
     * @param description The drugs description.
     */
    public HttpStatus changeDescription(final long id, final String description){
        //make sure the drug exists in the database
        final Drug drug = find(id);

        //make sure the description is not null, not a blank string and not the same as the current description
        if (description != null && !description.isEmpty() && !Objects.equals(description, drug.getDescription())){
            drug.setFormula(description);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The drugs description could not be changed." +
                " Please make sure all information is correct and try again.");
    }

    //a helper method to find a drug in the database
    private Drug find(final long id){
        return repository.findById(id).orElseThrow(() -> new InvalidIdException(
                "Formula with formula  " + id + " not found."));
    }

}
