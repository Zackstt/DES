import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.*;

public class Des {

    private static SecretKeySpec secretKey;
    private static byte[] secretKeyBytes;
    private static byte[] iv = {22,33,11,44,55,99,66,77};

    public String encryptFile = "encrypt.txt";
    public String decryptFile = "decrypt.txt";
    public String outputFile = "output.txt";

    // Constructor,
    public Des(int option) {


        Scanner inputListener = new Scanner(System.in);

        System.out.println("Enter hexadecimal secret key: ");

        secretKeyBytes = hexStringToByte(inputListener.nextLine());

        this.secretKey = new SecretKeySpec(secretKeyBytes, "DES");


        callMethod(option);
    }

    //This function will be passed an encrypt (1) or decrypt (2) option and runs the required functions
    public void callMethod(int option) {



        // The input chosen by the user will result in the encrypt/decrypt function call
        switch (option) {
        case 1:
            String inputText = readFileText(encryptFile);

            byte[] encrypted = encrypt(inputText);

            writeFileByte(encrypted, outputFile);
            System.out.println("Your encrypted text is saved locally to " + outputFile);
            break;

        case 2:
            byte[] inputCipher = readFileByte(decryptFile);
            //System.out.println("Input Cipher: " + inputCipher +'\n');

            String decrypted = decrypt(inputCipher);
            //System.out.println("Output String: " + decrypted +'\n');
            plainTextWrite(decrypted, outputFile);
            System.out.println("Your encrypted text is saved locally to " + outputFile);
            System.out.println("Message from sender: " + decrypted);

            break;

            default: System.out.println("An error has occurred in callMethod");

        }

    }

    //Writes a file as a byte array
    public void writeFileByte(byte[] cipherText, String fileName) {

        try {
            Path path = Paths.get(fileName);
            Files.write(path, cipherText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Reads in a file that is saved as a byte array
    public byte[] readFileByte(String fileName) {
        try {
            Path path = Paths.get(fileName);
            byte[] returnVal = Files.readAllBytes(path);
            return returnVal;
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] empty = {};
        return empty;

    }

    //readFileText reads a plain text file in.
    public static String readFileText(String fileName) {
        try {

            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            String returnVal = new String(encoded, "UTF8");

            returnVal = returnVal.substring(2, returnVal.length());

            return returnVal;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path path = Paths.get(fileName);
        try {
            String returnVal = Files.lines(path).toString();
            return returnVal;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

    //plainTextWrite writes to a file in plaintext
    public static void plainTextWrite(String s, String file) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(s);
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Encrypt function takes a string and encrypts it. It returns an encrypted byte array
    private static byte[] encrypt(String input) {
        try {
            AlgorithmParameterSpec aps = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            byte[] text = input.getBytes("UTF8");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, aps);
            byte[] encryptedText = cipher.doFinal(text);
            return encryptedText;

        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] empty = {0};
        return empty;

    }

    // decrypt function takes a byte array as input and decrypts it. It returns a plaintext string
    private static String decrypt(byte[] input) {
        try {
            AlgorithmParameterSpec aps = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, aps);
            byte[] decryptedBytes = cipher.doFinal(input);
            String returnVal = new String(decryptedBytes);
            return returnVal;

        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return "";

    }

    // Turns a hex string input to a byte array
    static byte[] hexStringToByte(String inputString) {
        return inputString.getBytes();
    }

}
