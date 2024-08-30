package org.example.EfficiencyTesting;

import Libraries.ArrayList;

import java.text.NumberFormat;
import java.util.Locale;

public class ArrayListEfficiency {

    public static void main(String[] args) {

        System.out.println("Time taken (university): " + NumberFormat.getNumberInstance(Locale.US).format(university()) + "ns");
        System.out.println("Time taken (custom): " + NumberFormat.getNumberInstance(Locale.US).format(custom()) + "ns");


        long custom = 0;
        long uni= 0;
        int time= 100;

        for (int i = 0; i < time; i++) {
            uni += university();
        }

        for (int i = 0; i < time; i++) {
            custom += custom();
        }



        System.out.println("Average custom: " + custom / time);
        System.out.println("Average uni: " + uni / time);

    }

    public static long university() {
        long startTime2 = System.nanoTime();

        UniArrayList<String> listJava = new UniArrayList<>();

        for (int i = 0; i < 999999; i++) {
            listJava.add(i + "");
        }

        long endTime2 = System.nanoTime();

        long total = (endTime2 - startTime2);

        //System.out.println("Time taken (university): " + NumberFormat.getNumberInstance(Locale.US).format(total) + "ns");

        return total;
    }

    public static long custom(){
        long startTime = System.nanoTime();

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 999999; i++) {
            list.add(i + "");
        }

        long endTime = System.nanoTime();

        long total =(endTime - startTime);

        //System.out.println("Time taken (custom): " + NumberFormat.getNumberInstance(Locale.US).format(total) + "ns");

        return total;
    }

}
