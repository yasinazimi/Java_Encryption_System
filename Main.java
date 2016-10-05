/* Yasin Azimi - 11733490 */

public class Main
{
    public static void main(String args[])
    {
        new Main();
    }

    public Main()
    {
        menu();
    }

    private void menu()
    {
        char command;
        while( (command = readMenuCommand()) != 'x'){
            switch(command)
            {
                case 'e': encryptionSetup();
					break;
                case 'd': decryptionSetup();
					break;
                case 'x':
					break;
                default: help();
            }
        }
    }

    private char readMenuCommand()
    {
        System.out.println("Enter a command to either Encrypt 'e' or Decrypt 'd' a message");
        return In.nextChar();
    }

    private void encryptionSetup()
    {
        System.out.println("Enter message to encrypt");
        new Encryption(readMessage());
    }

    private void decryptionSetup()
    {
        System.out.println("Enter message to decrypt");
        new Decryption(readMessageDe(), readKeyCode());
    }

    
    private int readKeyCode()
    {
        System.out.println("Enter KeyCode 2/2");
        return In.nextInt();
    }
    
    private void help()
    {
        System.out.println("Woops thats not the right command! \n 'e' to encrypt OR 'd' to decrypt. \n 'x' to exit the program");
    }

    private String readMessage()
    {
        System.out.println("Enter Message: ");
        return In.nextLine();
    }

    private String readMessageDe()
    {
        System.out.println("Enter Message 1/2");
        return In.nextLine();
    }
}
