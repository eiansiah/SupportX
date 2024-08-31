//<<<<<<< Updated upstream
///*
//package org.example.TestingArea.ADT;
//
//import Libraries.LinkedList;
//
//public class LinkedListTesting {
//
//    public static void main(String[] args) {
//        scenario10();
//    }
//
//    */
///** test contains 'string' function and size function*//*
//
//    static void scenario1(){
//        LinkedList<String> strings = new LinkedList<>();
//
//        strings.add("Hello");
//        strings.add("ME");
//
//        //strings.display();
//
//        System.out.println("LinkedList size: " + strings.size());
//        System.out.println(strings.contains("M"));
//    }
//
//    */
///** test contains 'class' function and indexOf function *//*
//
//    static void scenario2(){
//        LinkedList<testingLinkedListClass> list = new LinkedList<>();
//
//        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
//        testingLinkedListClass t2 = new testingLinkedListClass(40, "LOL");
//        testingLinkedListClass t3 = new testingLinkedListClass(20, "New");
//
//        list.add(t1);
//        list.add(t3);
//
//        System.out.println("Testing contains t1: " + list.contains(t1) + " index pos: " + list.indexOf(t1));
//        System.out.println("Testing contains t3: " + list.contains(t3) + " index pos: " + list.indexOf(t3));
//        System.out.println("Testing contains t2: " + list.contains(t2) + " index pos: " + list.indexOf(t2));
//    }
//
//    */
///** test get function*//*
//
//    static void scenario3(){
//        LinkedList<String> strings = new LinkedList<>();
//
//        strings.add("Hello");
//        strings.add("ME");
//
//        //strings.display();
//
//        System.out.println(strings.get(0));
//        System.out.println(strings.get(1));
//
//        //error
//        //System.out.println(strings.get(2));
//        //System.out.println(strings.get(-1));
//    }
//
//    */
///** test remove and clear function*//*
//
//    static void scenario4(){
//        LinkedList<testingLinkedListClass> list = new LinkedList<>();
//
//        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
//        testingLinkedListClass t2 = new testingLinkedListClass(40, "LOL");
//        testingLinkedListClass t3 = new testingLinkedListClass(20, "New");
//
//        list.add(t1);
//        list.add(t2);
//        list.add(t3);
//
//        System.out.println("Testing contains t1: " + list.contains(t1));
//        list.remove(0);
//        System.out.println("Testing contains t2: " + list.contains(t2));
//        list.remove(t2);
//        System.out.println("Testing contains t2 (after removed): " + list.contains(t2));
//
//        System.out.println("now: " + list.contains(t1));
//
//        System.out.println("Size: " + list.size());
//        list.add(null);
//        System.out.println("now (after add null): " + list.size());
//        list.remove(null);
//        System.out.println("now (after remove null): " + list.size());
//
//        list.clear();
//        System.out.println("size (after clear): " + list.size());
//    }
//
//    static void scenario5(){
//        LinkedList<testingLinkedListClass> list = new LinkedList<>();
//        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
//        testingLinkedListClass t2 = new testingLinkedListClass(20, "New");
//        list.add(t1);
//
//        System.out.println("testing name: " + list.get(0).name);
//
//        System.out.println("name (previous): " + list.set(0, t2).name);
//
//        System.out.println("name (after set): " + list.get(0).name);
//    }
//
//    */
///** testing foreach loop*//*
//
//    static void scenario6(){
//        LinkedList<String> list = new LinkedList<>();
//
//        list.add("Hello");
//        list.add("ME");
//
//        for(String s : list){
//            System.out.println(s);
//        }
//    }
//
//    */
///** testing lastIndexOf function *//*
//
//    static void scenario7(){
//        LinkedList<testingLinkedListClass> list = new LinkedList<>();
//
//        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
//        testingLinkedListClass t2 = new testingLinkedListClass(12, "new");
//        testingLinkedListClass t3 = new testingLinkedListClass(17, "JK");
//
//        list.add(t1);
//        list.add(t2);
//        list.add(t2);
//        list.add(t1);
//
//        System.out.println("Size: " + list.size());
//
//        System.out.println("first index of t1 pos: " + list.indexOf(t1));
//        System.out.println("first index of t2 pos: " + list.indexOf(t2));
//        System.out.println("last index of t1 pos: " + list.lastIndexOf(t1));
//        System.out.println("last index of t2 pos: " + list.lastIndexOf(t2));
//
//        System.out.println("last index of t3 pos: " + list.lastIndexOf(t3));
//    }
//
//    */
///** testing add(int index, T element)*//*
//
//    static void scenario8(){
//        LinkedList<String> list = new LinkedList<>();
//
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//
//        list.display();
//
//        System.out.println();
//        list.add(3, "4");
//
//        list.display();
//    }
//
//    */
///** testing add(int index, T element)*//*
//
//    static void scenario9(){
//        LinkedList<String> list = new LinkedList<>();
//
//
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//
//        list.display();
//
//        System.out.println();
//        list.addFirst("1");
//
//        list.display();
//    }
//
//    */
///** testing addFirst then addLast *//*
//
//    static void scenario10(){
//        LinkedList<String> list = new LinkedList<>();
//
//
//        list.addFirst("2");
//        list.add("3");
//
//        list.display();
//    }
//}
//
//class testingLinkedListClass{
//    int number;
//    String name;
//
//    public testingLinkedListClass(){}
//
//    public testingLinkedListClass(int number, String name){
//        this.number = number;
//        this.name = name;
//    }
//
//    public void display(){
//        System.out.println(number + " " + name);
//    }
//}
//*/
//=======
////package org.example.TestingArea.ADT;
////
////import Libraries.LinkedList;
////
////public class LinkedListTesting {
////
////    public static void main(String[] args) {
////        scenario10();
////    }
////
////    /** test contains 'string' function and size function*/
////    static void scenario1(){
////        LinkedList<String> strings = new LinkedList<>();
////
////        strings.add("Hello");
////        strings.add("ME");
////
////        //strings.display();
////
////        System.out.println("LinkedList size: " + strings.size());
////        System.out.println(strings.contains("M"));
////    }
////
////    /** test contains 'class' function and indexOf function */
////    static void scenario2(){
////        LinkedList<testingLinkedListClass> list = new LinkedList<>();
////
////        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
////        testingLinkedListClass t2 = new testingLinkedListClass(40, "LOL");
////        testingLinkedListClass t3 = new testingLinkedListClass(20, "New");
////
////        list.add(t1);
////        list.add(t3);
////
////        System.out.println("Testing contains t1: " + list.contains(t1) + " index pos: " + list.indexOf(t1));
////        System.out.println("Testing contains t3: " + list.contains(t3) + " index pos: " + list.indexOf(t3));
////        System.out.println("Testing contains t2: " + list.contains(t2) + " index pos: " + list.indexOf(t2));
////    }
////
////    /** test get function*/
////    static void scenario3(){
////        LinkedList<String> strings = new LinkedList<>();
////
////        strings.add("Hello");
////        strings.add("ME");
////
////        //strings.display();
////
////        System.out.println(strings.get(0));
////        System.out.println(strings.get(1));
////
////        //error
////        //System.out.println(strings.get(2));
////        //System.out.println(strings.get(-1));
////    }
////
////    /** test remove and clear function*/
////    static void scenario4(){
////        LinkedList<testingLinkedListClass> list = new LinkedList<>();
////
////        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
////        testingLinkedListClass t2 = new testingLinkedListClass(40, "LOL");
////        testingLinkedListClass t3 = new testingLinkedListClass(20, "New");
////
////        list.add(t1);
////        list.add(t2);
////        list.add(t3);
////
////        System.out.println("Testing contains t1: " + list.contains(t1));
////        list.remove(0);
////        System.out.println("Testing contains t2: " + list.contains(t2));
////        list.remove(t2);
////        System.out.println("Testing contains t2 (after removed): " + list.contains(t2));
////
////        System.out.println("now: " + list.contains(t1));
////
////        System.out.println("Size: " + list.size());
////        list.add(null);
////        System.out.println("now (after add null): " + list.size());
////        list.remove(null);
////        System.out.println("now (after remove null): " + list.size());
////
////        list.clear();
////        System.out.println("size (after clear): " + list.size());
////    }
////
////    static void scenario5(){
////        LinkedList<testingLinkedListClass> list = new LinkedList<>();
////        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
////        testingLinkedListClass t2 = new testingLinkedListClass(20, "New");
////        list.add(t1);
////
////        System.out.println("testing name: " + list.get(0).name);
////
////        System.out.println("name (previous): " + list.set(0, t2).name);
////
////        System.out.println("name (after set): " + list.get(0).name);
////    }
////
////    /** testing foreach loop*/
////    static void scenario6(){
////        LinkedList<String> list = new LinkedList<>();
////
////        list.add("Hello");
////        list.add("ME");
////
////        for(String s : list){
////            System.out.println(s);
////        }
////    }
////
////    /** testing lastIndexOf function */
////    static void scenario7(){
////        LinkedList<testingLinkedListClass> list = new LinkedList<>();
////
////        testingLinkedListClass t1 = new testingLinkedListClass(40, "LOL");
////        testingLinkedListClass t2 = new testingLinkedListClass(12, "new");
////        testingLinkedListClass t3 = new testingLinkedListClass(17, "JK");
////
////        list.add(t1);
////        list.add(t2);
////        list.add(t2);
////        list.add(t1);
////
////        System.out.println("Size: " + list.size());
////
////        System.out.println("first index of t1 pos: " + list.indexOf(t1));
////        System.out.println("first index of t2 pos: " + list.indexOf(t2));
////        System.out.println("last index of t1 pos: " + list.lastIndexOf(t1));
////        System.out.println("last index of t2 pos: " + list.lastIndexOf(t2));
////
////        System.out.println("last index of t3 pos: " + list.lastIndexOf(t3));
////    }
////
////    /** testing add(int index, T element)*/
////    static void scenario8(){
////        LinkedList<String> list = new LinkedList<>();
////
////        list.add("1");
////        list.add("2");
////        list.add("3");
////        list.add("5");
////        list.add("6");
////        list.add("7");
////
////        list.display();
////
////        System.out.println();
////        list.add(3, "4");
////
////        list.display();
////    }
////
////    /** testing add(int index, T element)*/
////    static void scenario9(){
////        LinkedList<String> list = new LinkedList<>();
////
////
////        list.add("2");
////        list.add("3");
////        list.add("4");
////        list.add("5");
////        list.add("6");
////        list.add("7");
////
////        list.display();
////
////        System.out.println();
////        list.addFirst("1");
////
////        list.display();
////    }
////
////    /** testing addFirst then addLast */
////    static void scenario10(){
////        LinkedList<String> list = new LinkedList<>();
////
////
////        list.addFirst("2");
////        list.add("3");
////
////        list.display();
////    }
////}
////
////class testingLinkedListClass{
////    int number;
////    String name;
////
////    public testingLinkedListClass(){}
////
////    public testingLinkedListClass(int number, String name){
////        this.number = number;
////        this.name = name;
////    }
////
////    public void display(){
////        System.out.println(number + " " + name);
////    }
////}
//>>>>>>> Stashed changes
