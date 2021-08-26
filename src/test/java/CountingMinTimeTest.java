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
    private String number;
    private String[] lines;
    private int [] numbers;
    private String [][][] expectedArray;
    private CheckData checkData;

    private CountingMinTimeTest(){
        super();

        someFile = "someFile";
        number = "5 5 5";
        firstLine = "4 4 4";
        lines = new String[]{"4 4 4",
                             "1...", "ooo.", ".oo.", "....",
                             "oooo", "oo.o", "...o", "oooo",
                             "oooo", "oo..", "ooo.", "....",
                             "...2", ".ooo", ".ooo", ".ooo"};
        expectedArray = new String[][][]{{{"1", ".", ".", "."}, {"o", "o", "o", "."}, {".", "o", "o", "."}, {".", ".", ".", "."}},
                                         {{"o", "o", "o", "o"}, {"o", "o", ".", "o"}, {".", ".", ".", "o"}, {"o", "o", "o", "o"}},
                                         {{"o", "o", "o", "o"}, {"o", "o", ".", "."}, {"o", "o", "o", "."}, {".", ".", ".", "."}},
                                         {{".", ".", ".", "2"}, {".", "o", "o", "o"}, {".", "o", "o", "o"}, {".", "o", "o", "o"}}};
        numbers = new int[]{4,4,4};
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
            for (int i = 1, j = i+1; i < lines.length; i++, j++) {
                if(j%numberOfBlocks == 0)
                    writer.write(lines[i]+"\n\n");
                else writer.write(lines[i]+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = this.readDataFromFile(someFile);

        Assertions.assertEquals(firstLine,list.get(0));
        Assertions.assertEquals(Arrays.asList(lines),list);
    }

    @Test
    void testFillingArrayWithData() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(lines));

        Assertions.assertArrayEquals(expectedArray,this.fillingArrayWithData(numbers,list));
    }

    @Test
    void testConversionStringToInt() {
        int [] expectedResult = {5, 5, 5};

        Assertions.assertArrayEquals(expectedResult,this.conversionStringToInt(number));
    }

    @Test
    void whetherThrowExceptionIfNotConversionStringToInt(){
        String notNumber = "5 a 6";

        Assertions.assertDoesNotThrow(()->this.conversionStringToInt(notNumber));
    }

    @Test
    void testCountingTime() {
        int expectedResult = 140;
        CountTime countTime = this.new CountTime();
        countTime.setLabyrinth(expectedArray);
        countTime.move();

        Assertions.assertEquals(expectedResult,countTime.getCountTime());
    }

    @Test
    void testCheckData(){
        checkData = this.new CheckData(expectedArray);

        Assertions.assertTrue(checkData.isCheckResult());
    }

    @Test
    void testCheckDataIfDataIsWrong(){
        String [][][] newArray = expectedArray;
        newArray[0][2][0] = "b";
        checkData = this.new CheckData(newArray);

        Assertions.assertFalse(checkData.isCheckResult());
    }
}