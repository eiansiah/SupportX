package Main;

import java.util.Comparator;
import Libraries.PriorityQueue;

public class TestDonee {

    public static void main(String[] args) {
        Comparator<Donee> doneeComparator = new Comparator<Donee>() {
            @Override
            public int compare(Donee d1, Donee d2) {
                int urgencyComparison = mapUrgencyToInt(d1.getDoneeUrgency()) - mapUrgencyToInt(d2.getDoneeUrgency());
                if (urgencyComparison != 0) {
                    return urgencyComparison;
                }
                return d1.getDoneeID().compareTo(d2.getDoneeID());
            }

            private int mapUrgencyToInt(String urgency) {
                switch (urgency.toLowerCase()) {
                    case "high": return 1;
                    case "medium": return 2;
                    case "low": return 3;
                    default: return Integer.MAX_VALUE;
                }
            }
        };

        PriorityQueue<Donee> pq = new PriorityQueue<>(doneeComparator);

        Donee donee1 = new Donee("1", "John Doe1Low", "john@example.com", "123456789",
                "123 Main St", "Type1", "Food", "Low");
        Donee donee2 = new Donee("2", "Jane Doe1Medium", "jane@example.com", "987654321",
                "456 Elm St", "Type2", "Clothing", "Medium");
        Donee donee3 = new Donee("3", "Jane Doe2Medium", "jane@example.com", "987654321",
                "456 Elm St", "Type2", "Clothing", "Medium");
        Donee donee4 = new Donee("4", "Jane Doe3Low", "jane@example.com", "987654321",
                "456 Elm St", "Type2", "Clothing", "Low");
        Donee donee5 = new Donee("5", "Jane Doe4High", "jane@example.com", "987654321",
                "456 Elm St", "Type2", "Clothing", "High");
        Donee donee6 = new Donee("6", "Jane Doe5Medium", "jane@example.com", "987654321",
                "456 Elm St", "Type2", "Clothing", "Medium");

        pq.enqueue(donee1);
        pq.enqueue(donee2);
        pq.enqueue(donee3);
        pq.enqueue(donee4);
        pq.enqueue(donee5);
        pq.enqueue(donee6);

        System.out.println(pq.peek().getName() + " " + pq.dequeue().getDoneeUrgency());
        System.out.println(pq.peek().getName() + " " + pq.dequeue().getDoneeUrgency());
        System.out.println(pq.peek().getName() + " " + pq.dequeue().getDoneeUrgency());
        System.out.println(pq.peek().getName() + " " + pq.dequeue().getDoneeUrgency());
        System.out.println(pq.peek().getName() + " " + pq.dequeue().getDoneeUrgency());
        System.out.println(pq.peek().getName() + " " + pq.dequeue().getDoneeUrgency());
    }
}
