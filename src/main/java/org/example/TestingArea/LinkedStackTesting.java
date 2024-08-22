package org.example.TestingArea;

import Libraries.LinkedStack;

public class LinkedStackTesting {

    public static void main(String[] args) {
        scenario3();
    }

    /** test stack declaration, push, pop and peek function */
    static void scenario1(){
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    /** test stack 'class' declaration */
    static void scenario2(){
        LinkedStack<testingLinkedStackClass> stack = new LinkedStack<>();
        stack.push(new testingLinkedStackClass(12, "lol"));
        stack.push(new testingLinkedStackClass(13, "new"));
        stack.push(new testingLinkedStackClass(4, "man"));

        stack.pop().display();
        stack.pop().display();
        stack.pop().display();
    }

    static void scenario3(){
        LinkedStack<String> stack = new LinkedStack<>();

        for (int i=1; i<=17; i++){
            stack.push(i + "");
        }

        System.out.println("Peek: " + stack.peek() + "  Size: " + stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.peek());

    }
}

class testingLinkedStackClass {
    int number;
    String name;

    public testingLinkedStackClass(){}

    public testingLinkedStackClass(int number, String name){
        this.number = number;
        this.name = name;
    }

    public void display(){
        System.out.println(number + " " + name);
    }
}
