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
        Drug drug = createDrug(formula,name,description);
        drugRepository.save(drug);
    }

    public void deleteDrug(String formula, String name, String description){
        Drug drug = createDrug(formula,name,description);
        drugRepository.delete(drug);
    }

    public void changeFormula(String formula){
        
    }

    public void changeName(String name){

    }

    public void changeDescription(String description){

    }

    public Drug createDrug(String formula, String name, String description){
        return new Drug(formula,name,description);
    }

}
