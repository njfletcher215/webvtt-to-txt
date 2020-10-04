import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Nathan Fletcher, 2020
 *
 * For use with WebvttToTextUser
 *
 * converts one or more .vtt files to Strings, then Strings to .txt files
 * saving the .txt files in the same place as its .vtt counterpart
 */

public class WebvttToTxtConverter {

    private ArrayList<File> filesToConvert = new ArrayList<>();
    private ArrayList<String> convertedFiles = new ArrayList<>();


    /*
     * converts all Files in filesToConvert to Strings using convert(File), then stores those Strings in convertedFiles.
     * removes all Files from filesToConvert.
     */
    public void convert() throws FileNotFoundException {
        int filesConverted = 0;
        int filesConvertedLength = 0;

        if (filesToConvert.size() > 0) {    // make sure there are files to convert
            System.out.print("Files Converted:" + " ".repeat(8) + filesConverted);  // counter tracks how many files
                                                                                    // have been converted to String
            for (File file : filesToConvert) {  // iterate through each file in filesToConvert

                convertedFiles.add(this.convert(file));

                filesConverted++;
                filesConvertedLength = ("" + filesConverted).length();
                System.out.print("\b".repeat(filesConvertedLength) + filesConverted);   // update counter
            }
            System.out.println();
            filesToConvert = new ArrayList<>();
        }
        else { System.out.println("Error: no files given"); }
    }

    /*
    *  converts given File inFile to a String, with the first line being the file path and all timestamps etc. removed
    *
    *  @param File inFile, File to be converted
    *
    *  @return String, converted File, with file path as first line and only dialogue
     */
    public String convert(File inFile) throws FileNotFoundException {
        final String NUMS = "1234567890";

        String output = inFile.getAbsolutePath();   // String output starts with inFile's name, used when converting
        String line = "";                           // from String to .txt

        boolean add = true; // if we are clear to add the next character in the line
        boolean beforeFirst = true; // if we are in the header of the doc (before line 1)

        Scanner scanner = new Scanner(inFile);

        // get rid of everything before the first line of dialogue (file header, etc.)
        while (beforeFirst && scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.length() > 0 && line.charAt(0) == '1') { beforeFirst = false; }
        }

        // parse through the rest of the file, removing anything in tage (between < and >),
        // and all timestamps/line numbers (any line beginning with a number)
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.length() > 0 && !NUMS.contains("" + line.charAt(0))) { // if the first character of the line is
                                                                            // a number, ignore the whole line
                                                                            // (removes line nums and timestamps,
                                                                            // and some metadata)
                for (int i = 0; i < line.length(); i++) {   // parse through each other line
                    if (line.charAt(i) == '>') {    // when we reach a '>', start adding to the output again
                        add = true;                 // this goes before the '<' check so noting gets added at <i></i>
                        if (i != line.length() - 1) { i++; }    // if i isnt the end of line, move on to the next char
                        else { break; }                         // before adding to output, else move on to next line
                    }
                    if (line.charAt(i) == '<') {    // when we reach a '<', stop adding to the output
                        add = false;
                    }
                    if (add) {  // if we are outside a tag, add to output
                        output = output + line.charAt(i);
                    }
                }
            }
            output = output + "\n";
        }
        return output;
    }

    /*
    *  converts all Strings in convertedFiles into .txt files, with the same name as the originals (but now .txt).
    *  removes all Strings from convertedFiles.
    *
    *  @throws IOException
     */
    public void makeFiles() throws IOException {
        int filesCreated = 0;
        int filesCreatedLength = 0;
        System.out.println("Files Created:" + " ".repeat(10) + filesCreated);
        for (String string : convertedFiles) {  // iterates through each String in convertedFiles
            Scanner scanner = new Scanner(string);

            String fileName = scanner.nextLine();   // get the name of the file from the first line of the String
            fileName = fileName.substring(0, fileName.length() - 4) + ".txt";   // change the name from .vtt to .txt
            File outFile = new File(fileName);
            System.out.println(fileName);
            outFile.createNewFile();    // create a new file with fileName

            string = string.substring(fileName.length());   // remove the file name from the beginning of the String

            FileWriter outWriter = new FileWriter(outFile);
            outWriter.write(string);    // print the rest of the String onto the file


            filesCreated++;
            filesCreatedLength = ("" + filesCreated).length();
            System.out.print("\b".repeat(filesCreatedLength) + filesCreated);
        }
        System.out.println();
        convertedFiles = new ArrayList<>(); // remove all Strings from convertedFiles
    }

    /*
     *  calls this.prepFile(File) on all Files in File inDir
     *
     *  @param File inDir, directory whose files are passed into this.prepFile(File)
     */
    public void prepAll(File inDir) {
        for (File inFile : inDir.listFiles()) {
            if (inFile.isFile()) { this.prepFile(inFile); }
            else if (inFile.isDirectory()) { this.prepAll(inFile); } // also preps all files in all subdirectories
        }
    }

    /*
     *  adds a File to filesToConvert, fails if file is not .vtt
     *
     *  @param File inFile, file to convert
     */
    public void prepFile(File inFile) {
        if (inFile.getAbsolutePath().endsWith(".vtt")) {
            filesToConvert.add(inFile);
        }
    }
}