package hrpg.server.creature.service.exception;

import lombok.Getter;

@Getter
public class CreatureInUseException extends Exception{
    private Long id;

    public CreatureInUseException() {
    }

    public CreatureInUseException(Long id) {
        this.id = id;
    }
}
