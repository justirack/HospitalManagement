package com.example.demo.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class will represent a drug in the hospital management system.
 * A drug will have a formula, name and description.
 * @author - Justin Rackley
 */
@Entity
@Table
public final class Drug {
    //a drugs formula is its primary id
    @Id
    @Column(nullable = false)
    private String formula;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    public Drug(final String formula, final String name, final String description) {
        this.formula = formula;
        this.name = name;
        this.description = description;
    }

    public Drug() {
    }

    /**
     * Setter for the drugs formula.
     * @param formula The drugs new formula.
     */
    public void setFormula(final String formula) {
        this.formula = formula;
    }

    /**
     * Setter for the drugs name.
     * @param name The drugs new name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Setter for the drugs description.
     * @param description The drugs new description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for a drugs formula.
     * @return The drugs formula.
     */
    public String getFormula() {
        return formula;
    }

    /**
     * Getter for a drugs name.
     * @return The drugs name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for a drugs description.
     * @return The drugs description.
     */
    public String getDescription() {
        return description;
    }
}