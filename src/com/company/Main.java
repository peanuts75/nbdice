package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Scanner inputScanner = new Scanner(System.in);
    public static List<Integer> rollHolder1; public static List<Integer> rollHolder2;

    public static void main(String[] args) {
        while (true) {

            System.out.println("Press 1 for an endless roll interpreter");
            System.out.println("Press 2 to play a game against the computer");
            System.out.println("Press 3 to play a game against another player");
            System.out.println("Type exit in any mode to return to the previous menu");
            System.out.println("Type exit in this menu to exit the program");
            String response = inputScanner.nextLine().toLowerCase();

            if      (response.equals("1")) keepRolling();
            else if (response.equals("2")) game(false);
            else if (response.equals("3")) game(true);
            else if (response.contains("exit")) break;
            else System.out.println("please try again");}}

            public static void keepRolling(){
                while(true){
                    System.out.println("Type in a dice roll");
                    String input = inputScanner.nextLine();
                    if(input.toLowerCase().contains("exit")) break;
                    rollHolder1 = stringToRoll(input);
                    System.out.println("Your roll was " + input);
                    System.out.println(rollToString(rollHolder1));
                    System.out.println();}}

            public static void game(boolean human){
                while(true){
                    System.out.println("Player 1, type in a dice roll");
                    String input = inputScanner.nextLine();
                    if(input.toLowerCase().contains("exit")) break;
                    else rollHolder1 = stringToRoll(input);
                    if(human){
                        System.out.println("Player 2, type in a dice roll");
                        input = inputScanner.nextLine();
                        if(input.toLowerCase().contains("exit")) break;
                        else rollHolder2 = stringToRoll(input);
                    } else { rollHolder2 = randomRoll(); }

                    System.out.println("Player 1: "+rollToString(rollHolder1));
                    System.out.println("Player 2: "+rollToString(rollHolder2));
                    if(rollTotal(rollHolder1)>rollTotal(rollHolder2)){
                        System.out.println("Player 1 has won");
                    } else if(rollTotal(rollHolder1)<rollTotal(rollHolder2)){
                        System.out.println("Player 2 has won");
                    } else System.out.println("Both players draw!");
                    System.out.println();}}


            public static int rand(int max){
                return (int) Math.floor(Math.random()*max) +1;}

            public static List<Integer> stringToRoll(String input){
                List<Integer> firstHalf = addDiceFormatToIntegers(input);
                List<Integer> secondHalf = addModsToIntegers(input);
                List<Integer> results = new ArrayList<Integer>();
                results.addAll(firstHalf); results.addAll(secondHalf);
                int sum = 0; for (Integer result : results) sum += result;
                return results;}

            public static List<Integer> randomRoll(){
                int dice = rand(10); int faces = rand(10);
                int mod = rand(20)-10;
                List<Integer> roll = new ArrayList<Integer>();
                for(int i = 0; i < dice; i++){ roll.add(rand(faces));}
                roll.add(mod); return roll;}

            public static List<Integer> addDiceFormatToIntegers(String string){
                Pattern pattern = Pattern.compile("\\d*d\\d*");
                Matcher matcher = pattern.matcher(string);
                int dice; int faces; List<Integer> list = new ArrayList<Integer>();
                while(matcher.find()){
                    String roll = matcher.group();
                    if(roll.startsWith("d")) dice=1;
                    else dice = Integer.parseInt(roll.split("d")[0]);
                    if( roll.endsWith("d")) faces=6;
                    else faces = Integer.parseInt(roll.substring(roll.indexOf('d')+1));
                    for(int i = 0; i < dice; i++){ list.add(rand(faces));}}
                return list;}

            public static List<Integer> addModsToIntegers(String string){
                Pattern pattern = Pattern.compile("([+\\-])\\d*(?!d)");
                Matcher matcher = pattern.matcher(string);
                List<Integer> list = new ArrayList<Integer>();
                while(matcher.find()){
                    if(matcher.group().contains("-") && !matcher.group().endsWith("-")){
                        list.add(-Integer.parseInt(matcher.group().substring(matcher.group().indexOf("-") + 1)));}
                    else if(matcher.group().contains("+") && !matcher.group().endsWith("+")){
                        list.add(Integer.parseInt(matcher.group().substring(matcher.group().indexOf("+") + 1)));}}
                return list;}

            public static String rollToString(List<Integer> roll){
                int total = 0;
                for (int value : roll){ total+=value; }
                String outString = roll.toString().substring(1, roll.toString().length()-1) + " = " + total;
                Pattern p = Pattern.compile(","); Matcher m = p.matcher(outString);
                outString=outString.replaceAll(",", " +");
                return outString;}

            public static int rollTotal(List<Integer> roll){
                int total = 0;
                for (int value : roll){ total+=value; }
                return total;}}