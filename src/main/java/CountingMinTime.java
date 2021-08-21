import java.io.*;
import java.util.ArrayList;

public class CountingMinTime {
    private int resultingTime;
    private String inputFileName;
    //The fist line from the file
    private String fistLine = "";
    //The others lines from the file
    private ArrayList<String> lines;
    //An array with data from a file
    private String[][][] labyrinth;

    public CountingMinTime(String inputFileName){
        this.inputFileName = inputFileName;

        readDataFromFile();
        fillingArrayWithData();
        countingTime();
        System.out.println(getResultingTime());
    }

    protected CountingMinTime(){}
//These setters and getters are used in unittests
    protected void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    protected void setFistLine(String fistLine) {
        this.fistLine = fistLine;
    }

    protected void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    protected void setLabyrinth(String[][][] labyrinth) {
        this.labyrinth = labyrinth;
    }

    protected int getResultingTime() {
        return resultingTime;
    }

    protected String getInputFileName() {
        return inputFileName;
    }

    public String getFistLine() {
        return fistLine;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    protected String[][][] getLabyrinth() {
        return labyrinth;
    }

    //Reading data from the file
    protected void readDataFromFile(){
        lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            while (reader.ready()) {
                //Reading the first line
                if(fistLine.isEmpty())
                    fistLine = reader.readLine();
                //Reading the other lines and add them to list
                String str = reader.readLine();
                if (!str.isEmpty())
                    lines.add(str);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Such file hasn't been found");
        }
    }

    //Filling an array with data from the file
    protected void fillingArrayWithData() {
        //An array with the values of H, M and N
        String[] hMN;
        int h, m, n;
        //Getting the values of H, M and N
        hMN = fistLine.split(" ");
        h = conversionStringToInt(hMN[0]);
        m = conversionStringToInt(hMN[1]);
        n = conversionStringToInt(hMN[2]);
        //Filling the array
        labyrinth = new String[h][m][n];
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                for (int k = 0; k < labyrinth[i][j].length; k++) {
                    //The ordinal number of the line from the list
                    int index = i*labyrinth[i].length+j;
                    labyrinth[i][j][k] = lines.get(index).split("")[k];
                }
            }
        }
    }
//Conventing String to int
    protected int conversionStringToInt(String str){
        int number = 0;
        try {
            if(Integer.valueOf(str) instanceof Integer)
                number = Integer.parseInt(str);
        }catch (NumberFormatException ne){
            System.out.println("It's not a digit");
        }
        return number;
    }
//Counting the time
    protected void countingTime(){
        //A flag for starting or finishing of counting time
        boolean start = false;
        String step;
        for(int i = 0; i < labyrinth.length; i++){
            for (int j = 0; j < labyrinth[i].length; j++){
                for (int k = 0; k < labyrinth[i][j].length; k++) {
                    step = labyrinth[i][j][k];
                    if(step.equals("1"))
                        start = true;
                    if(start && step.equals("."))
                        resultingTime = resultingTime + 5;
                    if(step.equals("2"))
                        start = false;
                }
            }
        }
    }
}
