import java.io.*;

/**
 * Created by isuca in java-indent-check catalogue
 *
 * Reads line to line from System.in/file given
 * {@code nextLine()} returns next line or {@code null} if reached end of file
 * {@code close()} closes the scanner
 *
 * @date 05-May-18
 * @time 16:03
 */

class FastScanner {

    private BufferedReader in;

    FastScanner(String name) {
        try {
            in = new BufferedReader(new FileReader(name));
        } catch (FileNotFoundException e) {
            in = new BufferedReader(new InputStreamReader(System.in));
            e.printStackTrace();
        }
    }

    /*
    Returns next line or null if end of file found
     */
    String nextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Closes the BufferedReader if it is opened
     */
    void close() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}