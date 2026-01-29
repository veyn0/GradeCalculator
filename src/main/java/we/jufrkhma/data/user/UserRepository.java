package we.jufrkhma.data.user;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.data.grades.Grade;
import we.jufrkhma.data.subject.Subject;

import java.util.*;

public class UserRepository {
    private final GradeCalculator gradeCalculator;

    public UserRepository(GradeCalculator gradeCalculator) {
        this.gradeCalculator = gradeCalculator;
    }

    private Map<UUID, UserInfo> userData = new HashMap<>();

    public UserInfo getUserInfo(UUID userId){
        return  userData.get(userId);
    }

    public UserInfo getUserInfo(String userNameOrUUID){
        if(userNameOrUUID.length()>16) return userData.get(UUID.fromString(userNameOrUUID));

        for(UserInfo info : userData.values()){
            if(info.name().equalsIgnoreCase(userNameOrUUID)){
                return info;
            }
        }
        return null;
    }


    public void addUser(UserInfo userInfo){
        userData.put(userInfo.userId(), userInfo);
    }

    public Map<UUID, UserInfo> getUserData() {
        return userData;
    }

    public void remove(UUID userId){
        userData.remove(userId);
    }

    public List<Grade> getGrades(String userNameOrId, String subjectNameOrId){
        UserInfo user = this.getUserInfo(userNameOrId);

        Subject subject = gradeCalculator.getSubjectRepository().getSubject(subjectNameOrId);

        return getSubjectsWithGradesForUser(user.userId().toString()).get(subject);
    }

    public Map<Subject, List<Grade>> getSubjectsWithGradesForUser(String userNameOrId) {
        UUID userId = gradeCalculator.getUserRepository().getUserInfo(userNameOrId).userId();

        List<Grade> userGrades = gradeCalculator.getGradesRepository().getGradesByUser(userId);

        Map<UUID, List<Grade>> gradesBySubjectId = new HashMap<>();

        for (Grade grade : userGrades) {
            UUID subjectId = grade.subjectId();

            List<Grade> gradesForSubject = gradesBySubjectId.get(subjectId);
            if (gradesForSubject == null) {
                gradesForSubject = new ArrayList<>();
                gradesBySubjectId.put(subjectId, gradesForSubject);
            }

            gradesForSubject.add(grade);
        }

        Map<Subject, List<Grade>> result = new HashMap<>();

        for (Map.Entry<UUID, List<Grade>> entry : gradesBySubjectId.entrySet()) {
            UUID subjectId = entry.getKey();
            List<Grade> grades = entry.getValue();

            Subject subject = gradeCalculator.getSubjectRepository().getSubject(subjectId);
            if (subject != null) {
                result.put(subject, grades);
            }
        }

        return result;
    }
}
