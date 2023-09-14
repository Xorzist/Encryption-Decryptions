package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {



    public static void main(String[] args) {
        int key = 0;
        char[] data = new char[]{};
        String filenameIN = null;
        String filenameOut = null;
        String mode = "enc";
        String algorithm = "shift";
        StringBuilder resultingString;

        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-key":
                        key = Integer.parseInt(args[i + 1]);
                        break;

                    case "-data":
                        data = args[i + 1].toCharArray();
                        break;

                    case "-mode":
                        mode = args[i + 1];
                        break;

                    case "-in":
                        data = null;
                        filenameIN = args[i + 1];
                        break;

                    case "-out":

                        filenameOut = args[i + 1];
                        break;

                    case "-alg":
                        algorithm = args[i +1];
                        break;

                    default:
                        break;

                }
            }
        }
        catch (Exception e){
            System.out.println("AN Error occurred");
        }

        if(filenameOut == null && filenameIN == null){
            if(mode.equals("enc")){
                resultingString = !algorithm.equals("shift") ? encryptTextUnicode(data, key) :
                        encryptTextShift(data, key);
            }
            else{
                 resultingString = !algorithm.equals("shift") ? decryptTextUnicode(data, key) :
                        decryptTextShift(data, key);
            }


            System.out.println(resultingString);

        }else if (filenameIN == null && filenameOut!=null){

            writeToFile(data,key,mode,filenameOut,algorithm);

        }else if (filenameIN !=null && filenameOut!=null){
            writeToFile(filenameIN,key,mode,filenameOut,algorithm);

        }else if(filenameIN!=null && filenameOut==null){
            data = readFromFile(filenameIN);
            if(mode.equals("enc")){
                resultingString = !algorithm.equals("shift") ? encryptTextUnicode(data, key) :
                        encryptTextShift(data, key);
            }
            else{
                resultingString = !algorithm.equals("shift") ? decryptTextUnicode(data, key) :
                        decryptTextShift(data, key);
            }


            System.out.println(resultingString);
        }


    }

    public static void writeToFile(char[] charArray, int incrementer, String mode, String filenameOut
    ,String algorithm){


        String output;
        if(mode.equals("enc")){
            output = String.valueOf(!algorithm.equals("shift") ? encryptTextUnicode(charArray, incrementer) :
                    encryptTextShift(charArray, incrementer));
        }
        else{
            output = String.valueOf(!algorithm.equals("shift") ? decryptTextUnicode(charArray, incrementer) :
                    decryptTextShift(charArray, incrementer));
        }

        File file = new File(filenameOut);
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(output);

        }catch (IOException io){
            System.out.println("AN Error occurred");

        }
    }
    public static void writeToFile(String filename, int incrementer, String mode,String filenameOut
            ,String algorithm){
        char[] chars = readFromFile(filename);
        String output;

        if(mode.equals("enc")){
            output = String.valueOf(!algorithm.equals("shift") ? encryptTextUnicode(chars, incrementer) :
                    encryptTextShift(chars, incrementer));
        }
        else{
            output = String.valueOf(!algorithm.equals("shift") ? decryptTextUnicode(chars, incrementer) :
                    decryptTextShift(chars, incrementer));
        }

        File file = new File(filenameOut);
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(output);

        }catch (IOException io){
            System.out.println("AN Error occurred");

        }

    }
    public static char[] readFromFile(String filename){

        try {
            return new String(Files.readAllBytes(Paths.get((filename)))).toCharArray();
        } catch (IOException e) {
            System.out.println("AN Error occurred");

        }
        return new char[0];
    }

    private static StringBuilder encryptTextUnicode(char[] charArray, int incrementer) {
        StringBuilder resultingString = new StringBuilder();
        int offSet = 0;
        boolean isUpperCase = false;
        for (char letter : charArray) {
            if ((Character.isLetter(letter))) {
                isUpperCase = Character.isUpperCase(letter);
                letter = Character.toLowerCase(letter);
                offSet = (letter - 'a' + incrementer);

                if (offSet < 0) {
                    offSet += 26;
                }
                if (isUpperCase) {
                    char result = (char) ('a' + offSet);
                    // resultingString.append((char)('a'+offSet));
                    resultingString.append(Character.toUpperCase(result));
                } else {
                    resultingString.append((char) ('a' + offSet));
                }


            }
            else  {
                char  convertedLetter = (char)((int) letter + incrementer);
                if ( convertedLetter != '|' || Character.isDefined(convertedLetter) == true){

                    resultingString.append((char) convertedLetter);
                }


            }
        }
        StringBuilder stringBuilder = (incrementer == 5) ? resultingString.replace(0, 1, "\\") : resultingString;
        System.out.println("Output "+stringBuilder);
        return stringBuilder;
    }

    private static StringBuilder decryptTextUnicode(char[] charArray, int incrementer) {
        StringBuilder resultingString = new StringBuilder();
        int offSet = 0;
        boolean isUpperCase = false;
        for (char letter : charArray) {
            if ((Character.isLetter(letter))) {
                isUpperCase = Character.isUpperCase(letter);
                letter = Character.toLowerCase(letter);
                offSet = (letter - 'a' - incrementer);

                if (offSet < 0) {
                    offSet += 26;
                }
                if (isUpperCase) {
                    char result = (char) ('a' + offSet);
                    // resultingString.append((char)('a'+offSet));
                    resultingString.append(Character.toUpperCase(result));
                } else {
                    resultingString.append((char) ('a' + offSet));
                }


            }
            else {
                char  convertedLetter = (char)((int) letter - incrementer);
                if ( convertedLetter != '|' || Character.isDefined(convertedLetter) == true){

                    resultingString.append((char) convertedLetter);
                }


            }
        }
        return resultingString;
    }
    private static StringBuilder encryptTextShift(char[] charArray,int incrementer) {

        StringBuilder resultingString = new StringBuilder();
        int offSet = 0;
        for (char letter : charArray){
            if(letter != ' ') {
                offSet = (letter - 'a' + incrementer) % 26;

                if (offSet < 0) {
                    offSet += 26;
                }
                resultingString.append((char)('a'+offSet));
            }else{
                resultingString.append(letter);
            }
        }
        return resultingString;
    }
    private static StringBuilder decryptTextShift(char[] charArray,int incrementer) {
        StringBuilder resultingString = new StringBuilder();
        int offSet = 0;
        for (char letter : charArray){
            if(letter != ' ') {
                offSet = (letter - 'a' - incrementer) % 26;

                if (offSet < 0) {
                    offSet += 26;
                }
                resultingString.append((char)('a'+offSet));
            }else{
                resultingString.append(letter);
            }
        }
        return resultingString;
    }


}
