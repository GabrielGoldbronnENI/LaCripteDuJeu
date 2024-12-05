package fr.eni.lacriptedujeu.models;

import jakarta.validation.constraints.NotBlank;

public class AgeLimit {

    private int ageLimitID;

    @NotBlank(message = "Le Label de la limite d'age est obligatoire")
    private String label;

    public int getAgeLimitID() {
        return ageLimitID;
    }

    public void setAgeLimitID(int productId) {
        this.ageLimitID = productId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "ageLimitID=" + ageLimitID +
                ", label='" + label + '\'' +
                '}';
    }
}
