package com.example.demo.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Drug {
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

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormula() {
        return formula;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}