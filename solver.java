import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class solver {
    
    public static void main(String[] args) throws Exception{
        
        File file = new File("wordle-answers-alphabetical.txt");
        
        ArrayList <String> mainWordList = createWordList(file);

        HashMap<String, Double> expectedInformationMap = calculateBestGuess(mainWordList);
        
        printTopFive(expectedInformationMap);

        Scanner keyboard = new Scanner(System.in);
        
        boolean ctinue = true;
        String guess;

        int count = 0;

        do{

            System.out.println("What was your guess?");
            String gword = keyboard.nextLine();

            System.out.println("What was your result?");
            guess = keyboard.nextLine();
            
            guess+=" ";

            ArrayList<Integer> information = new ArrayList<>();

            for(int i = 0; i<5; i++){

                information.add(Integer.parseInt(guess.substring(i,i+1)));

            }

            int size = mainWordList.size();

            for(int i = size-1; i>-1; i--){

                String cword = mainWordList.get(i);

                ArrayList<Integer> poss = compare(gword, cword);
                if(!poss.equals(information)){
                    mainWordList.remove(cword);
                }

            }

            System.out.println(information);

            expectedInformationMap = calculateBestGuess(mainWordList);

            printTopFive(expectedInformationMap);

            count++;


        }while(!guess.equals("22222 "));

        System.out.println("Count was: "+count);



    }

    public static ArrayList <String> createWordList(File file) throws Exception{

        Scanner sc = new Scanner(file);
        
        ArrayList <String> mainWordList = new ArrayList<>();

        while(sc.hasNextLine()){ 
            mainWordList.add(sc.nextLine());
        }

        return mainWordList;

    }

    public static HashMap <String, Double> calculateBestGuess(ArrayList <String> mainWordList){

        HashMap<String, Double> expectedInformationMap = new HashMap<>();
        ArrayList <String> testWordList = mainWordList;
        

        for(int i = 0; i<testWordList.size(); i++){

            String tword = testWordList.get(i);

            HashMap<List<Integer>, Integer> possibilities = new HashMap<>();

            for(int j = 0; j < mainWordList.size(); j++){

                String cword = mainWordList.get(j);

                List<Integer> poss = compare(tword, cword);

                if(possibilities.containsKey(poss)){
                    int ccount = possibilities.get(poss);
                    ccount++;
                    possibilities.put(poss, ccount);
                }
                else{
                    possibilities.put(poss, 1);
                }

            }
            ArrayList<Double> probabilities = new ArrayList<>();
            ArrayList<Double> information = new ArrayList<>();
            Double expectedInformation = 0.0;

            for(List<Integer> key: possibilities.keySet()){
                Double prob = possibilities.get(key)/14855.0;

                Double oneOverP = 1 / prob;
                Double info = Math.log(1/oneOverP) / Math.log(2);

                probabilities.add(prob);
                information.add(info);
                expectedInformation+=prob*info;
            }

            expectedInformationMap.put(tword, expectedInformation*-1);


        }
        return expectedInformationMap;

    }

    public static void printTopFive(HashMap <String, Double>expectedInformationMap){
        System.out.println("Top 5 Guesses: Expected Information Gained");
        for(int k = 0; k<5; k++){
            double max = 0.0;
            String word = "";
    
            for(String key: expectedInformationMap.keySet()){
            
                if(expectedInformationMap.get(key)>max){
                    max = expectedInformationMap.get(key);
                    word = key;
                }
    
            }
            
            System.out.println(word + ": "+max);
            expectedInformationMap.remove(word);
            max = 0.0;

        }

    }

    public static ArrayList<Integer> compare(String tword, String cword){

        String[] tarr = tword.split("");
        String[] carr = cword.split("");
        
        ArrayList<Integer> poss = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        boolean[] used = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (tarr[i].equals(carr[i])) {
                poss.set(i, 2);
                used[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (poss.get(i) != 2) { 
                for (int j = 0; j < 5; j++) {
                    if (!used[j] && tarr[i].equals(carr[j])) {
                        poss.set(i, 1);
                        used[j] = true; 
                        break;
                    }
                }
            }
        }

        return poss; 
    }
}
