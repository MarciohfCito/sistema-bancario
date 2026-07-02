package br.uema.bd.banking_system.util;

import java.security.SecureRandom;
import java.time.LocalDate;

public class CardGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder("4"); // exemplo Visa

        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static String generateCVV() {
        int cvv = 100 + random.nextInt(900);
        return String.valueOf(cvv);
    }

    public static LocalDate generateExpirationDate() {
        return LocalDate.now().plusYears(4);
    }

    public static String maskCard(String number) {
        return number.substring(0, 4) + "********" + number.substring(12);
    }
}