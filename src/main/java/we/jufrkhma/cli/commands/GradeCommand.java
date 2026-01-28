package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;

public class GradeCommand implements CommandExecutor {

    private final GradeCalculator gradeCalculator;

    public GradeCommand(GradeCalculator gradeCalculator) {
        this.gradeCalculator = gradeCalculator;
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length==0){
            printInvalidArgs();
            return;
        }
        String action = args[0];
        if(action.equalsIgnoreCase("create")){
            if(args.length==1);
        }





    }

    private void printInvalidArgs(){
        System.out.println("invalid arguments. use \"help grade\" to see a full list of available commands");
    }

    @Override
    public void printHelpPage() {
        System.out.println("""
                Grade Command Help Page:
                Subcommands:
                - create <username/id> <subjectName/id> <>
                - list
                - info <username/id>
                
                
                
                
                """);
    }
}
