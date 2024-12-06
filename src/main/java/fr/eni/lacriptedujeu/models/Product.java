package fr.eni.lacriptedujeu.models;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @NotNull(message = "L'âge limite est obligatoire")
    private AgeLimit ageLimit;

    private List<Genre> genres;

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

    public AgeLimit getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(AgeLimit ageLimit) {
        this.ageLimit = ageLimit;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID == product.productID && Objects.equals(ageLimit, product.ageLimit) && Objects.equals(genres, product.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, ageLimit, genres);
    }
}
