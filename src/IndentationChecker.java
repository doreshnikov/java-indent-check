import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by isuca in java-indent-check catalogue
 *
 * @date 05-May-18
 * @time 16:02
 */

public class IndentationChecker {

    public static void main(String[] args) throws InvalidObjectException {
        new IndentationChecker().check(new FastScanner(args[0]));
    }

    /**
     * Simple greatest common divisor counting function
     * Used to find the largest number of indentation symbols in one indentation,
     * so that all lines' indents are valid
     */
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * Checks all the lines in given file and saves the count of indentation symbols used in each line
     * Also checks if there are any block or dangling comments because comments can have any
     * indentation that have no relation to code indents
     *
     * @param in FastScanner of given Java document
     * @throws InvalidObjectException if there are at least two types of indentation symbol used
     */
    private void check(FastScanner in) throws InvalidObjectException {
        String line;
        boolean comment = false;
        ArrayList<Integer> indent = new ArrayList<>();
        Character indentSymbol = null;

        while ((line = in.nextLine()) != null) {
            // read the document to the end
            if (!comment && line.length() > 0) {
                // if the line is a part of the code
                if (Character.isWhitespace(line.charAt(0))) {
                    // checking if it starts not from the first symbol
                    if (indentSymbol == null) {
                        indentSymbol = line.charAt(0);
                    } else if (indentSymbol != line.charAt(0)) {
                        // more than one indentation types ???
                        throw new InvalidObjectException("Found two types of indentation:\n" +
                                "\t- " + (int) indentSymbol +
                                "\t- " + (int) line.charAt(0));
                    }
                    int cnt = 0;
                    while (line.charAt(cnt) == line.charAt(0)) {
                        cnt++;
                    }
                    indent.add(cnt);
                }
            }
            if (line.contains("/*") || line.contains("*/")) {
                // if the line contains a start or an end of multiline comment, we check it,
                // also if any code is in this line too but after a comment, we ignore it,
                // because it's strange to talk about indents in this case

                boolean string = !comment && line.charAt(0) == '"'; // if it's inside code string, not comment
                boolean[] commentOn = new boolean[line.length()]; // if i-th symbol on the line inside a comment
                commentOn[0] = comment;
                for (int i = 1; i < line.length(); i++) {
                    if (line.charAt(i) == '"' && !comment) {
                        string = !string;
                        if (string) {
                            continue;
                        }
                    }
                    if (line.charAt(i - 1) == '*' && line.charAt(i) == '/') {
                        if (i == 1 || !(line.charAt(i - 2) == '/' && !commentOn[i - 2])) {
                            // if there is an end of comment which started at least two symbols before
                            // so if there is /*/ inside a comment, it is the end, and beginning otherwise
                            comment = false;
                        }
                        // also it is possible that there is \/*/ line,
                        // but there is no way to find \ outside of a comment
                    } else if (line.charAt(i) == '/' && line.charAt(i + 1) == '*') {
                        comment = true;
                    }
                    commentOn[i] = comment;
                }
            }
        }

        Collections.sort(indent);
        int smallestIndent = indent.size() == 0 ? 0 : indent.get(0);
        // so we will not have problems if file is not indented at all
        for (int t : indent) {
            // for example, if there is 2-symbol and 3-symbol indents, it is "obvious" (maybe)
            // that actual indent is 1 symbol, but it was used several times on these lines
            if (t % smallestIndent != 0) {
                smallestIndent = gcd(smallestIndent, t);
            }
        }

        System.out.println(Character.getName(indentSymbol == null ? ' ' : indentSymbol) + "\n" + smallestIndent);
    }

}
