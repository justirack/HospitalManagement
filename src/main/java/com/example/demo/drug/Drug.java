/**
 * this class will represent a drug in the hospital management system
 * a drug will have a formula, name and description
 * @author - Justin Rackley
 */

package com.example.demo.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//create a new table in the database for drugs
@Table
public class Drug {
    //a drugs formula is its primary id
    @Id
    @Column(nullable = false)
    private String formula;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    public Drug(String formula, String name, String description) {
        this.formula = formula;
        this.name = name;
        this.description = description;
    }

    public Drug() {
    }

    /**
     * setter for the drugs formula
     * @param formula the drugs new formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * setter for the drugs name
     * @param name teh drugs new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter for the drugs description
     * @param description the drugs new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for a drugs formula
     * @return the drugs formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * getter for a drugs name
     * @return the drugs name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for a drugs description
     * @return the drugs description
     */
    public String getDescription() {
        return description;
    }
}