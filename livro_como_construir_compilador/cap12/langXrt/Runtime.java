package langXrt;

import java.io.*;


public class Runtime {
    static BufferedReader in;

    static public int initialize() {
        System.out.println("Language X runtime system. Version 0.1");
        in = new BufferedReader(new InputStreamReader(System.in));

        if (in == null) {
            System.err.println("Error initializing X language runtime system");

            return -1;
        }

        return 0;
    }

    static public void finilizy() {
        try {
            in.close();
        } catch (IOException e) {
        }
    }

    static public int readInt() {
        String s = null;
        int k;

        try {
            s = in.readLine();
            k = Integer.parseInt(s);
        } catch (IOException e) {
            System.err.println("Error reading from standard input");
            System.err.println("Reason: " + e.getMessage());

            return 0;
        } catch (NumberFormatException f) {
            return 0;
        }

        return k;
    }

    static public String readString() {
        String s = null;

        try {
            s = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from standard input");
            System.err.println("Reason: " + e.getMessage());
        }

        return s;
    }
}
