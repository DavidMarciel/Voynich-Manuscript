package voynich.manuscript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DavidMarciel
 */
public class VoynichManuscript {
    
    public static void main(String[] args) {

        ArrayList<String> words = loadFile();        
        Scanner sc = getData("submitInput.txt");
        ArrayList<String> stringResponses = new ArrayList<>();
        
        int nCases = sc.nextInt();
            
        for (int i = 0; i < nCases; i++) {

            int firstWord = sc.nextInt();
            int lastWord = sc.nextInt();
                        
            String response = solveCase(words, firstWord, lastWord);
            
            stringResponses.add("Case #"+(i+1)+": "+ response+"\n");            
        }
        
        toDisk(stringResponses);
    }

    private static ArrayList<String> loadFile() {

        ArrayList<String> words = new ArrayList<>();
        Scanner sc;

        try {
            sc = new Scanner(new FileReader("corpus.txt"));
            sc.useDelimiter(" ");
            String s;
            
            while (sc.hasNext()) {
                s = sc.next();
                words.add(s);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(VoynichManuscript.class.getName()).log(Level.SEVERE, null, ex);
        }

        return words;
    }

    private static Scanner getData(String textInputtxt) {
        
        Scanner sc = null;

        try {
            sc = new Scanner(new FileReader(textInputtxt));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VoynichManuscript.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return sc;
    }

    private static String solveCase(ArrayList<String> words, int firstWord, int lastWord) {
    
        
        class Word {
            private String value;
            private int times;

            Word(String v){
                value = v;
                times = 1;
            }

            @Override
            public String toString() {
                return value+" "+times;
            }

            private void addTimes() {
                times++;   
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }
            
        }
        
        HashMap<String, Word> wordsInPiece = new HashMap<String, Word>();
        
        for (int i = firstWord-1; i <= lastWord; i++) {
            
            String s = words.get(i);
            
            if(wordsInPiece.containsKey(s)){
                
                //increases the value
                wordsInPiece.get(s).addTimes();
            }
            else{
                //adds the item
                wordsInPiece.put(s, new Word(s));
            }
        }
        
        ArrayList<Word> sortedWords = new ArrayList<>();
        
        for (Map.Entry<String, Word> entry : wordsInPiece.entrySet()) {
            Word value = entry.getValue();
            
            sortedWords.add(value);
        }
        
        Collections.sort(sortedWords, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
            
                return o2.getTimes()-o1.getTimes();
            }
        });
        
        Word first = sortedWords.get(0);
        Word second = sortedWords.get(1);
        Word third = sortedWords.get(2);
        
        return first+","+second+","+third;
        
    }

    private static void toDisk(ArrayList<String> stringResponses) {
    
        try {
            FileWriter fw = new FileWriter("Respuestas.txt");
            
            for (int i = 0; i < stringResponses.size(); i++) {
                fw.write(stringResponses.get(i));
            }
            
            fw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(VoynichManuscript.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}