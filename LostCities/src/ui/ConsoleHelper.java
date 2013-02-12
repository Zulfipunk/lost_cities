package ui;

import java.util.Scanner;

public class ConsoleHelper {

    private final static ConsoleType type = ConsoleType.ECLIPSE;

    public static String prompt(String string) {
        if (type == ConsoleType.SYSTEM) {
            return System.console()
                .readLine(string);
        } else if (type == ConsoleType.ECLIPSE) {
            Scanner scan = new Scanner(System.in);
            System.out.println(string);
            return scan.nextLine();
        } else {
            return null;
        }
    }

    private enum ConsoleType {
        SYSTEM,
        ECLIPSE
    }
}
