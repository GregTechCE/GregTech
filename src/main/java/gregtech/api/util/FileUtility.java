package gregtech.api.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtility {

    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] streamData = IOUtils.toByteArray(inputStream);
        return new String(streamData, StandardCharsets.UTF_8);
    }

    public static InputStream writeInputStream(String contents) {
        return new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
    }

}
