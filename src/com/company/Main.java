package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static final Scanner inputScanner = new Scanner(System.in);
    public static List<Integer> rollHolder1; public static List<Integer> rollHolder2;                                   //rollHolder1 and rollHolder2 are used to hold dice rolls and modifiers as lists of integers

    public static void main(String[] args) {                                                                            //program starts here
        while (true) {                                                                                                  //the main method uses a while loop, which can only be broken using break
            System.out.println("Press 1 for an endless roll interpreter");                                              //the program starts with instructions for the user
            System.out.println("Press 2 to play a game against the computer");
            System.out.println("Press 3 to play a game against another player");
            System.out.println("Type exit in any mode to return to the previous menu");
            System.out.println("Type exit in this menu to exit the program");
            String response = inputScanner.nextLine().toLowerCase();                                                    //their response is captured via inputScanner

            if      (response.equals("1")) keepRolling();                                                               //if their response is 1, they'll go to keepRolling() to just keep rolling dice
            else if (response.equals("2")) game(false);                                                         //if their response is 2, they'll play the game without a second human player, against the computer
            else if (response.equals("3")) game(true);                                                           //if their response is 3, they'll play the game with a second human player
            else if (response.contains("exit")) break;                                                                  //if they type "exit" at this point, they'll quit the program using break
            else System.out.println("please try again");}}                                                              //if their response is not one of these options, they'll return to the start of the main() method

            public static void keepRolling(){                                                                           //keepRolling() is one of the three game modes
                while(true){                                                                                            //again, this method uses a while loop that can only be escaped via break
                    System.out.println("Type in a dice roll");
                    String input = inputScanner.nextLine();                                                             //input is captured into input, and must use valid dice notation to continue
                    if(input.toLowerCase().contains("exit")) break;                                                     //if they type "exit", the loop will be broken, leading back to the main() menu
                    rollHolder1 = stringToRoll(input);                                                                  //rollHolder1 holds the input converted to a roll via stringToRoll()
                    System.out.println("Your roll was " + input);
                    System.out.println(rollToString(rollHolder1));                                                      //once the roll has been created, it can be returned to a human-readable string
                    System.out.println();}}                                                                             //after the player has been told their roll, the loop will continue from the top

            public static void game(boolean human){
                while(true){
                    System.out.println("Player 1, type in a dice roll");
                    String input = inputScanner.nextLine();
                    if(input.toLowerCase().contains("exit")) break;                                                     //typing exit whenever prompted for input will again return the player to the main() menu
                    else rollHolder1 = stringToRoll(input);                                                             //as before, the (first) player will be prompted for their roll which will be held in rollHolder1
                    if(human){
                        System.out.println("Player 2, type in a dice roll");
                        input = inputScanner.nextLine();
                        if(input.toLowerCase().contains("exit")) break;
                        else rollHolder2 = stringToRoll(input);                                                         //if the game is human vs human, player 2 will be prompted and have their roll held in rollHolder2
                    } else { rollHolder2 = randomRoll(); }                                                              //otherwise, rollHolder2 will just hold a random roll from randomRoll();
                                                                                                                        //rest of the method is continued the same regardless of player 2 being human or the computer
                    System.out.println("Player 1: "+rollToString(rollHolder1));
                    System.out.println("Player 2: "+rollToString(rollHolder2));                                         //player 1 and player 2's roll will be shown in a human readable format
                    if(rollTotal(rollHolder1)>rollTotal(rollHolder2)){
                        System.out.println("Player 1 has won");
                    } else if(rollTotal(rollHolder1)<rollTotal(rollHolder2)){
                        System.out.println("Player 2 has won");
                    } else System.out.println("Both players draw!");                                                    //depending on the win state, a different text will also be shown
                    System.out.println();}}                                                                             //then, the loop starts again until exit has been typed


            public static int rand(int max){ return (int) Math.floor(Math.random()*max) +1;}                            //rand() is a simple shorthand method to make getting random numbers more convenient

            public static List<Integer> stringToRoll(String input){                                                     //stringToRoll() takes an String input which is a human-input dice roll in dice notation
                List<Integer> firstHalf = addDiceFormatToIntegers(input);                                               //this method returns results, but to calculate these results there are two steps, requiring
                List<Integer> secondHalf = addModsToIntegers(input);                                                    //- a firstHalf() method and a secondHalf() method. firstHalf() handles the dice rolls while
                List<Integer> results = new ArrayList<>();                                                              //- secondHalf() handles the modifiers. All of these are ArrayLists of Integers
                results.addAll(firstHalf); results.addAll(secondHalf);                                                  //afterwards, the outcome of firstHalf() and secondHalf() are added to the results
                return results;}                                                                                        //the results are returned as-is; an array of integers holding every dice roll and modifier

            public static List<Integer> randomRoll(){
                int dice = rand(10); int faces = rand(10);
                int mod = rand(20)-10;                                                                            //to make a random roll, the dice, faces and modifiers are given random values in a reasonable range
                List<Integer> roll = new ArrayList<>();
                for(int i = 0; i < dice; i++){ roll.add(rand(faces));}                                                  //for each dice, a random number up to the number of faces is added to the array
                roll.add(mod); return roll;}                                                                            //then, the modifier is added to the roll which is returned

            public static List<Integer> addDiceFormatToIntegers(String string){
                Pattern pattern = Pattern.compile("\\d*d\\d*");                                                         //this pattern means 'every time a "d" occurs, keeping the information for the numbers alongside'
                Matcher matcher = pattern.matcher(string);                                                              //matcher matches the pattern in the string given to the method
                int dice; int faces; List<Integer> list = new ArrayList<>();
                while(matcher.find()){                                                                                  //this is a loop for every match, with that match being referred to as matcher.group()
                    String roll = matcher.group();                                                                      //to simplify things, I assign matcher.group() to a string called roll instead
                    if(roll.startsWith("d")) dice=1;                                                                    //every match that doesn't start with a number defaults to 1 dice
                    else dice = Integer.parseInt(roll.split("d")[0]);                                            //every other match uses the number before the 'd' for the number of dice
                    if( roll.endsWith("d")) faces=6;                                                                    //the default for faces is 6, as 6 sided-dice are the most common
                    else faces = Integer.parseInt(roll.substring(roll.indexOf('d')+1));
                    for(int i = 0; i < dice; i++){ list.add(rand(faces));}}                                             //for each dice, a random number up to the number of faces is added to the array
                return list;}

            public static List<Integer> addModsToIntegers(String string){
                Pattern pattern = Pattern.compile("([+\\-])\\d*(?!d)");                                                 //this pattern means 'every number with a '+' or '-' in front and no 'd' afterwards
                Matcher matcher = pattern.matcher(string);
                List<Integer> list = new ArrayList<>();
                while(matcher.find()){
                    if(matcher.group().contains("-") && !matcher.group().endsWith("-")){                                //every match which contains a '-' but doesn't end with it is a viable negative number
                        list.add(-Integer.parseInt(matcher.group().substring(matcher.group().indexOf("-") + 1)));}      //each of these is added to the returned integer list as a negative number
                    else if(matcher.group().contains("+") && !matcher.group().endsWith("+")){
                        list.add(Integer.parseInt(matcher.group().substring(matcher.group().indexOf("+") + 1)));}}      //similarly, all positive numbers are added to the same list as positive numbers
                return list;}

            public static String rollToString(List<Integer> roll){
                int total = 0;
                for (int value : roll){ total+=value; }                                                                 //rollToString() includes a total, which uses an enhanced for loop, adding all roll values to total
                String outString = roll.toString().substring(1, roll.toString().length()-1) + " = " + total;            //the output string is the roll as a string without the first and last characters, with an equals
                outString=outString.replaceAll(",", " +");                                            //- sign, the total of the roll and all of the commas converted to plus signs
                return outString;}

            public static int rollTotal(List<Integer> roll){                                                            //rollTotal() simply returns the sum of all the integers in the roll
                int total = 0;
                for (int value : roll){ total+=value; }
                return total;}}