package we.jufrkhma;

import we.jufrkhma.cli.commands.*;
import we.jufrkhma.cli.input.CommandService;
import we.jufrkhma.cli.input.InputListener;
import we.jufrkhma.data.grades.GradesRepository;
import we.jufrkhma.data.subject.SubjectRepository;
import we.jufrkhma.data.user.UserRepository;

public class GradeCalculator {

    private GradesRepository gradesRepository;

    private SubjectRepository subjectRepository;

    private UserRepository userRepository;

    private InputListener inputListener;

    private CommandService commandService;


    private static GradeCalculator gradeCalculator = new GradeCalculator();

    private GradeCalculator(){

    }

    public static GradeCalculator getGradeCalculator() {
        return gradeCalculator;
    }

    public void onStartup(){
        System.out.println("Starting GradeCalculator...");
        commandService = new CommandService();
        inputListener = new InputListener(this);
        userRepository = new UserRepository();
        subjectRepository = new SubjectRepository();
        gradesRepository = new GradesRepository(this);

        initCommands();
        System.out.println("GradeCalculator started.");
    }

    public void onShutDown(){
        System.out.println("Shutting down...");
        inputListener.stop();

        System.out.println("GradeCalculator shut down.");
    }

    public void initCommands(){
        commandService.registerCommand("help", new HelpCommand(this));
        commandService.registerCommand("stop", new StopCommand(this));
        commandService.registerCommand("user", new UserCommand(this));
        commandService.registerCommand("subject", new SubjectCommand(this));
        commandService.registerCommand("grade", new GradeCommand(this));
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public GradesRepository getGradesRepository() {
        return gradesRepository;
    }

    public SubjectRepository getSubjectRepository() {
        return subjectRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
