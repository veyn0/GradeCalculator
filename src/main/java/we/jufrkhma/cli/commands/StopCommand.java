package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;

public class StopCommand implements CommandExecutor {

    private final GradeCalculator gradeCalculator;

    public StopCommand(GradeCalculator gradeCalculator) {
        this.gradeCalculator = gradeCalculator;
    }

    @Override
    public void onCommand(String[] args) {
        gradeCalculator.onShutDown();
    }

    @Override
    public void printHelpPage() {
        System.out.println("This command does not have subcommands");
    }
}
