package hrpg.server.item.service.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends Exception{
    private Long id;

    public ItemNotFoundException() {
    }

    public ItemNotFoundException(Long id) {
        this.id = id;
    }
}
