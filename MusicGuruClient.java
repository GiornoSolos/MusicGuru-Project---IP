import java.io.*;
import java.net.*;

public class MusicGuruClient {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java MusicGuruClient <hostname> <port> <year>");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String selectedYear = args[2];

        try {
            Socket socket = new Socket(hostname, port); // Connect to the server

            // Input and output streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Read and print the date range from the server
            String dateRange = in.readLine();
            System.out.println("Available years: " + dateRange);

            // Send the selected year to the server
            out.println(selectedYear);

            // Read and print the random top ten song from the server
            String randomSong = in.readLine();
            System.out.println("Random top ten song for " + selectedYear + ": " + randomSong);

            // Close the socket when done
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
