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
    File hangmanWordsList;

    /**
     * Main used for testing - PLEASE DO NOT DELETE THE COMMENTED ITEMS! I WILL CLEAN THEM UP ONCE THE CLASS
     * HAS BEEN FULLY TESTED AND WE REACHED A FINAL VERSION
     */
//    public static void main(String[] args) {
//
////        System.out.println("Calling HandleDirectories()");
//        String pathToDictionary = HandleDirectories();
//        File hangmanWordsList = new File(pathToDictionary);
////        System.out.println("hangmanWordsList file name is "+hangmanWordsList.getName());
////        System.out.println("Calling CheckForFile()");
//        CheckForFile(hangmanWordsList);
////        try {
////            System.out.println("The chosen word is "+searchWord(hangmanWordsList));
////            System.out.println("The size of the dictionaryWords list = "+setDictionary(hangmanWordsList).size());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }

    /**
     * Constractor for the HangDictionary class
     * It checks the file path and adjusts it according to the platform used
     * Looks and, if needed, creates the dictionary file
     * Makes the dictionary available as file and as a List<String>
     */
    public HangDictionary() throws IOException {
        String pathToDictionary = HandleDirectories();
        hangmanWordsList = new File(pathToDictionary);
        CheckForFile(hangmanWordsList);
        setDictionaryList(hangmanWordsList);
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
//        newPath = path.substring(0, (path.length()-19)).concat("TestWordsListss.txt");
        System.out.println("absolute newPath is "+newPath);

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
                separator = path.substring((path.length()-20), (path.length()-19));
//                System.out.println("separator = "+separator);
                pathToReadFile = path.substring(0,(path.length()-19))+"src"+separator+file.getName();
//                System.out.println("pathToReadFile = "+pathToReadFile);
                File read = new File(pathToReadFile);
//                System.out.println("read file name is "+read.getName());
//                System.out.println("Calling ReadWrite()");
                ReadWrite(read,file);
//                System.out.println("Opening read file");
//                OpenFileToScreen(read);
//                System.out.println("Opening written file");
//                OpenFileToScreen(file);
//                System.out.println("File "+file.getName()+" has been created.");
            }
//            else
//                System.out.println("File "+file.getName()+" already existed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads words from a file and writes it into another one - used in case the dictionary file has been misplaced
     * @param readFrom (file reading from)
     * @param writeTo (file writing into)
     */
    private static void ReadWrite(File readFrom, File writeTo) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(readFrom));
        BufferedWriter outputStream = new BufferedWriter(new FileWriter(writeTo));
        PrintWriter outFile = new PrintWriter (outputStream);
        String lineWord;

//        if(!readFrom.exists())
//            System.out.println("Does not exist.");
//        System.out.println("writeTo.getAbsoluteFile() = "+writeTo.getAbsoluteFile());
        while ((lineWord = inputStream.readLine()) != null) //{
//            System.out.print("Writing\t");
//            System.out.println("inputStream.readLine() = "+lineWord);
            outFile.println(lineWord);
//        }

        outFile.flush();
        outFile.close();
        inputStream.close();
    }

    /**
     * Opens the file in automatic for the user, or instructs the user to open it
     * @param file
     */
    public void OpenFileToScreen(File file){

        //Check if Desktop functions are supported by Platform
        if(!Desktop.isDesktopSupported()){
            System.out.println("\tDesktop is not supported."
                    + "\t\nPlease open the "+file.getName()+"file manually with the default text editor."
                    + "\t\nThe file is located in the project directory.");
        }
        else{
            try {
                Desktop.getDesktop().edit(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Searches for a new word to return to the Hangman game
     * @param file (cannot use the filed/non-static variable because this method is static)
     */
    public static String searchWord(File file) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(file));
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

        BufferedReader searchPool = new BufferedReader(new FileReader(file));
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
     * Sets the List<String> field with all the words of the dictionary giving the option of using this or
     * the dictionary file as search pool
     * @param file (cannot use the filed/non-static variable because this method is static)
     */
    private static void setDictionaryList(File file) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(file.getName()));
        String line;

        while ((line = inputStream.readLine()) != null)
            dictionaryWords.add(line);

//        System.out.println("the size of the dictionary = "+dictionaryWords.size());
        inputStream.close();
    }

    /**
     * Returns a word to use in the Hangman game
     */
    public String getWord(File file) throws IOException {
        System.out.println("Thank you for choosing the Hangman Dictionary service for your dictionary needs." +
                "\nHope to serve you again soon :) .");
        return searchWord(hangmanWordsList);
    }

    /**
     * Returns the dictionary in a List<String> format
     */
    public List<String> getDictionary() throws IOException { return dictionaryWords; }

}