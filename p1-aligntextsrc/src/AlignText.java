import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An class to achieve the test aligning.
 * @author Mengying Chen (mc386@st-andrews.ac.uk)
 * @version 1.0
 * @since 28 September 2020
 */
public class AlignText {

    private static final int B_LIMIT_MINUS = 4;
    private static final int THREE_ARGUS_LENGTH = 3;
    private static final int TWO_ARGUS_LENGTH = 2;

    /**
     * Accept two arguments from the command line.
     * @param str String
     * @return boolean
     */
    public static boolean isNum(String str) {
        char c = str.charAt(0);
        return Character.isDigit(c);
    }

    /**
     * Accept two arguments from the command line.
     * @param param1 The first String
     * @param param2 The second String
     * @param param3 The third String
     * @return List
     */
    public static List align(String param1, String param2, String param3) {
        String[]paragraphs = FileUtil.readFile(param1);
        List<String> alignText = new ArrayList<>(); //Create an empty list
        String text = "";
        int lengthParagraphs = paragraphs.length;
        int lengthOfLine = Integer.parseInt(param2);

        if ("B".equals(param3)) {
            lengthOfLine = lengthOfLine - B_LIMIT_MINUS;
        }

        for (int i = 0; i < lengthParagraphs; i++) { //Print paragraph one by one
            String[] words = paragraphs[i].split(" "); //Split the words by space
            for (String word : words) { //For each the word from words
                int lengthText = text.length(); //Initialize lengthTest to the desired length of the line
                int length = word.length(); //Initialize length to the length of the word

                if (length >= lengthOfLine) { //When the length of a word over the desired length of the line
                    if (lengthText == 0) {
                        text = word;
                        alignText.add(text);
                        text = "";
                    } else {
                        alignText.add(text); //Add the text to the alignText list

                        text = word;

                        alignText.add(text); //Add the text to the alignText list
                        text = "";

                    }
                } else {
                    if (lengthText + length + 1 < lengthOfLine) {
                        if (lengthText == 0) {
                            text = word;

                        } else {
                            text = text + " " + word;
                        }
                    } else if (lengthText + length + 1 == lengthOfLine) {
                        if (lengthText == 0) {
                            text = word;

                        } else { //如果此时文本长度不为0
                            text = text + " " + word;

                        }
                        alignText.add(text);
                        text = "";

                    } else {
                        alignText.add(text);
                        text = word;
                    }
                }
            }
            if (!text.equals("")) {
                alignText.add(text);

            } text = "";
        } return alignText;
    }

    /**
     * Accept two arguments from the command line.
     * @param args three string
     */
    public static void main(String[] args) {

        String mode = "";

        if (args.length < TWO_ARGUS_LENGTH) { //If do not accept two parameters from command line
            System.out.println("usage: java AlignText file_name line_length"); //exception
        }
        else if (args.length == TWO_ARGUS_LENGTH) { //If only accept two parameters from command line
            mode = "L";
            if (args[0] == null || (args[0].equals(""))) {
                System.out.println("usage: java AlignText file_name line_length"); //exception
            }
            else {
                if (!isNum(args[1])) {
                    System.out.println("usage: java AlignText file_name line_length"); //exception
                }
                else {
                    List<String> alignLeft = align(args[0], args[1], mode);
                    for (String newLine : alignLeft) {
                        System.out.println(newLine);
                    }
                }
            }
        }
        else if (args.length == THREE_ARGUS_LENGTH) { //If accept three parameters from command line
            mode = args[2];

            if (args[0] == null || (args[0].equals(""))) {
                System.out.println("usage: java AlignText file_name line_length"); //exception
            }
            else {
                if (!isNum(args[1])) {
                    System.out.println("usage: java AlignText file_name line_length"); //exception
                }
                else {
                    if ("L".equals(mode)) {
                        List<String> alignLeft = align(args[0], args[1], args[2]);
                        for (String newLine : alignLeft) {
                            System.out.println(newLine);
                        }
                    }
                    else if ("R".equals(mode)) {
                        List<String> alignRight = align(args[0], args[1], args[2]);
                        for (String newLine : alignRight) {
                            String newLineRight = "";
                            String temp = "";

                            int lengthOfLine = Integer.parseInt(args[1]);

                            if (newLine.length() >= lengthOfLine) {
                                System.out.println(newLine);
                            }
                            else {
                                for (int i = 0; i < lengthOfLine - newLine.length(); i++) {
                                    temp = temp + " ";
                                }
                                newLineRight = temp + newLine;
                                System.out.println(newLineRight);
                            }
                        }
                    }
                    else if ("C".equals(mode)) {
                        List<String> alignCenter = align(args[0], args[1], args[2]);
                        for (String newLine : alignCenter) {
                            String newLineRight = "";
                            String temp = "";

                            int lengthOfLine = Integer.parseInt(args[1]);

                            if (newLine.length() >= lengthOfLine) {
                                System.out.println(newLine);
                            } else {

                                int l;

                                if ((lengthOfLine - newLine.length()) % 2 == 0) {
                                    l = (lengthOfLine - newLine.length()) / 2;
                                } else {
                                    l = (lengthOfLine - newLine.length() + 1) / 2;
                                }

                                for (int i = 0; i < l; i++) {
                                    temp = temp + " ";
                                }
                                newLineRight = temp + newLine;
                                System.out.println(newLineRight);
                            }
                        }
                    }
                    else if ("B".equals(mode)) {
                        List<String> alignBubble = align(args[0], args[1], args[2]);
                        String temp = " ";
                        String newLineBubble = "";
                        String temp2 = " ";
                        int max = 0;

                        for (String newLine : alignBubble) {

                            if (newLine.length() > max) {
                                max = newLine.length();
                            }
                        }

                        for (int i = 0; i < max + 2; i++) {
                            temp = temp + "_";
                        }
                        System.out.println(temp);

                        for (String newLine : alignBubble) {
                            String temp1 = " ";
                            for (int i = 0; i < max - newLine.length(); i++) {
                                temp1 = temp1 + " ";
                            }
                            newLineBubble = newLine + temp1;
                            System.out.println("|" + " " + newLineBubble + "|");

                        }

                        for (int i = 0; i < max + 2; i++) {
                            temp2 = temp2 + "-";
                        }
                        System.out.println(temp2);
                        System.out.println("        " + "\\");
                        System.out.println("         " + "\\");
                    }
                    else {
                        System.out.println("usage: java AlignText file_name line_length [align_mode]");
                    }
                }
            }
        }
    }
}
