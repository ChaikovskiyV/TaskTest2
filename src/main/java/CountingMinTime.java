import java.io.*;
import java.util.ArrayList;

public class CountingMinTime {
    private int resultingTime;
    private String inputFileName;
    private BufferedReader reader;
    private String[][][] labyrinth;
    private String[] sizeOfArray;
    private int h,m,n;
    private boolean start;

    public CountingMinTime(String inputFileName){
        this.inputFileName = inputFileName;
        start = false;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            if(reader.ready()) {
                sizeOfArray = reader.readLine().split(" ");
                h = conversionStringToInt(sizeOfArray[0]);
                m = conversionStringToInt(sizeOfArray[1]);
                n = conversionStringToInt(sizeOfArray[2]);
            }

        } catch(IOException e){
            System.out.println("Such file is not found.");
        }

        fillingArrayWithData();
        countingTime();
        System.out.println(resultingTime);
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public int getResultingTime() {
        return resultingTime;
    }

    protected void fillingArrayWithData() {
        labyrinth = new String[h][m][n];
        ArrayList<String> lines = new ArrayList<>();
        try {
            while (reader.ready()) {
                String str = reader.readLine();
                if (!str.isEmpty())
                    lines.add(str);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                for (int k = 0; k < labyrinth[i][j].length; k++) {
                    int index = i*labyrinth[i].length+j;
                    labyrinth[i][j][k] = lines.get(index).split("")[k];
                }
            }
        }
    }

    protected Integer conversionStringToInt(String str){
        int number = 0;
        try {
            if(Integer.valueOf(str) instanceof Integer)
                number = Integer.parseInt(str);
            else throw new IllegalArgumentException();
        }catch (IllegalArgumentException ie){
            System.out.println("It's not a digit");
        }
        return number;
    }

    protected void countingTime(){
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
