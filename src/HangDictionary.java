/**
 * Created by MarcoRosa
 * Hangman dictionary class.
 */

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HangDictionary {
    static List<String> dictionaryWords = new ArrayList<String>();
    static File hangmanWordsList;

    /**
     * Constructor for the HangDictionary class
     * It checks the file path and adjusts it according to the platform used
     * Looks and, if needed, creates the dictionary file
     * Makes the dictionary available as file and as a List<String>
     */
    public HangDictionary() throws IOException {
        String pathToDictionary = HandleDirectories();
        hangmanWordsList = new File(pathToDictionary);
        CheckForFile(hangmanWordsList);
        setDictionaryList();
    }

    /**
     * Eliminates the problem of lunching the game on different platforms by looking for the directory's path
     * and adapting the directory for the dictionary as needed
     */
    private static String HandleDirectories() {
        File referenceCodeFile = new File("HangDictionary.java");
        String path, newPath;

        path = referenceCodeFile.getAbsolutePath();
        newPath = path.substring(0, (path.length()-19)).concat("HangmanWordsList.txt");
        return newPath;
    }

    /**
     * Check for a file, if not found it creates it and copies the words from an original one
     * in the src project directory
     * @param file
     */
    private static void CheckForFile(File file) {
        String pathToReadFile, separator, path;
        path = file.getAbsolutePath();
        try {
            if (!file.exists()) {
                file.createNewFile();
                separator = path.substring((path.length()-21), (path.length()-20));
                pathToReadFile = path.substring(0,(path.length()-20))+"src"+separator+file.getName();
                File read = new File(pathToReadFile);
                ReadWrite(read, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads words from a file and writes it into another one - used in case the dictionary file has been misplaced
     * @param readFrom (file reading from)
     */
    private static void ReadWrite(File readFrom, File writeTo) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(readFrom));
        BufferedWriter outputStream = new BufferedWriter(new FileWriter(writeTo));
        PrintWriter outFile = new PrintWriter (outputStream);
        String lineWord;

        while ((lineWord = inputStream.readLine()) != null)
            outFile.println(lineWord);

        outFile.flush();
        outFile.close();
        inputStream.close();
        hangmanWordsList = writeTo;
    }

    /**
     * Searches for a new word to return to the Hangman game from a dictionary file
     */
    private static String SearchWordFromFile() throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(hangmanWordsList));
        Random findLine = new Random();
        int totCounter = 0, counter = 0, chosenLine;
        String lineWord, chosenWord;

        while ((lineWord = inputStream.readLine()) != null)
            totCounter++;
        
        inputStream.close();
        BufferedReader searchPool = new BufferedReader(new FileReader(hangmanWordsList));
        chosenLine = findLine.nextInt(totCounter)+1;
        
        do{
            chosenWord = searchPool.readLine();
            counter++;
        }while(counter<chosenLine);
        
        searchPool.close();
        return chosenWord;
    }

    /**
     * Searches for a new word to return to the Hangman game from a dictionary list of strings
     */
    private static String SearchWordFromList() {
        Random findLine = new Random();
        int chosenLine;
        String chosenWord;

        if(!dictionaryWords.isEmpty()) {
            chosenLine = findLine.nextInt(dictionaryWords.size()) + 1;
            chosenWord = dictionaryWords.get(chosenLine);
            return chosenWord;
        }
        else
            return "Sorry, dictionary list is empty. Try using the file";
    }

    /**
     * Sets the List<String> field with all the words of the dictionary giving the option of using this or
     * the dictionary file as search pool
     */
    private static void setDictionaryList() throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(hangmanWordsList.getName()));
        String line;

        while ((line = inputStream.readLine()) != null)
            dictionaryWords.add(line);

        inputStream.close();
    }

    /**
     * Returns a word to use in the Hangman game (uses a list of strings)
     */
    public String getWordFromList() { return SearchWordFromList(); }

    /**
     * Returns a word to use in the Hangman game (uses a file)
     */
    public String getWordFromFile() throws IOException { return SearchWordFromFile(); }

    /**
     * Returns the dictionary in a List<String> format
     */
    public List<String> getDictionary() throws IOException { return dictionaryWords; }

    /**
     * Opens the dictionary file in automatic for the user, or instructs the user how to open it
     */
    
    public void OpenFileToScreen(){
        //Check if Desktop functions are supported by Platform
        if(!Desktop.isDesktopSupported()){
            System.out.println("\tDesktop is not supported."
                    +"\t\nPlease open the "+hangmanWordsList.getName()+"file manually with the default text editor."
                    + "\t\nThe file is located in the project directory.");
        }
        else{
            try {
                Desktop.getDesktop().edit(hangmanWordsList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}