package we.jufrkhma.cli.input;

import we.jufrkhma.GradeCalculator;

import java.util.Arrays;
import java.util.Scanner;

public class InputListener {

    private final Thread t;
    private final Scanner scanner;
    private final GradeCalculator gradeCalculator;
    private boolean running = true;

    public InputListener(GradeCalculator gradeCalculator){
        this.gradeCalculator = gradeCalculator;
        scanner = new Scanner(System.in);
        t = new Thread(this::startListening);
        t.start();
    }

    private void startListening(){
        System.out.println("Started listening for Inputs.");
        while (running){
            if(scanner.hasNext()) {
                String input = scanner.nextLine();
                handleInput(input.split(" "));
            }
            else {
                try {
                    Thread.sleep(10);
                }
                catch (Exception ignored){
                }
            }
        }
        System.out.println("exiting InputListener");
    }

    private void handleInput(String[] s){
//        System.out.println(String.join(" ", s));
        gradeCalculator.getCommandService().onCommand(s);
    }

    public void stop(){
        running = false;
        scanner.close();
        t.interrupt();
    }

}
