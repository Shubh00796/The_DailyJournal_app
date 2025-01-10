package com.sprinboot2025.demo.utility;

public class World extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            System.out.println("World");
        }
    }
}