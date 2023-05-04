package priv.mmap;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;

public class SharedMemoryReader {
    public static void main(String[] args) {
        try {
            // Open shared memory file
            RandomAccessFile memoryFile = new RandomAccessFile("F:\\shm\\memory", "r");
            FileChannel channel = memoryFile.getChannel();

            // Create a buffer for reading
            ByteBuffer buffer = ByteBuffer.allocate(5 * 4); // 5 integers

            // Wait for 5 integers to be written
            int count = 0;
            while (true) {
                // Read from shared memory into buffer
                int bytesRead = channel.read(buffer);
                if (bytesRead <= 0) {
                    continue;
                }
                System.out.println("Bytes read: " + bytesRead);

                // Convert buffer to array of integers
                buffer.position(0);
                int[] arr = new int[bytesRead / 4];
                buffer.asIntBuffer().get(arr);

                // Print the integers
                System.out.println(Arrays.toString(arr));

                // Increment the count
                count += arr.length;

                // Clear the buffer
                buffer.clear();
            }

            // Close the channel and file
//            channel.close();
//            memoryFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
