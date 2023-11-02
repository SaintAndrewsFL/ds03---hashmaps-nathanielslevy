package com.company;

public class Main {

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        for (int i=0; i<30;i++) {
            map.putIfAbsent((Math.random()), "hello: " + i);
        }

        map.display();

    }
}
