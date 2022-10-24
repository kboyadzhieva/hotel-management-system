package api;

public class ObjectCreator {

    private final FileReader fileReader = new FileReader();
    private final ValueReader valueReader = new ValueReader();

    public <T> T createObject(String fileName, Class<T> valueType) {
        String content = fileReader.readFile(fileName);
        T t = valueReader.readValue(content, valueType);

        return t;
    }
}
