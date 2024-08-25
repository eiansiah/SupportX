package org.example;

import FileHandling.UniversalFileHandler;
import Libraries.ArrayList;
import Libraries.Color;
import Libraries.Debug;
//import Main.Event;
//import Main.EventHandler;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        //TODO: ADT hashmap with multiple key
        //TODO: Multi algorithm to store data. example: <List> list of donee --> filter with condition --> <Queue> Delivery flow, near to far to near (circle)

        //Event event = EventHandler.addNewEvent(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "TARUMT", 0, 0, "");

        /*for (String temp: UniversalFileHandler.readData("event.txt")){
            System.out.println(temp);
        }*/

        /*ArrayList<String> temp = new ArrayList<>();
        temp.add("620, 2024-08-25T22:00:05.149610500, 2024-08-26T22:00:05.149610500, TARUMT, 0, 0, , UPCOMING");
        temp.add("500, 2024-08-25T22:46:32.191004800, 2024-08-26T22:46:32.191004800, TARUMT, 0, 0, , UPCOMING");

        UniversalFileHandler.removeData("event.txt", temp);*/
    }
}