package we.jufrkhma.data.subject;

import we.jufrkhma.data.user.UserInfo;

import java.util.*;

public class SubjectRepository {

    private Map<UUID, Subject> subjects = new HashMap<>();

    public Subject getSubject(UUID subjectId){
        return subjects.get(subjectId);
    }

    public Subject getSubject(String subjectNameOrUUID){
        if(subjectNameOrUUID.length()>16) return subjects.get(UUID.fromString(subjectNameOrUUID));

        for(Subject subject : subjects.values()){
            if(subject.name().equalsIgnoreCase(subjectNameOrUUID)){
                return subject;
            }
        }

        return null;
    }

    public void addSubject(Subject subject){
        subjects.put(subject.subjectId(), subject);
    }

    public Map<UUID, Subject> getSubjects() {
        return subjects;
    }

    public void remove(UUID subjectId){
        subjects.remove(subjectId);
    }

}
