package org.firefly.core.util;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.stb.STBImage.*;

public class ResourceLoader {

    public static ByteBuffer loadImageToByteBuffer(String file){
        ByteBuffer imageBuffer;
        try {
            imageBuffer = ioResourceToByteBuffer(file, 128 * 128);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);

        if(!stbi_info_from_memory(imageBuffer, w, h, c)){
            throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
        }

        ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, c, 0);
        if(image == null){
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
        }

        return image;
    }

    private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = ResourceLoader.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            )
            {
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = BufferUtil.resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }
}
