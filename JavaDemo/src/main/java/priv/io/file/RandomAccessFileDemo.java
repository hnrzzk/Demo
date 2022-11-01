package priv.io.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;

public class RandomAccessFileDemo {
    private static RandomAccessFile randomAccessFile;
    public static void main(String[] args) {
        try {
            randomAccessFile = new RandomAccessFile("src/main/resources/data/RandomAccessFileTestData.txt", "rw");
            randomAccessFile.seek(0);
            randomAccessFile.write("张凯\n".getBytes());
            randomAccessFile.write("张凯\n".getBytes());
            randomAccessFile.seek(3);
            randomAccessFile.writeBytes("a");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
