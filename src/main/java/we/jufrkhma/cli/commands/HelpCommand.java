package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;

public class HelpCommand implements CommandExecutor {

    private final GradeCalculator gradeCalculator;

    public HelpCommand(GradeCalculator gradeCalculator){
        this.gradeCalculator = gradeCalculator;
    }


    @Override
    public void onCommand(String[] args) {
        if(args.length==0) {
            System.out.println("""
                    this is the help page
                    available commands:
                    - help
                    - stop
                    - user
                    - subject
                    - grade
                    - calc
                    
                    
                    Tipp:
                    Use \"help <command>\" to see available subcommands
                    """);
        }
        else if(args.length==1){
            String command = args[0];
            CommandExecutor executor = gradeCalculator.getCommandService().getCommand(command);
            if(executor!=null){
                executor.printHelpPage();
            }
            else {
                System.out.println("Command \"" + command +"\" not found.");
            }
        }

    }

    @Override
    public void printHelpPage() {
        System.out.println("Use \"help <command>\" to see available subcommands");
    }
}
