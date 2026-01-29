package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;
import we.jufrkhma.data.grades.Grade;
import we.jufrkhma.data.subject.Subject;
import we.jufrkhma.data.user.UserInfo;

import java.util.List;
import java.util.Map;

public class CalcCommand implements CommandExecutor {

    private final GradeCalculator gradeCalculator;

    public CalcCommand(GradeCalculator gradeCalculator) {
        this.gradeCalculator = gradeCalculator;
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length==0){
            printInvalidArgs();
            return;
        }
        UserInfo userInfo = gradeCalculator.getUserRepository().getUserInfo(args[0]);
        if(userInfo==null){
            System.out.println("User not found");
            return;
        }
        if(args.length==1){
            Map<Subject, List<Grade>> gradesBySubject = gradeCalculator.getUserRepository().getSubjectsWithGradesForUser(userInfo.userId().toString());
            int sum = 0;
            for(Subject s : gradesBySubject.keySet()){
                List<Grade> grades = gradesBySubject.get(s);
                List<Grade> verbalG = gradeCalculator.getCalculator().getVerbalGrades(grades);
                List<Grade> nonVerbalG = gradeCalculator.getCalculator().getNonverbalGrades(grades);
                int grade = gradeCalculator.getCalculator().getAdjustedGrade(verbalG, nonVerbalG, s.verbalGradeWeight());
                System.out.println("Average of User " + userInfo.name() + " in the subject " + s.name()+  " is : " + grade);
                sum+=grade;
            }

            System.out.println("Total Average is: " + sum/gradesBySubject.size() );

            return;

        }
        if(args.length==2){
            Subject subject = gradeCalculator.getSubjectRepository().getSubject(args[1]);
            if(subject==null){
                System.out.println("Subject not found");
                return;
            }
            List<Grade> grades = gradeCalculator.getUserRepository().getGrades(userInfo.name(), subject.name());
            List<Grade> verbalG = gradeCalculator.getCalculator().getVerbalGrades(grades);
            List<Grade> nonVerbalG = gradeCalculator.getCalculator().getNonverbalGrades(grades);
            System.out.println("Average of User " + userInfo.name() + " in the subject " + subject.name()+  " is : " + gradeCalculator.getCalculator().getAdjustedGrade(verbalG, nonVerbalG, subject.verbalGradeWeight()));

        }
    }




    private void printInvalidArgs(){
        System.out.println("invalid arguments. use \"help calc\" to see a full list of available commands");
    }

    @Override
    public void printHelpPage() {
        System.out.println("""
                Calc Help Page
                Sub commands:
                - <userName/id> <(optional)subjectName/id>
                """);
    }
}
