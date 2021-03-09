package com.httprestdemo.restservicesdemo.randomgenerators;

import java.io.IOException;
import java.util.Random;

import static com.httprestdemo.restservicesdemo.randomgenerators.NumberGenerator.randInt;


/**
 * Created by dgliga on 15.11.2016.
 * This class contains some helpers like cnpGenerator or cuiGenerator, that you will not find easily in other places.
 * Use Fairy if you need to create random addresses, phones, emails, etc.
 */
public class PersonalInfoGenerator {

    private static String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk"};
    private static String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak"};
    private static String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur"};

    private static final String CONS = "zxcvbnmlkjhgfdsqwrtyp"; //String which store the consonances
    private static final String VOWELS = "aeiou";//String which store vowels
    private static Random rand = new Random();

    public static String generateName(int len) { //len define length of names
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0)
                sb.append(CONS.charAt(rand.nextInt(CONS.length())));
            else
                sb.append(VOWELS.charAt(rand.nextInt(VOWELS.length())));
        }
        String generatedName = sb.toString();
        generatedName = generatedName.substring(0, 1).toUpperCase() + generatedName.substring(1);

        return generatedName;
    }

    public static String generateFullName() {

        return Beginning[rand.nextInt(Beginning.length)] + Middle[rand.nextInt(Middle.length)] + End[rand.nextInt(End.length)]
                + " " + Beginning[rand.nextInt(Beginning.length)] + Middle[rand.nextInt(Middle.length)] + End[rand.nextInt(End.length)];
    }

    public static String generateOrcName() {
        NameGenerator nameGenerator;
        String name;
        try {
            nameGenerator = new NameGenerator("src/main/resources/names/names.txt");
            name = nameGenerator.compose(3);
        } catch (IOException e) {
            e.printStackTrace();
            name = "Andronicus";
        }

        return name;
    }

    public static String generateCUI() {
        //build reverse key for cui validation
        int[] reverse_key = {1, 2, 3, 5, 7, 1, 2, 3, 5, 7};
        int rest;
        StringBuilder ss;

        do {
            //generate random reverse cui and add 1 as first char
            String cui = new StringBuilder(Integer.toString(randInt(100000000, 999999999))).append("1").reverse().toString();

            //transform reversed cui string to int array
            String[] arr = cui.split("(?!^)");
            int[] reverseCuiArr = new int[arr.length];

            for (int i = 0; i < arr.length; i++) {
                reverseCuiArr[i] = Integer.parseInt(arr[i]);
            }

            //calculate final character using key
            int sum = 0;
            for (int i = 1; i < reverse_key.length; i++) {
                sum += reverseCuiArr[i] * reverse_key[i];
            }
            sum = sum * 10;

            rest = sum % 11;
            //add control char at first position of the reversed cui array
            if (rest < 10) {
                reverseCuiArr[0] = rest;
            } else if (rest == 10) {
                reverseCuiArr[0] = 0;
            }

            //transform to string and reverse order for final valid cui
            ss = new StringBuilder();

            for (int i : reverseCuiArr) {
                String s = Integer.toString(i);
                ss.append(s);
            }
        } while (rest == 10);

        return ss.reverse().toString();
    }

    public static String generateCNP() {
        String sex = Integer.toString(randInt(1, 2));
        String an = Integer.toString(randInt(30, 80));
        int l = randInt(1, 12);
        String luna = Integer.toString(l);
        String zi = Integer.toString(randInt(10, 30));
        String judet = Integer.toString(randInt(10, 52));
        String cod = Integer.toString(randInt(100, 999));
        StringBuilder sb = new StringBuilder();
        StringBuilder cnp = new StringBuilder();

        if (l < 10) {
            sb.append(sex).append(an).append("0").append(luna).append(zi).append(judet).append(cod);
        } else {
            sb.append(sex).append(an).append(luna).append(zi).append(judet).append(cod);
        }

        String[] arr = sb.toString().split("(?!^)");

        int[] finalArr = new int[arr.length + 1];

        for (int i = 0; i < arr.length; i++) {
            finalArr[i] = Integer.parseInt(arr[i]);
        }

        int[] key = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};

        int sum = 0;

        for (int i = 0; i <= 11; i++) {
            sum += finalArr[i] * key[i];
        }

        if (sum % 11 < 10) {
            finalArr[12] = sum % 11;
        } else {
            finalArr[12] = 1;
        }

        for (int i : finalArr) {
            cnp.append(Integer.toString(i));
        }

        return cnp.toString();
    }
}