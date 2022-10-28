package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValueReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readValue(String content, Class<T> valueType) {
        T t = null;

        try {
            t = objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }
}
