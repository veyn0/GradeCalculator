package we.jufrkhma.cli.input;

public interface CommandExecutor {

    void onCommand(String[] args);

    void printHelpPage();
}
