package com.httprestdemo.restservicesdemo.randomgenerators;

import java.math.BigInteger;

/**
 * Created by dgliga on 28.11.2016.
 */
public class IbanGenerator {

    private static String country = "RO";

    /**
     * This method returns a 24 digit iban valid in Romania
     *
     * @return
     */
    public static String generateIban(String bankSwift) {
        String accountNumber = getRandomAccountNumber(16);

        return country + getCheckDigits(accountNumber, bankSwift) + bankSwift + accountNumber;
    }

    /**
     * This method returns a random account number of variable size.
     *
     * @param numberOfDigits
     * @return
     */
    private static String getRandomAccountNumber(int numberOfDigits) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < numberOfDigits; i++) {
            str.append(NumberGenerator.randInt(0, 9));
        }

        return str.toString();
    }

    /**
     * This method returns valid check digits present after the country code.
     *
     * @param accountNumber
     * @return
     */
    private static String getCheckDigits(String accountNumber, String bankSwift) {
        String convertedNumberToTest = convertBankCode(bankSwift) + accountNumber + convertCountryCode(country);
        BigInteger convertedIban = new BigInteger(convertedNumberToTest);
        BigInteger verificationMod = BigInteger.valueOf(97);

        BigInteger check = convertedIban.mod(verificationMod);
        int newCheckDigits = 98 - check.intValue();
        String checkDigitsFinal;
        if (newCheckDigits < 10) {
            checkDigitsFinal = "0" + newCheckDigits;
        } else {
            checkDigitsFinal = String.valueOf(newCheckDigits);
        }

        return checkDigitsFinal;
    }

    /**
     * This method converts the bank code letters to values needed to pass validation.
     *
     * @param bankSwift
     * @return
     */
    private static String convertBankCode(String bankSwift) {
        StringBuilder convertedBankCode = new StringBuilder();

        if (bankSwift.length() > 4) {
            bankSwift = bankSwift.substring(0, 4);
        }

        for (int i = 0; i < bankSwift.length(); i++) {
            if ((int) bankSwift.charAt(i) > 64) {
                convertedBankCode.append(String.valueOf(((int) bankSwift.charAt(i) - 55)));
            } else {
                convertedBankCode.append(bankSwift.charAt(i));
            }
        }

        return convertedBankCode.toString();
    }

    /**
     * This method converts the country code letters to values needed to pass validation.
     *
     * @param country id representing the IBAN head depending on country - RO for Romania
     * @return
     */
    private static String convertCountryCode(String country) {
        String headOfIban = country + "00";
        StringBuilder convertedCountryCode = new StringBuilder();
        for (int i = 0; i < headOfIban.length(); i++) {
            if ((int) headOfIban.charAt(i) > 64) {
                convertedCountryCode.append(String.valueOf(((int) headOfIban.charAt(i) - 55)));
            } else {
                convertedCountryCode.append(headOfIban.charAt(i));
            }
        }

        return convertedCountryCode.toString();
    }
}

