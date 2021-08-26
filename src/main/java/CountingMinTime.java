import java.io.*;
import java.util.ArrayList;

public class CountingMinTime {
    private int resultingTime;
    private String inputFileName;
    //The fist line from the file
    private String firstLine = "";
    //The others lines from the file
    private ArrayList<String> lines;
    //An array with data from a file
    private String[][][] labyrinth;

    public CountingMinTime(String inputFileName){
        this.inputFileName = inputFileName;

        lines = readDataFromFile(inputFileName);
        CountTime countTime = new CountTime(fillingArrayWithData(conversionStringToInt(lines.get(0)),lines));
    }

    protected CountingMinTime(){}
//These setters and getters are used in unittests
    protected void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
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

    public ArrayList<String> getLines() {
        return lines;
    }

    protected String[][][] getLabyrinth() {
        return labyrinth;
    }

    //Reading data from the file
    protected ArrayList<String> readDataFromFile(String inputFileName){
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            while (reader.ready()) {
                //Reading lines and add them to list
                String str = reader.readLine();
                if (!str.isEmpty())
                    lines.add(str);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Such file hasn't been found");
        }
        return lines;
    }

    //Filling an array with data from the file
    protected String [][][] fillingArrayWithData(int [] numbers, ArrayList<String> lines) {
        String [][][] labyrinth;
        //Getting the values of H, M and N
        int h, m, n;
        h = numbers[0];
        m = numbers[1];
        n = numbers[2];
        //Filling the array
        labyrinth = new String[h][m][n];
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                for (int k = 0; k < labyrinth[i][j].length; k++) {
                    //The ordinal number of the line from the list
                    int index = i*labyrinth[i].length+j;
                        labyrinth[i][j][k] = lines.get(index+1).split("")[k];
                }
            }
        }
        return labyrinth;
    }
//Conventing String to int
    protected int[] conversionStringToInt(String str){
        String[] strings = str.split(" ");
        int[] numbers = new int[strings.length];
        for (int i = 0; i < numbers.length; i++) {
            try {
                if(Integer.valueOf(strings[i]) instanceof Integer);
                numbers[i] = Integer.parseInt(strings[i]);
            }catch (NumberFormatException ne){
                System.out.println("It's not a digit");
            }
        }
        return numbers;
    }

    class CountTime{
        private String[][][] labyrinth;
        private int i, j, k;
        private int countTime;

        public CountTime(String[][][] labyrinth){
            this.labyrinth = labyrinth;

            move();
            System.out.println(countTime*5);
        }

        public CountTime(){}

        public void setLabyrinth(String[][][] labyrinth) {
            this.labyrinth = labyrinth;
        }

        public int getCountTime() {
            return countTime*5;
        }

        void move(){
            int l;
            String a, b, c, d, e, f;
            boolean back = false;
            boolean up = false;
            boolean down = false;
            boolean forward = false;
            boolean nextLevel = false;

            for (i = 0, l = i+1;i < labyrinth.length && l < labyrinth.length; i++, l++) {
                for (j = 0; j < labyrinth[i].length; j++) {
                    for (k = 0; k < labyrinth[i][j].length; k++) {
                        //Current cage
                        a = labyrinth[i][j][k];
                        //Next cage
                        if(k < labyrinth[i][j].length-1)
                            b = labyrinth[i][j][k+1];
                        else b = "";
                        //Lower cage
                        if(j < labyrinth[i].length-1)
                            c = labyrinth [i][j+1][k];
                        else c = "";
                        //The same cage on next level
                        if(i != l)
                            d = labyrinth [l][j][k];
                        else d = "";
                        //Previous cage
                        if(k > 0)
                            e = labyrinth [i][j][k-1];
                        else e = "";
                        //Upper cage
                        if(j > 0)
                            f = labyrinth [i][j-1][k];
                        else f = "";
                        //Go to free zone
                        if(a.equals(".")){
                            countTime++;
                        }
                        //The goal is found
                        if(a.equals("2")){
                            countTime++;
                            return;
                        }
                        //Conditions of moving direction choice
                        //To next level
                        if(d.equals(".")) {
                            nextLevel = true;
                            if(forward)
                                forward = false;
                            if (back)
                                back = false;
                            if(up)
                                up = false;
                            if(down)
                                down = false;
                        }
                        else {
                            nextLevel = false;
                            //Down
                            if((c.equals(".") || c.equals("2")) && !up) {
                                down = true;
                                if(forward)
                                    forward = false;
                                if(back)
                                    back = false;
                            }
                            else {
                                //Forward
                                if((b.equals(".") || b.equals("2")) && !back) {
                                    forward = true;
                                    if(down)
                                        down = false;
                                    if(up)
                                        up = false;
                                }
                                else {
                                    //Back
                                    if((e.equals(".") || e.equals("2")) && !forward) {
                                        back = true;
                                        if(down)
                                            down = false;
                                        if(up)
                                            up = false;
                                    }
                                    else {
                                        //Up
                                        if(f.equals(".") || f.equals("2") && !down) {
                                            up = true;
                                            if(forward)
                                                forward = false;
                                            if(back)
                                                back = false;
                                        }
                                    }
                                }
                            }
                        }
                        //Go to next level
                        if(nextLevel){
                            i++;
                            if(l < labyrinth.length-1)
                                l++;
                            k--;
                            continue;
                        }
                        //Go down
                        if(down){
                            j++;
                            k--;
                            continue;
                        }
                        //Go back
                        if (back){
                            if(k >= 1)
                                k -=2;
                            continue;
                        }
                        //Go up
                        if(up){
                            j--;
                            k--;
                            continue;
                        }
                    }
                }
            }
        }
    }
}



