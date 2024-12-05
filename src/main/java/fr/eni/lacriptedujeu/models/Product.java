package fr.eni.lacriptedujeu.models;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class Product {

    private int productID;

    @NotBlank(message = "Le titre du produit est obligatoire")
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
    private String title;

    @NotBlank(message = "Le temps de jeu est obligatoire")
    @Size(max = 255, message = "Le temps de jeu ne doit pas dépasser 255 caractères")
    private String playTime;

    @NotNull(message = "Le tarif est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le tarif doit être supérieur à 0")
    @Digits(integer = 8, fraction = 2, message = "Le tarif doit avoir au maximum 8 chiffres avant la virgule et 2 après")
    private BigDecimal tariff;

    @Min(value = 1, message = "L'âge limite est obligatoire")
    private int ageLimit;

    private AgeLimit ageLimitDetails;

    private List<Integer> genres;

    private List<Genre> genresDetails;

    public int getProductID() {
        return productID;
    }

    public void setProductId(int productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public AgeLimit getAgeLimitDetails() {
        return ageLimitDetails;
    }

    public void setAgeLimitDetails(AgeLimit ageLimitDetails) {
        this.ageLimitDetails = ageLimitDetails;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }


    public List<Genre> getGenresDetails() {
        return genresDetails;
    }

    public void setGenresDetails(List<Genre> genresDetails) {
        this.genresDetails = genresDetails;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productID +
                ", title='" + title + '\'' +
                ", playTime='" + playTime + '\'' +
                ", tariff=" + tariff +
                ", ageLimit=" + ageLimit +
                ", genres=" + genres +
                '}';
    }
}
