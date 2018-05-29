package com.gator;
/*
  Created by Wally Haven on 5/16/2018.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import static java.lang.String.format;

class Lottery {
    private final int SIZE;
    private final int MEGABUCKS;
    private final ArrayList<Integer> lottoTicket;
    private int num_boards;
    private String answer;
    private final String outFileName;
    private FileWriter outFile;
    private Scanner scanner;
    private final char myCopyRight;

    Lottery() {
        SIZE = 6;
        MEGABUCKS = 48;
        lottoTicket = new ArrayList<>(SIZE);
        num_boards = 0;
        answer = "n";
        outFileName = "LotteryNumbers.txt";
        outFile = null;
        scanner = new Scanner(System.in);
        myCopyRight = '\u00A9';  //unicode copyright symbol
    }

    void generateNumbers() throws IOException {
        while (!answer.equals("Y")) {
            System.out.println("\n\t\t JAVA LOTTERY NUMBER GENERATOR");
            System.out.println("\t\t\t" + myCopyRight + java.time.Year.now() + " by Wally Haven");
            System.out.println("\nHow many sets of numbers do you wish to play?");
            num_boards = getUserNumber();
            if (num_boards > 0) {
                Random rand = new Random();
                createFile();

                for (var index = 1; index <= num_boards; index++) {
                    System.out.println(format("\nBoard: %d", index));
                    outFile.write(String.format("\n" + "Board: %d \n", index));
                    for (var i = 0; i < SIZE; i++) {
                        lottoTicket.add(rand.nextInt(MEGABUCKS) + 1);
                        var tmp_num = lottoTicket.get(i);
                        var flag = searchTicket(lottoTicket, tmp_num);
                        while (flag) {
                            tmp_num = rand.nextInt(MEGABUCKS) + 1;
                            flag = searchTicket(lottoTicket, tmp_num);
                            if (!flag) {
                                lottoTicket.set(i, tmp_num);
                            }
                        }
                    }
                    Collections.sort(lottoTicket);

                    for (var ticket : lottoTicket) {
                        try {
                            System.out.println(ticket + " ");
                            outFile.write(String.valueOf(ticket) + " ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    lottoTicket.clear();
                }
                System.out.println("\nDo you wish to exit?");
                answer = getUserString();
            }
            else {
                System.out.println("\nGoodbye");
                return;
            }
            System.out.println("\nGoodbye and good luck!");
            outFile.close();
        }
    }

    private void createFile() {
        try {
            outFile = new FileWriter(outFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getUserNumber() {
        for (; ; ) {
            Scanner scanner = new Scanner(System.in);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input was not a number. Please retry.");
            }
        }
    }

    private String getUserString() {
        String inputLine;
        scanner = new Scanner(System.in);

        inputLine = scanner.nextLine();
        return inputLine.toUpperCase();
    }

    private boolean searchTicket(ArrayList<Integer> lottoTicket, int num) {
        return lottoTicket.contains(num);
    }
}
