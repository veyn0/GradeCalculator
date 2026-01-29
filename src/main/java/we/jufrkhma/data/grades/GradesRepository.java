package we.jufrkhma.data.grades;

import we.jufrkhma.GradeCalculator;

import java.util.*;

public class GradesRepository {

    private Map<UUID, Grade> grades = new HashMap<>();

    private final GradeCalculator gradeCalculator;

    public GradesRepository(GradeCalculator gradeCalculator){
        this.gradeCalculator = gradeCalculator;
    }

    public List<Grade> getGradesByUser(UUID userId){
        List<Grade> result = new ArrayList<>();
        for(Grade g : grades.values()){
            if(g.userId().equals(userId)) result. add(g);
        }
        return result;
    }

    public List<Grade> getGradesBySubject(UUID subjectId){
        List<Grade> result = new ArrayList<>();
        for(Grade g : grades.values()){
            if(g.subjectId().equals(subjectId)) result. add(g);
        }
        return result;
    }


    public List<Grade> getGradesByUser(String userNameOrId){
        return getGradesByUser(gradeCalculator.getUserRepository().getUserInfo(userNameOrId).userId());
    }

    public List<Grade> getGradesBySubject(String subjectNameOrId){
        return getGradesBySubject(gradeCalculator.getSubjectRepository().getSubject(subjectNameOrId).subjectId());
    }

    public Grade getGrade(UUID gradeId){
        return grades.get(gradeId);
    }

    public void addGrade(Grade grade){
        grades.put(grade.gradeId(), grade);
    }

    public List<Grade> getGrades(){
        return grades.values().stream().toList();
    }

    public void remove(UUID gradeId){
        grades.remove(gradeId);
    }


}
