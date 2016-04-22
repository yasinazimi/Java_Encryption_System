
/**
 * Write a description of class Encryption here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Encryption
{
    boolean debugger = true;
    private int lenMessage; //length of message
    private int keyCode; // special Key used
    private String modMessage = ""; 
    private char[] tmp_alphabetMod;
    private char[] ASCIIArray = new char[95];
    private String msg;
    private char[][] permutateArray;

    public Encryption(String msg)
    {
        System.out.println("The Message is: " + msg);
        this.msg = msg;
        permutateArray = new char[(msg.length()/8)+1][8];

        createASCIIArray();                           // Creating ASCII ARRAY
        tmp_alphabetMod = ASCIIArray.clone();         // COPYING ASCII array to another for rotational use
        lenMessage = msgLenMeasure(msg);              // Method that measures length of message;
        keyCode = processKeyFormulation(msg);         // Method that creates the keyCode
        rotationalShift();                            // Method that setups the necessary parts for rotational shift
        rotationalShiftApplication(tmp_alphabetMod);  // Method that applies the rotational shift to the message
        permutate();
        permutateApplication();
    }

    private void testMsg(String testMessage)            //De-bug Method that uses test messages throughout, is set by debugger variable;
    {
        if(debugger == true)
        {
            System.out.println(testMessage);
        }
    }

    private void createASCIIArray()                     //Method that creates the ASCII Array
    {
        testMsg("Creating ASCII Array");
        char ASCIIConv = 32;
        for(int i = 0;i < ASCIIArray.length;i++)
        {
            ASCIIArray[i] = ASCIIConv;
            ASCIIConv++;
        }
        testMsg("ASCII Array established");
    }

    private int msgLenMeasure(String msgMeasured)       //Method that measures the length of the string
    {
        testMsg(" Message: " + msgMeasured);
        testMsg(" Length of Message: " + msgMeasured.length() + " characters including space ");
        return msgMeasured.length();
    }

    private int processKeyFormulation(String message)   // Method that creates the keyCode
    {
        //Prep work in setting up variables
        int alphabetCounter = 0;
        char[] tmp_alphabetBlock = ASCIIArray.clone();

        // Conversion to lowerCase
        System.out.println("Initializing Key Generation Part I...");
        testMsg("Conversion to lowerCase \n"+  message);
        message = message.toLowerCase();                // Conversion to lower case
        testMsg("Converted to lowerCase \n" + message);

        //Looping to identify letters and remove them in the tmp_alphabetBlock Array
        testMsg("Initializing Key Generation Process Part II...");
        for(int i = 0;i < message.length();i++)
        {
            if(message.charAt(i) == searchAlphabet(message.charAt(i) ,tmp_alphabetBlock)){
                for(int e = 0;e < tmp_alphabetBlock.length; e++)
                {
                    if(message.charAt(i) == tmp_alphabetBlock[e]) 
                    {
                        testMsg("Letter identified: " + tmp_alphabetBlock[e]);
                        tmp_alphabetBlock[e] = ' '; //------------------------------------- Turns it to manually assigned capital character in the tmp_alphabetBlock Array - makes it unaccessible
                        alphabetCounter++;          //-----------------------------------------Increments in alphabetCounter for later use
                        break;
                    }
                }
            }

        }

        testMsg("Total Number of Alphabets used: "  + alphabetCounter);

        testMsg("Initializing final Key Output Part III...");
        keyCode = lenMessage * alphabetCounter;     //Final Calculation
        System.out.println("Key Formed , the key is :" + keyCode);

        return keyCode;
    }

    private char searchAlphabet(char letter, char[] arrayList)                  // SubMethod that searches and retrieves the letter from an array
    {
        for(char checkLetter: arrayList)
        {

            if(letter == checkLetter)
            {
                return checkLetter;
            }    
        }
        return '\n';
    }

    private void rotationalShift()                                              // Main Method that sets up rotational Shift
    {
        //using keycode..

        int signature;
        signature = signatureCreation(keyCode);
        testMsg("Signature Created: " + (signature) + "\nAlphabet Starting point: "+ ASCIIArray[signature] + "\nSetting up Rotational Shift Method Part I...");
        for(int i = 0;i < tmp_alphabetMod.length;i++)
        {
            // testMsg("Signature current: "+ signature + "Current Alphabet: " + ASCIIArray[signature]);
            tmp_alphabetMod[i] = ASCIIArray[signature];
            // testMsg("Alphabet Manipulated to: "+ tmp_alphabetMod[i]);
            signature++;

            if(signature > tmp_alphabetMod.length - 1)
            {
                signature = 0;
            }
        }
        demostrateArray(ASCIIArray);
        System.out.println();
        demostrateArray(tmp_alphabetMod);
        System.out.println();
        testMsg("Rotational Shift setup Part 1/2 Completed");
    }

    public void demostrateArray(char[] arrayDisplayed)                          // Unnessesary Method that prints the array
    {
        for(char l: arrayDisplayed)
        {
            System.out.print(l);
        }
    }

    private void rotationalShiftApplication(char[] array)                       // Main method that applies rotational shift to the message, like a cisear cipher
    {
        testMsg("Rotational Shift application Part 2/2...");
        char tmp_alphabetStore;
        int tmp_index;

        for(int i = 0;i < msg.length();i++)
        {
            tmp_alphabetStore = msg.charAt(i);
            tmp_index = letterComparisionIndexRetreive(tmp_alphabetStore, ASCIIArray);  // Retrieves index
            //testMsg("Swapping letter: "+ tmp_alphabetStore + " To.. " + tmp_alphabetMod[tmp_index]);
            tmp_alphabetStore = tmp_alphabetMod[tmp_index - 1];                         // Applies index to array
            modMessage += tmp_alphabetStore;                                            // Attaches alphabet from array to the message

        }

        testMsg("Rotational Shift application Part 2/2 completed");
        System.out.println("\n\nThe Message is converted to:" + modMessage);
        System.out.println("Original Message is convert:" + msg +"\n\n");
    }   

    private int signatureCreation(int key)
    {
        testMsg("Initializing Signature");
        key = key % 94;
        if(key == 0)
        {
            testMsg("In IF Statement, defaulting to 1");
            key = 1;
        }

        testMsg("Signature Completed");
        return key;
    }

    private int letterComparisionIndexRetreive(char letter , char[] listFor)    // SubMethod that retrieves the index from an array
    {
        int counter = 0;

        for(char letterPulled: listFor)
        {
            counter++;
            // testMsg("Checking letter: " + letter + " Current letter: " + letterPulled);
            if(letter == letterPulled)
            {
                return counter;
            }
        }

        return counter;
    }

    private void permutate()
    {
        testMsg("Beginning permutate setup part 1/3...");

        char messageComponents[] = modMessage.toCharArray();
        int counter = 0;

        for(int i = 0;i < permutateArray.length;i++)
        {
            for(int e = 0; e < permutateArray[i].length;e++)                        // Loop that creates block sized bits of 8 bits each
            {
                //testMsg("Counter is currently at" + counter);
                if(counter < msg.length())
                {               
                    permutateArray[i][e] = messageComponents[counter];
                    System.out.print("" + permutateArray[i][e]);
                    counter++;

                } else
                {   
                    permutateArray[i][e] = ' ';
                    System.out.print("" + permutateArray[i][e]);
                }

            }
            System.out.println();
        }
        testMsg("Permutate setup part 1 completed");
        testMsg("Beginning Permutate application part 2/3...");

    }

    private void permutateApplication(){
        testMsg("Starting output generation Part 3/3...");
        String message = "";
        for(int i = 0;i < permutateArray.length;i++)
        {
            for(int e = 0;e < 4;e++){
                shiftOneRight(i);
            }
        }

        for(int r = 0;r < permutateArray.length;r++)
        {
            for(int g = 0;g < permutateArray[r].length;g++)
            {
                message += "" + permutateArray[r][g];

            }
        }

        testMsg("Finished final output generation. Final encryption completed");
        System.out.println("\n\nMessage permutated to : " + message + "\nKeycode is:"+keyCode);
    }

    private void shiftOneRight(int row) //Shifts the block to one right
    {
        char elementToSwap = permutateArray[row][permutateArray[row].length-1]; // Initial Element
        char tempElement; // Element to hold
        char elementPushed; // Element being pushed (next element)
        //First index recorded

        for(int i = 0;i < permutateArray[row].length;i++){

            tempElement = permutateArray[row][i];
            permutateArray[row][i] = elementToSwap;

            elementToSwap = tempElement;
        }
    }

}
