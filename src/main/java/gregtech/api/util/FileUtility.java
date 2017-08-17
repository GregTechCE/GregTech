package gregtech.api.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtility {

    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] streamData = sun.misc.IOUtils.readFully(inputStream, -1, false);
        return new String(streamData, StandardCharsets.UTF_8);
    }

    public static InputStream writeInputStream(String contents) {
        return new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
    }

}
