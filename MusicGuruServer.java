import java.io.*;
import java.net.*;
import java.util.*;

public class MusicGuruServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000); // Listen on port 5000

            System.out.println("Server is running and waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept client connection
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle each client connection in a new thread
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<String> dataset;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.dataset = new ArrayList<>();
        String filePath = "C:\\Users\\Administrator\\Downloads\\dataset.txt"; // Use the correct path separator (\ or /)
        loadDataFromFile(filePath);
    }
    private void loadDataFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataset.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            // Send the date range to the client
            sendDateRange();

            // Read the selected year from the client
            String selectedYear = readSelectedYear();

            // Send a random top ten song for the selected year
            sendRandomTopTenSong(selectedYear);

            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendDateRange() throws IOException {
        Set<String> years = new HashSet<>();
        for (String entry : dataset) {
            String[] parts = entry.split("\t");
            String date = parts[0];
            String year = date.split("/")[2];
            years.add(year);
        }
        String dateRange = String.join("-", years);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(dateRange);
    }

    private String readSelectedYear() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }

    private void sendRandomTopTenSong(String selectedYear) throws IOException {
        List<String> topTenSongs = new ArrayList<>();
        for (String entry : dataset) {
            String[] parts = entry.split("\t");
            String date = parts[0];
            String year = date.split("/")[2];
            if (year.equals(selectedYear)) {
                topTenSongs.add(parts[2]); // Add song name to top ten list
            }
        }

        // Send a random song from the top ten list
        Random random = new Random();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        if (!topTenSongs.isEmpty()) {
            String randomSong = topTenSongs.get(random.nextInt(topTenSongs.size()));
            out.println(randomSong);
        } else {
            out.println("No top ten songs found for the selected year.");
        }
    }
}