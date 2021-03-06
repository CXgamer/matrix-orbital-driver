package api.bitmap;

import api.display.Display;
import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class BitmapData {

    private final byte width, height;

    private boolean[][] data;

    public BitmapData(int width, int height) {
        checkSize(width, height);

        this.width = (byte) width;
        this.height = (byte) height;

        data = new boolean[width][height];
    }

    public BitmapData(@NonNull BufferedImage bufferedImage) {
        if (bufferedImage.getType() != BufferedImage.TYPE_BYTE_BINARY)
            throw new IllegalArgumentException("Only binary buffered images are supported");

        this.width = (byte) bufferedImage.getWidth();
        this.height = (byte) bufferedImage.getHeight();

        data = new boolean[width][height];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                data[x][y] = bufferedImage.getRGB(x, y) == -1;
    }

    public void setOn(int x, int y) {
        data[x][y] = true;
    }

    public void setOff(int x, int y) {
        data[x][y] = false;
    }

    byte[] getData() {
        int maxRange = (int) Math.ceil(width * height / 8F) + 2;
        byte[] output = new byte[maxRange];
        output[0] = width;
        output[1] = height;

        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(0);

        for (int i = 2; i < maxRange; i++) {
            byte byteBuilder = 0;
            for (int bit = 0; bit < 8; bit++) {
                byteBuilder <<= 1;
                byteBuilder |= next(x, y) ? 1 : 0;
            }
            output[i] = byteBuilder;
        }
        return output;
    }

    private boolean next(AtomicInteger x, AtomicInteger y) {
        if (x.incrementAndGet() >= width) {
            if (y.incrementAndGet() >= height)
                return false;
            x.set(0);
        }

        return data[x.get()][y.get()];
    }

    private void checkSize(int width, int height) {
        if (width > Display.WIDTH || height > Display.HEIGHT)
            throw new IllegalArgumentException("The bitmap can be no larger than 192x64.");
    }
}
