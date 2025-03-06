package lt.restservice.bl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThumbnailGenerator {

    public static byte[] createThumbnail(byte[] file) throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(file);
        BufferedImage bufferedImage = ImageIO.read(bais);

        final int targetWidth = 300;

        // every thumbnail has unified format
        final String thumbnailFormat = "jpg";

        BufferedImage thumbnail = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumbnail, thumbnailFormat, baos);

        return baos.toByteArray();
    }
}
