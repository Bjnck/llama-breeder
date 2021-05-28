package hrpg.server.item.service.exception;

import lombok.Getter;

@Getter
public class ItemInUseException extends Exception{
    private Long id;

    public ItemInUseException() {
    }

    public ItemInUseException(Long id) {
        this.id = id;
    }
}
