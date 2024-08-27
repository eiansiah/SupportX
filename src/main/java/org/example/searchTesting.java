package org.example;

import Main.Event.Event;
import Main.Event.EventHandler;
import Utilities.Search;

import java.time.LocalDateTime;

public class searchTesting {
    public static void main(String[] args) {
        test();
    }

    public static void addDummy(){
        for (int i = 0; i < 30; i++) {
            Event event = EventHandler.addNewEvent(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "TARUMT", "");
        }
    }

    public static void test(){
        System.out.println(Search.searchFirstMatchesStringFromFile("event.txt", "10"));
        System.out.println();

        for (String data: Search.searchAllMatchesString("event.txt", "2024-08-28T00:03:07.955495600", "!,!", 2)){
            System.out.println(data);
        }
        System.out.println();


    }
}
