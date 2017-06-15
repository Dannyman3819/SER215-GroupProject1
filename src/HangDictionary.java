/**
 * Created by MarcoRosa on 6/9/17
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
     * Main used for testing - PLEASE DO NOT DELETE THE COMMENTED ITEMS! I WILL CLEAN THEM UP ONCE THE CLASS
     * HAS BEEN FULLY TESTED AND WE REACHED A FINAL VERSION
     */
//    public static void main(String[] args) throws IOException{
//        HangDictionary myDictionary = new HangDictionary();
//
////        System.out.println("The size of the dictionaryWords list = "+dictionaryWords.size());
//        System.out.println("SearchWordFromFile returns "+SearchWordFromFile());
//        System.out.println("SearchWordFromList returns "+SearchWordFromList());
//
//        System.out.println("getWordFromList() returns "+myDictionary.getWordFromList());
//        System.out.println("getWordFromFile() returns "+myDictionary.getWordFromFile());
//
//        System.out.println("Thank you for choosing the Hangman Dictionary service for your dictionary needs." +
//                "\nHope to serve you again soon :)");
//    }

    /**
     * Constructor for the HangDictionary class
     * It checks the file path and adjusts it according to the platform used
     * Looks and, if needed, creates the dictionary file
     * Makes the dictionary available as file and as a List<String>
     */
    public HangDictionary() throws IOException {
//        System.out.println("Calling HandleDirectories()");
        String pathToDictionary = HandleDirectories();
        hangmanWordsList = new File(pathToDictionary);
//        System.out.println("hangmanWordsList file name is "+hangmanWordsList.getName());
//        System.out.println("Calling CheckForFile()");
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
//        System.out.println("absolute path is "+referenceCodeFile.getAbsolutePath());
        newPath = path.substring(0, (path.length()-19)).concat("HangmanWordsList.txt");
////        newPath = path.substring(0, (path.length()-19)).concat("TestWordsList.txt");
//        System.out.println("absolute newPath is "+newPath);

        return newPath;
    }

    /**
     * Check for a file, if not found it creates it and copies the words from an original one
     * in the src project directory
     * @param file (cannot use the filed/non-static variable because this method is static)
     */
    private static void CheckForFile(File file) {
        String pathToReadFile, separator, path;
        path = file.getAbsolutePath();
        try {
            if (!file.exists()) {
                file.createNewFile();
                separator = path.substring((path.length()-21), (path.length()-20));
////                separator = path.substring((path.length()-18), (path.length()-17));
//                System.out.println("separator = "+separator);
                pathToReadFile = path.substring(0,(path.length()-20))+"src"+separator+file.getName();
////                pathToReadFile = path.substring(0,(path.length()-17))+"src"+separator+file.getName();
//                System.out.println("pathToReadFile = "+pathToReadFile);
                File read = new File(pathToReadFile);
//                System.out.println("read file name is "+read.getName());
//                System.out.println("Calling ReadWrite()");
                ReadWrite(read, file);
//                System.out.println("Opening read file");
//                OpenFileToScreen(read);
//                System.out.println("Opening written file");
//                OpenFileToScreen(file);
//                System.out.println("File "+file.getName()+" has been created.");
            }
//            else
//               System.out.println("File "+file.getName()+" already existed!");
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

//        if(!readFrom.exists())
//            System.out.println("Does not exist.");
//
//        System.out.println("writeTo.getAbsoluteFile() = "+writeTo.getAbsoluteFile());
        while ((lineWord = inputStream.readLine()) != null) {
//            System.out.print("Writing\t");
//            System.out.println("inputStream.readLine() = "+lineWord);
            outFile.println(lineWord);
        }

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

        while ((lineWord = inputStream.readLine()) != null) {
//            System.out.println("inputStream.readLine() = "+lineWord);
            totCounter++;
        }
//        System.out.println("tot number of words = "+totCounter);
        inputStream.close();
//        System.out.println("inputStream closed");

        BufferedReader searchPool = new BufferedReader(new FileReader(hangmanWordsList));
//        System.out.println("searchPool opened");

        chosenLine = findLine.nextInt(totCounter)+1;
//        System.out.println("the chosen line is "+chosenLine);
        do{
            chosenWord = searchPool.readLine();
//            System.out.println("searchPool.readLine("+(counter+1)+") = "+chosenWord);
            counter++;
//            System.out.println("counter = "+counter);
        }while(counter<chosenLine);

//        System.out.println("the chosen word is "+chosenWord);
        searchPool.close();
//        System.out.println("searchPool closed");

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

//        System.out.println("the size of the dictionary = "+dictionaryWords.size());
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
//    public static void OpenFileToScreen(File file){
    public void OpenFileToScreen(){
        //Check if Desktop functions are supported by Platform
        if(!Desktop.isDesktopSupported()){
            System.out.println("\tDesktop is not supported."
                    +"\t\nPlease open the "+hangmanWordsList.getName()+"file manually with the default text editor."
//                    + "\t\nPlease open the "+file.getName()+"file manually with the default text editor."
                    + "\t\nThe file is located in the project directory.");
        }
        else{
            try {
                Desktop.getDesktop().edit(hangmanWordsList);
//                Desktop.getDesktop().edit(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}