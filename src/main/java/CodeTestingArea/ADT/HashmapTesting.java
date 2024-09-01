package CodeTestingArea.ADT;

import ADT.Hashmap;

public class HashmapTesting {

    public static void main(String[] args) {
        scenario4();
    }

    /** test put, get, size, display function */
    static void scenario1(){
        Hashmap<String, String> hashmap = new Hashmap<>();
        hashmap.put("Malaysia", "Selangor");
        hashmap.put("USA", "New York");
        hashmap.put("UK", "London");
        hashmap.put("China", "Beijing");
        hashmap.put("USAff", "New York2");

        hashmap.display();
        System.out.println("Size: " + hashmap.size());

        System.out.println();
        System.out.println(hashmap.get("US"));
        System.out.println(hashmap.get("Malaysia"));
        System.out.println(hashmap.get("UK"));
        System.out.println(hashmap.get("China"));
        System.out.println(hashmap.get("USAff"));
    }

    /** test clear function*/
    static void scenario2(){
        Hashmap<String, String> hashmap = new Hashmap<>();
        hashmap.put("Malaysia", "Selangor");
        hashmap.put("USA", "New York");
        hashmap.put("UK", "London");

        hashmap.display();

        hashmap.clear();

        hashmap.display(); // didn't display
    }

    /** check if hashmap resizing after hit threshold (capacity * load_factor) */
    static void scenario3(){
        Hashmap<String, String> hashmap = new Hashmap<>();
        hashmap.put("Malaysia", "Selangor");
        hashmap.put("USA", "New York");
        hashmap.put("UK", "London");
        hashmap.put("China", "Beijing");
        hashmap.put("USAff", "New York2");
        hashmap.put("1", "1");
        hashmap.put("2", "2");
        hashmap.put("3", "3");
        hashmap.put("4", "4");
        hashmap.put("5", "5");
        hashmap.put("6", "6");
        hashmap.put("7", "7");
        hashmap.put("8", "8");
        hashmap.put("9", "9");
        hashmap.put("10", "10");

        hashmap.display();
        System.out.println("Size: " + hashmap.size());
    }

    /** test remove and containsKey function*/
    static void scenario4(){
        Hashmap<String, String> hashmap = new Hashmap<>();
        hashmap.put("Malaysia", "Selangor");

        System.out.println("Have Malaysia: " + hashmap.containsKey("Malaysia"));
        System.out.println("Have UK: " + hashmap.containsKey("UK"));

        hashmap.remove("Malaysia");

        System.out.println("Have Malaysia after remove: " + hashmap.containsKey("Malaysia"));
    }
}
