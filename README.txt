*****************************************************************************************
*                                                                                       *
*                                       READ ME                                         *
*                                                                                       *
*****************************************************************************************

*If you don't understand the rules of hangman, please read the following. Otherwise, the
game pane should be very easy to understand for game play.

*RULES:
    *To initiate game play, you must select a letter that you suspect will be a part of
     the word you are trying to decipher.
    *You may also type the entire word into the provided text box to guess what the word
     is when you think you know it.
     *There will be a prompt before the start of the game that will allow you to choose a difficulty,
          BEGINNER, INTERMEDIATE or EXPERT.
             *BEGINNER allows you to make 16 incorrect guesses.
             *INTERMEDIATE allows you to make 12 incorrect guesses.
             *HARD allows you to make 6 incorrect guesses.
         *The body parts of the hangman will be displayed at different rates depending on the difficulty.
    *If you guess a letter or the word incorrectly, your "guesses remaining"
     will decrement.
    *Once your "guesses remaining" count is equal to 0, you will lose the game and be given an option to
     play again or not.

*YOU MUST READ THE FOLLOWING TO ENSURE THAT THE PROGRAM WORKS CORRECTLY!!!!

*SETUP:
    *You must have the resources folder located inside of the src folder of our project.
    *You must have the HangmanWordsList.txt (file with word choices for game play) located in the src folder or the project folder.
    *You must have the source code (Hangman.java, HangDictionary.java, HangmanGUI.java)
     located in the space hierarchical position as the resource folder. In other words the
     java files and the resources folder must have the same parent folder, which will be the src folder.
    *All .png files used for the hangman body parts must be located in the resources folder.
    *.jar located in the out folder executes the folder.

    *DIRECTORY TREE (Inside project folder):
        *Snapshots
        *README.txt
        *out
            *artifacts
               *FinalProject.jar
            *production
        *src
            *resources
                *Arm1.png
                *Arm2.png
                *Blank.png
                *Body.png
                *Ear1.png
                *Ear2.png
                *Eye1.png
                *Eye2.png
                *Foot1.png
                *Foot2.png
                *Hand1.png
                *Hand2.png
                *Hanger.png
                *Head.png
                *Leg1.png
                *Mouth.png
                *Nose.png
            *HangDictionary.java
            *Hangman.java
            *HangmanGUI.java
            *HangmanWordsLists.txt
