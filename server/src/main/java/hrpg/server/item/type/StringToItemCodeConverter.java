package hrpg.server.item.type;

import org.springframework.core.convert.converter.Converter;

public class StringToItemCodeConverter implements Converter<String, ItemCode> {
    @Override
    public ItemCode convert(String source) {
        try {
            return ItemCode.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}