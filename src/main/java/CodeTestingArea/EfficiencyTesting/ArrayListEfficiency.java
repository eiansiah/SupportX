package CodeTestingArea.EfficiencyTesting;

import ADT.ArrayList;

import java.text.NumberFormat;
import java.util.Locale;

public class ArrayListEfficiency {
    public static void main(String[] args) {
        System.out.println("Time taken to add 999 element (custom): " + NumberFormat.getNumberInstance(Locale.US).format(custom(999)) + "ns");
        System.out.println("Time taken to add 999 element (university): " + NumberFormat.getNumberInstance(Locale.US).format(university(999)) + "ns");
        System.out.println("Time taken to add 999 element (java): " + NumberFormat.getNumberInstance(Locale.US).format(java(999)) + "ns");
        System.out.println();

        testAvgRun(10, 999);
        testAvgRun(100, 999);
        testAvgRun(999, 999);
        testAvgRun(9999, 999);
        testAvgRun(99999, 999);
        testAvgRun(999999, 999);
    }

    public static void testAvgRun(int sampleSize, int runs){
        long custom = 0;
        long uni= 0;
        long java = 0;

        for (int i = 0; i < runs; i++) {
            uni += university(sampleSize);
        }

        for (int i = 0; i < runs; i++) {
            custom += custom(sampleSize);
        }

        for (int i = 0; i < runs; i++) {
            java += java(sampleSize);
        }

        System.out.println("Average custom of "+ runs + " runs adding " +sampleSize+" element: " + custom / runs + " ns");
        System.out.println("Average uni of "+ runs + " runs adding " +sampleSize+" element: " + uni / runs + " ns");
        System.out.println("Average java of "+ runs + " runs adding " +sampleSize+" element: " + java / runs + " ns");
        System.out.println();
    }

    public static long university(int sampleSize) {
        long startTime2 = System.nanoTime();

        UniArrayList<String> listJava = new UniArrayList<>();

        for (int i = 0; i < sampleSize; i++) {
            listJava.add(i + "");
        }

        long endTime2 = System.nanoTime();

        long total = (endTime2 - startTime2);

        //System.out.println("Time taken (university): " + NumberFormat.getNumberInstance(Locale.US).format(total) + "ns");

        return total;
    }

    public static long custom(int sampleSize){
        long startTime = System.nanoTime();

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < sampleSize; i++) {
            list.add(i + "");
        }

        long endTime = System.nanoTime();

        long total =(endTime - startTime);

        //System.out.println("Time taken (custom): " + NumberFormat.getNumberInstance(Locale.US).format(total) + "ns");

        return total;
    }

    public static long java(int sampleSize){
        long startTime = System.nanoTime();

        java.util.ArrayList<String> list = new java.util.ArrayList<>();

        for (int i = 0; i < sampleSize; i++) {
            list.add(i + "");
        }

        long endTime = System.nanoTime();

        long total =(endTime - startTime);

        //System.out.println("Time taken (custom): " + NumberFormat.getNumberInstance(Locale.US).format(total) + "ns");

        return total;
    }

}
