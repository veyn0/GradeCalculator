package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;
import we.jufrkhma.data.subject.Subject;
import we.jufrkhma.data.user.UserInfo;

import java.util.Collection;
import java.util.UUID;

public class SubjectCommand implements CommandExecutor {

    private final GradeCalculator gradeCalculator;

    public SubjectCommand(GradeCalculator gradeCalculator){
        this.gradeCalculator = gradeCalculator;
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length==0){
            printInvalidArgs();
            return;
        }
        String action = args[0];
        if(action.equalsIgnoreCase("info")){
            if(args.length==1){
                printInvalidArgs();
                return;
            }
            String user = args[1];
            printSubjectInfo(user);
        }
        else if(action.equalsIgnoreCase("create")){
            if(args.length<=2){
                printInvalidArgs();
                return;
            }
            if(args[1].length()>16){
                System.out.println("Failed to create Subject. Name may only be 16 characters in length");
                return;
            }
            String name = args[1];
            float verbalGradeWeight = Float.parseFloat(args[2]);
            Subject created = new Subject(UUID.randomUUID(), name, verbalGradeWeight);
            gradeCalculator.getSubjectRepository().addSubject(created);
            System.out.println("Subject \"" + name + "\" created successfully.");
            printSubjectInfo(created.subjectId().toString());
        }
        else if(action.equalsIgnoreCase("list")){
            Collection<Subject> subjects = gradeCalculator.getSubjectRepository().getSubjects().values();
            if(subjects.isEmpty()){
                System.out.println("There are no subjects created yet");
                return;
            }
            System.out.println();
            for(Subject subject : subjects){
                printSubjectInfo(subject.subjectId().toString());
                System.out.println();
            }
        }

    }



    private void printSubjectInfo(String subjectNameOrUUID){
        Subject currentSubject = gradeCalculator.getSubjectRepository().getSubject(subjectNameOrUUID);

        if(currentSubject==null){
            System.out.println("Subject \"" + subjectNameOrUUID + "\" not found.");
            return;
        }
        System.out.println("Subject info for \"" + currentSubject.name() + "\": \nUUID: " + currentSubject.subjectId() + "\nVerbal Grade weight: " + currentSubject.verbalGradeWeight());
    }

    private void printInvalidArgs(){
        System.out.println("invalid arguments. use \"help subject\" to see a full list of available commands");
    }

    @Override
    public void printHelpPage() {
        System.out.println("""
                subject command info:
                - list : list all saved subjects
                - info <subjectName:String> : list info about specific saved subject
                - create <name:String> <verbalGradeWeight:float> : create Subject with specified parameters
                """);
    }
    
}
