package org.ibaqu;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner consoleScanner;

    public UserInputHandler() {
        consoleScanner = new Scanner(System.in);
    }

    public String getUserInput() {
        System.out.println("    -----     Options     ----- ");
        System.out.println("[D] - Draw from Stock");
        System.out.print("[W][T(i)] - Waste to Tableau (index)");
        System.out.print("\t[T(i)][T(i)] - Tableau (index) to Tableau (index)");
        System.out.print("\t[W][F] - Waste to Foundation");
        System.out.print("\t[T(i)][F] - Tableau (index) to Foundation");
        System.out.print("\t[F][T(i)] - Foundation to Tableau (index)");

        System.out.print("\nEnter choice: ");
        return consoleScanner.nextLine();
    }
}
