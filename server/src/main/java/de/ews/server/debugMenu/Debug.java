package de.ews.server.debugMenu;

import java.util.Scanner;

public class Debug implements Runnable {
    Scanner scanner = new Scanner(System.in);

    public Debug() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {

        while (true) {
            while (scanner.hasNext()) {

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
