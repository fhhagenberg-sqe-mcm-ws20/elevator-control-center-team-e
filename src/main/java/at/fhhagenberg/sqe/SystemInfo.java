package at.fhhagenberg.sqe;

public class SystemInfo {

    public static String javaVersion() {
        return System.getProperty("java.version", "13");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version", "13");
    }

}