
/**
 * Write a description of class Decryption here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Decryption
{
    String msg;
    int keyCode;
    boolean debugger = true;
    private char[] ASCIIArray = new char[95];
    private char[][] permutateArray;
    private char[] tmp_alphabetMod;
    private int origFirstIndex;
    String modMessage = "";
    public Decryption(String msg, int key)
    {
        this.keyCode = key;
        this.msg = msg;

        System.out.println("The Message is: " + msg);

        permutateArray = new char[(msg.length()/8)+1][8];
        createASCIIArray();
        tmp_alphabetMod = ASCIIArray.clone();

        permutate();
        permutateApplication();
        decipherCrypt();
        rotationalShiftApplication(tmp_alphabetMod);
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

    private void permutateApplication(){
        testMsg("Beginning reverse permutation");
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

        testMsg("Decryption permutation");
        System.out.println("Message de-permutated to : " + message);
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

    private void permutate()
    {
        testMsg("Beginning permutate setup part 1/3...");

        char messageComponents[] = msg.toCharArray();
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
        testMsg("Beginning Permutate application part 2/2...");

    }

    private void decipherCrypt()
    {
        int signature;

        signature = signatureIdentifier();
        signature = signatureFindFirstLetter(signature);
        //using keycode..;
        testMsg("Signature identified: " + (signature) + "\nAlphabet Starting point: "+ ASCIIArray[signature] + "\nSetting up Rotational Shift Method Part I...");
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
        
        
        origFirstIndex = letterComparisionIndexRetreive('a', tmp_alphabetMod);
        System.out.println("Orignal Index gap is.." + origFirstIndex);
        demostrateArray(tmp_alphabetMod);
        System.out.println();

        demostrateArray(ASCIIArray);
        System.out.println();
        testMsg("Rotational Shift setup Part 1/2 Completed");
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
            tmp_alphabetStore = ASCIIArray[tmp_index - 1];                         // Applies index to array
            modMessage += tmp_alphabetStore;                                            // Attaches alphabet from array to the message

        }

        testMsg("Rotational Shift application Part 2/2 completed");
        System.out.println("The Message is converted to: " + modMessage);
        System.out.println("Original Message is convert: " + msg);
    }  

    public void demostrateArray(char[] arrayDisplayed)                          // Unnessesary Method that prints the array
    {
        for(char l: arrayDisplayed)
        {
            System.out.print(l);
        }
    }

    private int signatureFindFirstLetter(int signature)
    {

        signature = (signature % 94)+ 1;
        return signature;
    }

    private int signatureIdentifier()
    {
        int key;
        testMsg("Initializing Signature");
        key = keyCode % 94;
        if(key == 0)
        {
            testMsg("In IF Statement, defaulting to 1");
            key = 1;
        }

        testMsg("Signature identified");
        return key;
    }

}
