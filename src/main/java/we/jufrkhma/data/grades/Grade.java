package we.jufrkhma.data.grades;

import java.util.UUID;

public record Grade(
        UUID gradeId,
        UUID userId,
        UUID subjectId,
        int grade,
        boolean isVerbal
) {
}
