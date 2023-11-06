package com.company;

import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.ArrayList;

public class MyHashMap<N,T> {
    private LinkedList<Node>[] map;
    double loadFactor;
    int capacity;
    int numFilled = 0;

    public MyHashMap() {
        map = new LinkedList[16];
        loadFactor = 0.75;
        capacity = 16;
        for (int i = 0; i < map.length; i++) {
            LinkedList<Node> newLinked = new LinkedList<Node>();
            map[i] = newLinked;
        }
    }

    public MyHashMap(double loadFactor, int capacity) {
        map = new LinkedList[capacity];
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        for (int i = 0; i < map.length; i++) {
            LinkedList<Node> newLinked = new LinkedList<>();
            map[i] = newLinked;
        }
    }


    public void empty() {
        for (int i = 0; i < map.length; i++) {
            LinkedList<Node> newLinked = new LinkedList<>();
            map[i] = newLinked;
        }
    }

    public boolean isEmpty() {
        return map.length == 0;
    }

    public void put(N key, T value) { // replaces value if key is present in index
        boolean absent = true;
        int index = calculateIndex(key);
        Node node = new Node(key, value);
        for (Node x : map[index]) {
            if (x.getKey().equals(key)) {
                x.setValue(value);
                absent = false;
            }
        }
        if (absent) {
            map[index].add(node);
        }
        if (map[index].size() == 1) {
            numFilled++;
        }
        if (numFilled > loadFactor * capacity) {
            capacity*=2;
            LinkedList[] arr = new LinkedList[capacity];
            for (int i = 0; i < arr.length; i++) {
                LinkedList<Node> newLinked = new LinkedList<>();
                arr[i] = newLinked;
            }
            for (LinkedList<Node> nodes : map) {
                for (Node j : nodes) {
                    N k = j.getKey();
                    T v = j.getValue();
                    index = calculateIndex(k);
                    Node tempNode = new Node(k, v);
                    System.out.println(k);
                    arr[index].add(tempNode);
                }
            }
            map = arr;
        }
    }


    public T putIfAbsent(N key, T value) {
        int index = calculateIndex(key);
        for (Node j : map[index]) {
            if (j.getValue().equals(value)) {
                return null;
            }
        }
        put(key, value);
        return value;
    }


    public Node remove(Object key, Object value) { //Returns the item being removed
        for (LinkedList<Node> nodes : map) {
            for (Node j : nodes) {
                if (j.getKey().equals(key) && j.getValue().equals(value)) {
                    nodes.remove(j);
                    return j;
                }
            }
        }
        return null;
    }

    public boolean containsValue(Object value) { //Will need to search all buckets - think about why.
        for (LinkedList<Node> nodes : map) {
            for (Node j : nodes) {
                if (j.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsKey(Object key) { //Will search only one bucket.
        for (LinkedList<Node> nodes : map) {
            for (Node j : nodes) {
                if (j.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object get(Object key) { //Only searches one bucket.
        int index = calculateIndex(key);
        for (Node node : map[index]) {
            if (node.getKey().equals(key)) {
                return node.getKey();
            }
        }
        return null;
    }

    public Object getOrDefault(Object key, Object value) { //Searches for key and returns respective value if found, otherwise returns 2nd parameter
        int index = calculateIndex(key);
        for (Node node : map[index]) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return value;
    }

    public int size() { //Returns num of items in total HashMap.
        int size = 0;
        for (LinkedList<Node> nodes : map) {
            for (Node j : nodes) {
                size++;
                }
            }
        return size;
    }

    public Object replace(N key, T value) { //Overwrites existing key/value. Return is old object. If no object exists, should output a message indicating so and do nothing.
        for (LinkedList<Node> nodes : map) {
            for (Node j : nodes) {
            if (j.getKey().equals(key)) {
                j.setValue(value);
                }
            }
        }
        System.out.println("Key not present");
        return null;

    }

    public void display() { //Output entire HashMap. Should indicate which bucket each element belongs to and, if multiple per bucket, starts at head and goes to tail.
        int idx = 0;
        for (LinkedList<Node> nodes : map) {
            System.out.print("Index: " + idx + ": ");
            idx++;
            if (nodes.isEmpty()) {
                System.out.print("[]");
            }
            for (Node j : nodes) {
                if (! j.equals(nodes.getFirst())) {
                    System.out.print(", ");
                }
                System.out.print("[" + j.getKey() + ", " + j.getValue() + "]");
            }
            System.out.println();
        }

    }

    public int calculateIndex(Object key) {
        return key.hashCode() & capacity - 1;
    }


    private class Node {
        private Node next;
        private N key;
        private T value;

        public Node(N key, T value) {
            this.key = key;
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public N getKey() {
            return key;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}

