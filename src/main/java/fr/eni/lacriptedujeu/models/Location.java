package fr.eni.lacriptedujeu.models;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Location {
    private Long locationID;
    private BigDecimal price;
    private int productID;
    @Min(value = 1, message = "l'utilisateur est obligatoire")
    private int userID;
    private int rentalStatusID;
    @Min(value = 1, message = "l'exemplaire est obligatoire")
    private int copyID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getLocationID() {
        return locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRentalStatusID() {
        return rentalStatusID;
    }

    public void setRentalStatusID(int rentalStatusID) {
        this.rentalStatusID = rentalStatusID;
    }

    public int getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString
    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", price=" + price +
                ", productID=" + productID +
                ", userID=" + userID +
                ", rentalStatusID=" + rentalStatusID +
                ", copyID=" + copyID +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
