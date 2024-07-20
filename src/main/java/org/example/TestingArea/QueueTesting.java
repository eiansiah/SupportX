package org.example.TestingArea;
import Libraries.Queue;

public class QueueTesting {

    public static void main(String[] args) {
        scenario4();
    }

    /** test queue declaration, queue and dequeue function */
    static void scenario1(){
        Queue<String> queue = new Queue<>();

        queue.enqueue("12");
        queue.enqueue("14");

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

    /** test declaration and enqueue different types */
    static void scenario2(){
        Queue<testingStackClass> queue = new Queue<>();

        //Error
        /*queue.enqueue("12");
        queue.enqueue("14");*/

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

    /** test arraylist function */
    static void scenario3(){
        Queue<testingStackClass> queue = new Queue<>();

        queue.enqueue(new testingStackClass(12, "Lol"));
        queue.enqueue(new testingStackClass(14, "new"));

        queue.dequeue().display();
        queue.dequeue().display();
        System.out.println(queue.dequeue());
    }

    /** test peek function */
    static void scenario4(){
        Queue<String> queue = new Queue<>();

        queue.enqueue("12");
        queue.enqueue("14");

        System.out.println(queue.peek());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.peek());
    }
}

class testingQueueClass{
    int number;
    String name;

    public testingQueueClass(){}

    public testingQueueClass(int number, String name){
        this.number = number;
        this.name = name;
    }

    public void display(){
        System.out.println(number + " " + name);
    }
}
