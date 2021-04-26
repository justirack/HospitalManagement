package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * This class acts as an in-between for drugController and DrugRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public class DrugService {

    //create a permanent reference to the drug repository
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    /**
     * a method that will return a list of all the drugs in the database
     * @return the list of all drugs
     */
    public List<Drug> getDrugs(){
        return drugRepository.findAll();
    }

    public void addDrug(String formula, String name, String description){
        Drug drug = createDrug(formula,name,description);
        drugRepository.save(drug);
    }

    public void deleteDrug(String formula){
        //make sure the drug exists, will throw an exception if not
        findDrug(formula);
        //delete the drug from the database
        drugRepository.deleteById(formula);
    }

    public void changeFormula(String oldFormula, String newFormula){
        Drug drug = findDrug(oldFormula);
        if (oldFormula != null && oldFormula.length() > 0 && !Objects.equals(oldFormula,newFormula)){
            drug.setFormula(newFormula);
        }
    }

    public void changeName(String formula, String name){
        Drug drug = findDrug(formula);
        if (name != null && name.length() > 0 && !Objects.equals(name, drug.getName())){
            drug.setFormula(name);
        }
    }

    public void changeDescription(String formula, String description){
        Drug drug = findDrug(formula);
        if (description != null && description.length() > 0 && !Objects.equals(description, drug.getName())){
            drug.setFormula(description);
        }
    }

    //a helper method to create a drug
    private Drug createDrug(String formula, String name, String description){
        return new Drug(formula, name, description);
    }

    //a helper method to find a drug in the database
    private Drug findDrug(String formula){
        return drugRepository.findById(formula).orElseThrow(() -> new IllegalStateException(
                "Formula with formula  " + formula + " not found."));
    }

}
