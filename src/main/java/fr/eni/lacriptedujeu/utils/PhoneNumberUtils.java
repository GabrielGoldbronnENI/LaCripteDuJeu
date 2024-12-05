package fr.eni.lacriptedujeu.utils;

public class PhoneNumberUtils {

    public static String normalizePhoneNumber(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Le numéro de téléphone est obligatoire");
        }

        phone = phone.replaceAll("\\s+", "");

        if (phone.startsWith("+33")) {
            phone = "0" + phone.substring(3);
        }

        if (!phone.matches("0[67]\\d{8}")) {
            throw new IllegalArgumentException("Le numéro de téléphone doit commencer par 06 ou 07 et contenir 10 chiffres");
        }

        return phone;
    }
}
