package hrpg.server.creature.service.exception;

import lombok.Getter;

@Getter
public class CreatureNotFoundException extends Exception {
    private Long id;

    public CreatureNotFoundException() {
    }

    public CreatureNotFoundException(Long id) {
        this.id = id;
    }
}
