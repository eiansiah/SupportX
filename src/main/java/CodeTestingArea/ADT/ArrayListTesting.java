package CodeTestingArea.ADT;

import ADT.ArrayList;

public class ArrayListTesting {

    public static void main(String[] args) {
        scenario3();
    }

    /** test contains 'string' function and size function*/
    static void scenario1(){
        ArrayList<String> strings = new ArrayList<>();

        strings.add("Hello");
        strings.add("ME");

        //strings.display();

        System.out.println("ArrayList size: " + strings.size());
        System.out.println(strings.contains("M"));
    }

    /** test contains 'class' function and indexOf function */
    static void scenario2(){
        ArrayList<testingArrayListClass> list = new ArrayList<>();

        testingArrayListClass t1 = new testingArrayListClass(40, "LOL");
        testingArrayListClass t2 = new testingArrayListClass(40, "LOL");
        testingArrayListClass t3 = new testingArrayListClass(20, "New");

        list.add(t1);
        list.add(t3);

        System.out.println("Testing contains t1: " + list.contains(t1) + " index pos: " + list.indexOf(t1));
        System.out.println("Testing contains t3: " + list.contains(t3) + " index pos: " + list.indexOf(t3));
        System.out.println("Testing contains t2: " + list.contains(t2) + " index pos: " + list.indexOf(t2));
    }

    /** test get function*/
    static void scenario3(){
        ArrayList<String> strings = new ArrayList<>();

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
        ArrayList<testingArrayListClass> list = new ArrayList<>();

        testingArrayListClass t1 = new testingArrayListClass(40, "LOL");
        testingArrayListClass t2 = new testingArrayListClass(40, "LOL");
        testingArrayListClass t3 = new testingArrayListClass(20, "New");

        list.add(t1);
        list.add(t2);
        list.add(t3);

        System.out.println("Testing contains t1: " + list.contains(t1));
        list.remove(0);
        System.out.println("Testing contains t2: " + list.contains(t2));
        list.remove(t2);
        System.out.println("Testing contains t2 (after removed): " + list.contains(t2));

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
        ArrayList<testingArrayListClass> list = new ArrayList<>();
        testingArrayListClass t1 = new testingArrayListClass(40, "LOL");
        testingArrayListClass t2 = new testingArrayListClass(20, "New");
        list.add(t1);

        System.out.println("testing name: " + list.get(0).name);

        System.out.println("name (previous): " + list.set(0, t2).name);

        System.out.println("name (after set): " + list.get(0).name);
    }

    /** testing foreach loop*/
    static void scenario6(){
        ArrayList<String> list = new ArrayList<>();

        list.add("Hello");
        list.add("ME");

        for(String s : list){
            System.out.println(s);
        }
    }

    /** testing lastIndexOf function */
    static void scenario7(){
        ArrayList<testingArrayListClass> list = new ArrayList<>();

        testingArrayListClass t1 = new testingArrayListClass(40, "LOL");
        testingArrayListClass t2 = new testingArrayListClass(12, "new");
        testingArrayListClass t3 = new testingArrayListClass(17, "JK");

        list.add(t1);
        list.add(t2);
        list.add(t2);
        list.add(t1);

        System.out.println("Size: " + list.size());

        System.out.println("first index of t1 pos: " + list.indexOf(t1));
        System.out.println("first index of t2 pos: " + list.indexOf(t2));
        System.out.println("last index of t1 pos: " + list.lastIndexOf(t1));
        System.out.println("last index of t2 pos: " + list.lastIndexOf(t2));

        System.out.println("last index of t3 pos: " + list.lastIndexOf(t3));
    }

    /** testing add(int index, T element)*/
    static void scenario8(){
        ArrayList<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }

        System.out.println();
        list.add(3, "4");

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }
}

class testingArrayListClass{
    int number;
    String name;

    public testingArrayListClass(){}

    public testingArrayListClass(int number, String name){
        this.number = number;
        this.name = name;
    }

    public void display(){
        System.out.println(number + " " + name);
    }
}
