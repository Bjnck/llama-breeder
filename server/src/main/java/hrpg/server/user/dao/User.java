package hrpg.server.user.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder(toBuilder = true)

@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;
    @Version
    @Builder.Default
    private int version = 0;

    @Indexed(unique = true)
    private String name;

    @Singular
    private List<String> registrationKeys;

    @Builder.Default
    private long coins = 0;
    @Builder.Default
    private int level = 0;

    @Builder.Default
    private boolean capture = false;
}
