package we.jufrkhma.data.subject;

import java.util.UUID;

public record Subject(
   UUID subjectId,
   String name,
   float verbalGradeWeight
) {
}
