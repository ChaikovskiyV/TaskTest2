import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CountingMinTimeTest extends CountingMinTime {
    private String someFile;
    private String firstLine;
    private String[] lines;
    private String [][][] expectedArray;

    private CountingMinTimeTest(){
        super();

        someFile = "someFile";
        firstLine = "3 3 2";
        lines = new String[]{"1.", "..", "oo",
                             "o.", "34", "45",
                             ".o", "oo", ".2"};
        expectedArray = new String[][][]{{{"1", "."}, {".", "."}, {"o", "o"}},
                                         {{"o", "."}, {"3", "4"}, {"4", "5"}},
                                         {{".", "o"}, {"o", "o"}, {".", "2"}}};

    }

    @BeforeEach
    void setUp() {
        System.out.println("Test is starting ...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("The test has finished.");
    }

    @Test
    void testSetInputFileName() {
        this.setInputFileName(someFile);

        Assertions.assertEquals(someFile,this.getInputFileName());
    }

    @Test
    void testReadDataFromFile(){
        int numberOfBlocks = Integer.parseInt(firstLine.substring(0,1));
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(someFile)));
            writer.write(firstLine+"\n\n");
            for (int i = 0, j = i+1; i < lines.length; i++, j++) {
                if(j%numberOfBlocks == 0)
                    writer.write(lines[i]+"\n\n");
                else writer.write(lines[i]+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setInputFileName(someFile);
        this.readDataFromFile();

        Assertions.assertEquals(firstLine,this.getFistLine());
        Assertions.assertEquals(Arrays.asList(lines),this.getLines());
    }

    @Test
    void testFillingArrayWithData() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(lines));
        this.setFistLine(firstLine);
        this.setLines(list);
        this.fillingArrayWithData();

        Assertions.assertTrue(Arrays.deepEquals(expectedArray,this.getLabyrinth()));
    }

    @Test
    void testConversionStringToInt() {
        String number = "555";
        int expectedResult = 555;

        Assertions.assertEquals(expectedResult,this.conversionStringToInt(number));
    }

    @Test
    void whetherThrowExceptionIfNotConversionStringToInt(){
        String notNumber = "a";
        String message = "It's not a digit";

        Assertions.assertDoesNotThrow(()->this.conversionStringToInt(notNumber));
    }

    @Test
    void testCountingTime() {
        int expectedResult = 30;
        this.setLabyrinth(expectedArray);
        this.countingTime();

        Assertions.assertEquals(expectedResult,this.getResultingTime());
    }
}