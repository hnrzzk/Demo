package priv.mmap;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;

public class SharedMemoryWriter {
    public static void main(String[] args) {
        try {
            // Open shared memory file
            RandomAccessFile memoryFile = new RandomAccessFile("F:\\shm\\memory", "rw");
            FileChannel channel = memoryFile.getChannel();

            // Create a buffer for writing
            ByteBuffer buffer = ByteBuffer.allocate(4); // 4 bytes per int

            // Write integers to shared memory every 1-2 seconds
            Random random = new Random();
            int count = 0;
            while (true) {
                // Generate a random integer
                int num = random.nextInt(100);

                // Write the integer to the buffer and reset its position
                buffer.putInt(0, num);
                buffer.position(0);

                // Write the buffer to the shared memory
                long start = System.nanoTime();
                int bytesWritten = channel.write(buffer);
                long end = System.nanoTime();
                System.out.printf("Bytes written: %s Value: %s cost:%d %n", bytesWritten, num, (end - start));
                // Increment the count and sleep for 1-2 seconds
                count++;
                Thread.sleep(random.nextInt(1000));

                // Exit loop if 5 integers have been written
//                if (count == 5) {
//                    break;
//                }
            }

            // Close the channel and file
//            channel.close();
//            memoryFile.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
