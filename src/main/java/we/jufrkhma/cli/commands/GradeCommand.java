package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;
import we.jufrkhma.data.grades.Grade;
import we.jufrkhma.data.grades.GradesRepository;
import we.jufrkhma.data.subject.Subject;
import we.jufrkhma.data.user.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            if(args.length<=4){
                printInvalidArgs();
                return;
            }
            UserInfo user = gradeCalculator.getUserRepository().getUserInfo(args[1]);
            Subject subject = gradeCalculator.getSubjectRepository().getSubject(args[2]);
            String isVerbal = args[3];
            String grade = args[4];
            handleGradeCreate(user, subject, isVerbal, grade);
        }
        else  if(action.equalsIgnoreCase("list")){
            for(Grade g : gradeCalculator.getGradesRepository().getGrades()){
                printGradeInfo(g);
            }
        }
        else if(action.equalsIgnoreCase("info")){
            if(args.length!=2){
                printInvalidArgs();
                return;
            }
            for(Grade g : gradeCalculator.getGradesRepository().getGradesByUser(args[1])){
                printGradeInfo(g);
            }
        }
        else if(action.equalsIgnoreCase("delete")){
            if(args.length==2){
                try {
                    Grade grade = gradeCalculator.getGradesRepository().getGrade(UUID.fromString(args[2]));
                    gradeCalculator.getGradesRepository().remove(grade.gradeId());
                    System.out.println("Removed");
                } catch (Exception e){
                    System.out.println("Grade not found");
                    return;
                }
                return;
            }
            if(args.length==3){
                UserInfo userInfo = gradeCalculator.getUserRepository().getUserInfo(args[2]);
                if(userInfo==null){
                    System.out.println("User not found");
                    return;
                }
                Subject subject = gradeCalculator.getSubjectRepository().getSubject(args[3]);
                if(subject==null){
                    System.out.println("Subject not found");
                    return;
                }
                List<Grade> userGrades = gradeCalculator.getGradesRepository().getGradesByUser(userInfo.userId());
                List<Grade> userSubjectGrades = new ArrayList<>();
                for(Grade g : userGrades){
                    if(g.subjectId()==subject.subjectId()) userSubjectGrades.add(g);
                }
                System.out.println("Found "+ userSubjectGrades.size() + " Matching grades");
                for(Grade g : userSubjectGrades){
                    gradeCalculator.getGradesRepository().remove(g.gradeId());
                    System.out.println("Removed " + g.grade());
                }
                return;
            }
        }

    }



    private void handleGradeCreate(UserInfo userInfo, Subject subject, String isVerbalAsString, String gradeAsString){
        if(userInfo==null){
            System.out.println("Invalid User");
            return;
        }
        if(subject==null){
            System.out.println("Invalid subject");
            return;
        }
        boolean isVerbal = Boolean.parseBoolean(isVerbalAsString);;

        int grade = 0;
        try {
            grade = Integer.parseInt(gradeAsString);
        }
        catch (Exception e){
            System.out.println("Error Parsing grade");
            return;
        }

        Grade result = new Grade(UUID.randomUUID(), userInfo.userId(), subject.subjectId(), grade, isVerbal);
        gradeCalculator.getGradesRepository().addGrade(result);
        printGradeInfo(result);
    }

    private void printInvalidArgs(){
        System.out.println("invalid arguments. use \"help grade\" to see a full list of available commands");
    }


    private void printGradeInfo(Grade grade){
        UserInfo userInfo = gradeCalculator.getUserRepository().getUserInfo(grade.userId());

        if(userInfo==null){
            System.out.println("Error: User not found");
            return;
        }

        Subject subject = gradeCalculator.getSubjectRepository().getSubject(grade.subjectId());

        if(subject==null){
            System.out.println("Error: Subject not found");
            return;
        }

        System.out.println("\nGrade Info (" + grade.gradeId() + "):");
        System.out.println("User: " + userInfo.name() + " (" + userInfo.userId() + ")");
        System.out.println("Subject: " + subject.name() + " (" + subject.subjectId() + ")");
        System.out.println("isVerbal: " + grade.isVerbal());
        System.out.println("Grade: " + grade.grade() + "\n");

    }

    @Override
    public void printHelpPage() {
        System.out.println("""
                Grade Command Help Page:
                Subcommands:
                - create <userName/id> <subjectName/id> <isVerbal> <grade>
                - list
                - info <userName/id>
                - delete
                  - <userName/id> <subjectName/id>
                  - <gradeId>
                
                
                """);
    }
}
