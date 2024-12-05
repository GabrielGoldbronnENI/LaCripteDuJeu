package fr.eni.lacriptedujeu.models;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class Genre {

    private int genreID;

    @NotBlank(message = "Le titre du produit est obligatoire")
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
    private String title;

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int productID) {
        this.genreID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return genreID == genre.genreID && Objects.equals(title, genre.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreID, title);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreID=" + genreID +
                ", title='" + title + '\'' +
                '}';
    }
}
