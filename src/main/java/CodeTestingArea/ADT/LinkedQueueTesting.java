package CodeTestingArea.ADT;
import ADT.LinkedQueue;

public class LinkedQueueTesting {

    public static void main(String[] args) {
        scenario4();
    }

    /** test queue declaration, queue and dequeue function */
    static void scenario1(){
        LinkedQueue<String> queue = new LinkedQueue<>();

        queue.enqueue("12");
        queue.enqueue("14");
        queue.enqueue("15");
        queue.enqueue("16");
        queue.enqueue("17");

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

    /** test declaration and enqueue different types */
    static void scenario2(){
        LinkedQueue<testingStackClass> queue = new LinkedQueue<>();

        //Error
        /*queue.enqueue("12");
        queue.enqueue("14");*/

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

    /** test arraylist function */
    static void scenario3(){
        LinkedQueue<testingLinkedQueueClass> queue = new LinkedQueue<>();

        queue.enqueue(new testingLinkedQueueClass(12, "Lol"));
        queue.enqueue(new testingLinkedQueueClass(14, "new"));

        queue.dequeue().display();
        queue.dequeue().display();
        System.out.println(queue.dequeue());
    }

    /** test peek function */
    static void scenario4(){
        LinkedQueue<String> queue = new LinkedQueue<>();

        queue.enqueue("12");
        queue.enqueue("14");

        System.out.println(queue.peek());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.peek());
    }
}

class testingLinkedQueueClass{
    int number;
    String name;

    public testingLinkedQueueClass(){}

    public testingLinkedQueueClass(int number, String name){
        this.number = number;
        this.name = name;
    }

    public void display(){
        System.out.println(number + " " + name);
    }
}
