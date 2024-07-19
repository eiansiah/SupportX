package org.example.TestingArea;

import Libraries.ArrayList;

public class ArrayListTesting {

    public static void main(String[] args) {
        scenario5();
    }

    /** test contains 'string' function and size function*/
    static void scenario1(){
        ArrayList<String> strings = new ArrayList();

        strings.add("Hello");
        strings.add("ME");

        //strings.display();

        System.out.println("ArrayList size: " + strings.size());
        System.out.println(strings.contains("M"));
    }

    /** test contains 'class' function and indexOf function */
    static void scenario2(){
        ArrayList<testingClass> list = new ArrayList();

        testingClass t1 = new testingClass(40, "LOL");
        testingClass t2 = new testingClass(40, "LOL");
        testingClass t3 = new testingClass(20, "New");

        list.add(t1);
        list.add(t3);

        System.out.println("Testing contains t1: " + list.contains(t1) + " index pos: " + list.indexOf(t1));
        System.out.println("Testing contains t3: " + list.contains(t3) + " index pos: " + list.indexOf(t3));
        System.out.println("Testing contains t2: " + list.contains(t2) + " index pos: " + list.indexOf(t2));
    }

    /** test get function*/
    static void scenario3(){
        ArrayList<String> strings = new ArrayList();

        strings.add("Hello");
        strings.add("ME");

        //strings.display();

        System.out.println(strings.get(0));
        System.out.println(strings.get(1));

        //error
        System.out.println(strings.get(2));
        System.out.println(strings.get(-1));
    }

    /** test remove and clear function*/
    static void scenario4(){
        ArrayList<testingClass> list = new ArrayList<>();

        testingClass t1 = new testingClass(40, "LOL");
        testingClass t2 = new testingClass(40, "LOL");
        testingClass t3 = new testingClass(20, "New");

        list.add(t1);
        list.add(t2);
        list.add(t3);

        System.out.println("Testing contains t1: " + list.contains(t1));
        list.remove(0);
        System.out.println("now: " + list.contains(t1));

        System.out.println("Size: " + list.size());
        list.add(null);
        System.out.println("now (after add null): " + list.size());
        list.remove(null);
        System.out.println("now (after remove null): " + list.size());

        list.clear();
        System.out.println("size (after clear): " + list.size());
    }

    static void scenario5(){
        ArrayList<testingClass> list = new ArrayList<>();
        testingClass t1 = new testingClass(40, "LOL");
        testingClass t2 = new testingClass(20, "New");
        list.add(t1);

        System.out.println("testing name: " + list.get(0).name);

        System.out.println("name (previous): " + list.set(0, t2).name);

        System.out.println("name (after set): " + list.get(0).name);
    }
}

class testingClass{
    int number;
    String name;

    public testingClass(){}

    public testingClass(int number, String name){
        this.number = number;
        this.name = name;
    }

    public void display(){
        System.out.println(number + name);
    }
}
