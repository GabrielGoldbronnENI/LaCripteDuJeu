package fr.eni.lacriptedujeu.models;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Location {
    private Long locationID;
    private BigDecimal price;
    @NotNull(message = "l'utilisateur est obligatoire")
    private User user;
    private int rentalStatusID;
    @NotNull(message = "l'exemplaire est obligatoire")
    private Copy copy;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRentalStatusID() {
        return rentalStatusID;
    }

    public void setRentalStatusID(int rentalStatusID) {
        this.rentalStatusID = rentalStatusID;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return rentalStatusID == location.rentalStatusID && Objects.equals(locationID, location.locationID) && Objects.equals(price, location.price) && Objects.equals(user, location.user) && Objects.equals(copy, location.copy) && Objects.equals(createdAt, location.createdAt) && Objects.equals(updatedAt, location.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationID, price, user, rentalStatusID, copy, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", price=" + price +
                ", user=" + user +
                ", rentalStatusID=" + rentalStatusID +
                ", copy=" + copy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
