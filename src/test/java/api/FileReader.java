package api;

import com.moonlighthotel.hotelmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

public class FileReader {

    public String readFile(String fileName) {
        String content = null;

        try {
            File file = ResourceUtils.getFile(fileName);
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.format("File '%s' was not found.",
                    fileName.substring(10)), e);
        }
        return content;
    }
}
