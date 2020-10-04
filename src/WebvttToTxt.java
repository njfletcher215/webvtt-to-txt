/**
 * Nathan Fletcher, 2020
 *
 * For use with WebvttToTextConverter
 *
 * When one or more file or directory paths with .vtt files are passed into args,
 * converts all .vtt files into .txt files, keeping the originals.
 */

import java.io.File;
import java.io.IOException;

public class WebvttToTxt {

        public static void main(String[] args) throws IOException {
            WebvttToTxtConverter converter = new WebvttToTxtConverter();

            if (args.length == 0) {     // makes sure args isn't empty
                System.out.println("Error: pass in 1 or more .vtt files, or a directory containing .vtt files");
                System.exit(1);
            }

            for (String path : args) {  // iterates through each given pathname

                if (path.endsWith(".vtt")) {        // if the path is a .vtt, adds the file to WebvttToTxtConverter's
                    File inFile = new File(path);   // filesToConvert
                    if (inFile.isFile()) { converter.prepFile(inFile); }   // check that the path is valid, and file exists
                    else { System.out.println("Error: not a valid file path"); }    // prints an error if not
                }
                else {  // if the path is not a .vtt, assumes it is a directory and adds all files within to
                    File inDir = new File(path);    // WebvttToTxtConverter's filesToConvert
                    if (inDir.isDirectory()) { converter.prepAll(inDir); }   // check path is valid, and directory exists
                    else { System.out.println("Error: not a valid directory path"); }   // prints an error if not
                }
                    // note: WebvttToTxtConverter's .prepAll(String) method only adds .vtt files to its filesToConvert
            }

            converter.convert();    // converts all filesToAdd to Strings, with all but dialogue removed
            converter.makeFiles();  // makes output files from those Strings, with original names (but now .txt)

        }

}