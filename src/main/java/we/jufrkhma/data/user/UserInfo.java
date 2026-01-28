package we.jufrkhma.data.user;

import java.util.UUID;

public record UserInfo(
        UUID userId,
        String name
) {
}
