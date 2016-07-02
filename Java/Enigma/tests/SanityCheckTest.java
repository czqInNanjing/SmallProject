package tests;

import enigma.EnigmaException;
import enigma.Main;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.UnsupportedOperationException;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
public class SanityCheckTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void trivialTest() {
        String[] args = {"tests/correct/trivial/trivial.inp", "output.txt",
                         "tests/correct/trivial/trivial.out"};
        deleteFile(args[1]);
        Main.main(args);
        checkIsFile(args[0]);
        checkIsFile(args[1]);
        checkFilesEqual(args[1], args[2], args[0]);
        deleteFile(args[1]);

        args = new String[]{"tests/correct/trivial/trivial_encrypted.inp", "output.txt",
                            "tests/correct/trivial/trivial_encrypted.out"};
        Main.main(args);
        checkIsFile(args[0]);
        checkIsFile(args[1]);
        checkFilesEqual(args[1], args[2], args[0]);
        deleteFile(args[1]);
    }

    @Test
    public void largeTest() {
        String[] args = {"tests/correct/large/large.inp", "output.txt",
                         "tests/correct/large/large.out"};
        deleteFile(args[1]);
        Main.main(args);
        checkIsFile(args[0]);
        checkIsFile(args[1]);
        checkFilesEqual(args[1], args[2], args[0]);
        deleteFile(args[1]);
 
        args = new String[]{"tests/correct/large/large_encrypted.inp", "output.txt",
                            "tests/correct/large/large_encrypted.out"};
        Main.main(args);
        checkIsFile(args[0]);
        checkIsFile(args[1]);
        checkFilesEqual(args[1], args[2], args[0]);
        deleteFile(args[1]);
    }

    @Test
    public void errorTest() {
        String[] args = new String[]{"tests/error/trivialerr.inp", "output.txt"};
        expectedEx.expect(EnigmaException.class);
        Main.main(args);
        deleteFile(args[1]);
    }

    public void checkIsFile(String filename) {
        File f = new File(filename);
        assertTrue(f.exists());
        assertTrue(f.isFile());
    }

    public void deleteFile(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            try {
                f.delete();
            } catch (SecurityException e) {
                System.out.println("Encountered exception while deleting: " + e);
            }
        }
    }

    public static void checkFilesEqual(String outputFile, String expectedFile, String inputFile) {
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        BufferedReader br3 = null;
        try {
            br1 = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile)));
            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(expectedFile)));
            br3 = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        } catch (FileNotFoundException e) {
            assertTrue("FileNotFoundException: one of input files does not exist.", false);
        }
        StringBuilder outputBuffer = new StringBuilder();
        StringBuilder expectedBuffer = new StringBuilder();
        StringBuilder inputBuffer = new StringBuilder();

        try {
            String line;
            while ((line = br1.readLine()) != null) {
                outputBuffer.append(line + "\n");
            }

            while ((line = br2.readLine()) != null) {
                expectedBuffer.append(line + "\n");
            }

            while ((line = br3.readLine()) != null) {
                inputBuffer.append(line + "\n");
            }

            br1.close();
            br2.close();
            br3.close();
            assertEquals("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n" +
                         "Expected: " + expectedBuffer + "\nYour output: " + outputBuffer +
                         "\nTo help you debug, here is the input file:\n" + inputBuffer.toString() +
                         "\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n",
                         expectedBuffer.toString(), outputBuffer.toString());
        } catch (IOException e) {
            assertTrue("Exception while reading files.", false);
        }
    }
}
