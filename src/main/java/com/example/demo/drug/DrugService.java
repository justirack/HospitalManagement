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

    public List<Drug> getDrugs(){
        return drugRepository.findAll();
    }

    public void addDrug(String formula, String name, String description){
        Drug drug = findDrug(formula);
        drugRepository.save(drug);
    }

    public void deleteDrug(String formula, String name, String description){
        Drug drug = findDrug(formula);
        drugRepository.delete(drug);
    }

    public void changeFormula(String formula){
        Drug drug = findDrug(formula);
        if (formula != null && formula.length() > 0){
            drug.setFormula(formula);
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

    public Drug findDrug(String formula){
        return drugRepository.findById(formula).orElseThrow(() -> new IllegalStateException(
                "Formula with formula  " + formula + " not found."));
    }

}
