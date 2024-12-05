package fr.eni.lacriptedujeu.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Copy {

    private int copyID;

    @NotBlank(message = "Le Code barre est obligatoire")
    @Size(max = 13, min = 13, message = "Le code barre doit faire 13 caractères")
    private String barcode;

    private boolean status;

    @Min(value = 1, message = "Le produit associé est obligatoire")
    private int productID;

    private Product productDetails;

    public int getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Product getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(Product productDetails) {
        this.productDetails = productDetails;
    }


    @Override
    public String toString() {
        return "Copy{" +
                "copyID=" + copyID +
                ", status=" + status +
                ", productID=" + productID +
                ", productDetails=" + (productDetails != null ? productDetails : "null") +
                '}';
    }
}
