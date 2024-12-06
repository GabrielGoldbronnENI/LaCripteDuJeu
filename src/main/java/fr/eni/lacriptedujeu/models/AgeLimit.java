package fr.eni.lacriptedujeu.models;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

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
        return "AgeLimit{" +
                "ageLimitID=" + ageLimitID +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AgeLimit ageLimit = (AgeLimit) o;
        return ageLimitID == ageLimit.ageLimitID && Objects.equals(label, ageLimit.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ageLimitID, label);
    }
}
