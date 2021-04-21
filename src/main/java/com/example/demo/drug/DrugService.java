/**
 * This class acts as an in-between for drugController and DrugRepository
 * This is called the "service layer"
 * @autor - Justin Rackley
 */

package com.example.demo.drug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    /**
     * a method to add a drug to the database
     * @param formula the formula of the drug
     * @param name the name of the drug
     * @param description the description of the drug
     */
    public void addDrug(String formula, String name, String description){
        Drug drug = findDrug(formula);
        drugRepository.save(drug);
    }

    /**
     * delete a drug from the database
     * @param formula the formula of the drug to remove
     * @param name the name of the drug to remove
     * @param description the description of the drug to remove
     */
    public void deleteDrug(String formula, String name, String description){
        Drug drug = findDrug(formula);
        drugRepository.delete(drug);
    }

    /**
     * change the formula of a drug
     * @param oldFormula the drugs old formula
     * @param newFormula the drugs new formula
     */
    public void changeFormula(String oldFormula, String newFormula){
        Drug drug = findDrug(oldFormula);
        if (oldFormula != null && oldFormula.length() > 0 && !Objects.equals(oldFormula,newFormula)){
            drug.setFormula(newFormula);
        }
    }

    /**
     * change the name of a drug
     * @param formula the formula of the drug
     * @param name the drugs new name
     */
    public void changeName(String formula, String name){
        Drug drug = findDrug(formula);
        if (name != null && name.length() > 0 && !Objects.equals(name, drug.getName())){
            drug.setFormula(name);
        }
    }

    /**
     * change the description of a drug
     * @param formula the drugs formula
     * @param description the drugs new description
     */
    public void changeDescription(String formula, String description){
        Drug drug = findDrug(formula);
        if (description != null && description.length() > 0 && !Objects.equals(description, drug.getName())){
            drug.setFormula(description);
        }
    }

    //a helper method to get a drug from the database
    private Drug findDrug(String formula){
        return drugRepository.findById(formula).orElseThrow(() -> new IllegalStateException(
                "Formula with formula  " + formula + " not found."));
    }

}
