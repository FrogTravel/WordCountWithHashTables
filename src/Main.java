import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        HashMap<String, Integer> hashMap = new HashMap<>();
        String answer = "";



        Scanner scanner = null;

        try{
            String pattern = "[\\s.?,;']";
            scanner = new Scanner(new File("input.txt")).useDelimiter(pattern);
        }catch (FileNotFoundException e){

        }

        while (scanner.hasNext()){
            String word = scanner.next().toLowerCase();
            if(word.equals(""))
                continue;
            if(hashMap.get(word) == null)
                hashMap.put(word, 1);
            else{
                int value = hashMap.get(word);
                hashMap.remove(word);
                hashMap.put(word, value + 1);
            }
        }

        int max = 0;
        String maxWord = "";
        for(Object word : hashMap.getKeys()){
            int number = hashMap.get((String) word);
            if(number > max){
                max = number;
                maxWord = (String) word;
            }
        }
        answer += maxWord + " " + max + "\n";

        String[] stopWords = {"a", "the", "in", "at", "to", "on", "not", "for", "'s", "'d", "'re", "is", "are", "am", "has", "I", "we", "you"};

        for (String word:stopWords) {
            hashMap.remove(word);
        }

         max = 0;
         maxWord = "";
        for(Object word : hashMap.getKeys()){
            int number = hashMap.get((String) word);
            if(number > max){
                max = number;
                maxWord = (String) word;
            }
        }

        answer += maxWord + " " + max;
        writeFile(answer);


    }


    private static void writeFile(String words){
        PrintWriter printWriter = null;
        try{
            printWriter = new PrintWriter("output.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        printWriter.write(words);
        printWriter.close();
    }
}
