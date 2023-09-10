import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        int port = 2728;
        try {
            //create a server socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Listening on port " + port);
            while (true) {
                //creare a client socket
                Socket clientSocket = serverSocket.accept();
                //send client requst to our server
                new requestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class requestHandler extends Thread {
        //intialize socket
        private Socket socket;

        public requestHandler(Socket socket) {
            //set client socket to socket
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                //create new buffer reader for client socket requests
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream outputStream = socket.getOutputStream();
                // PrintWriter serverResponse = new PrintWriter(outputStream, true);
                String request = bufferedReader.readLine();
                if (request != null) {
                    //requst first line split with spaces
                    String[] requestParts = request.split(" ");
                    String method = requestParts[0]; //for method
                    String filePath = requestParts[1]; //for file path
                    String[] correctFilepath = filePath.split("\\?"); // get only file name when parameters are available
                    filePath = "htdocs" + correctFilepath[0]; //set htdocs path (root)
                    if (filePath.equals("htdocs/")) {
                        filePath = "htdocs/index.html";
                    }
                    if (method.equals("GET")) { //handle get requests
                        if (filePath.equals("htdocs/add.php")) {
                            String params = correctFilepath[1];
                            phpHandler(filePath, params, outputStream); //for get method php forms
                        } else {
                            htmlHandler(filePath, outputStream); //render html files
                        }
                    } else if (method.equals("POST")) { //handle post method
                        postRequestHandler(bufferedReader, filePath, outputStream);
                    } else { //when path doesn't exist
                        sendResponse(outputStream, "HTTP/1.1 405 Method Not Allowed", "Method Not Allowed");
                    }
                }
                bufferedReader.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void postRequestHandler(BufferedReader bufferedReader, String filePath, OutputStream outputStream)
            throws IOException {
        int contentLength = 0;
        String line;

        // Read the Content-Length header to determine the length of the request body
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
            }
        }

        // Read the request body
        char[] buffer = new char[contentLength];
        String params;
        int bytesRead = 0;
        while (bytesRead < contentLength) {
            int read = bufferedReader.read(buffer, bytesRead, contentLength - bytesRead);
            if (read == -1) {
                break;
            }
            bytesRead += read;
        }
        //get post request parameters
        params = new String(buffer);

        // Respond to the POST request
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n\r\n" +
                "POST request processed successfully.";

        // outputStream.write(response.getBytes());
        phpHandler(filePath, params, outputStream); //execute php file
    }

    private static void htmlHandler(String filePath, OutputStream outputStream) throws IOException {
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            // fileReader object reads the input of the file in the filePath
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            // Created a stringBuilder; s a class in Java that provides a way to efficiently construct and manipulate strings
            StringBuilder fileContent = new StringBuilder();
            String line ;
            while ((line = fileReader.readLine()) != null) {
                // Get all the content in the file by appending each line the file
                fileContent.append(line).append("\n");
            }

            String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + file.length() + "\r\n" +
                    "Connection: close\r\n\r\n";

            outputStream.write(responseHeaders.getBytes());
            outputStream.write(fileContent.toString().getBytes());
            // sendResponse(outputStream,"HTTP/1.1 200 OK","Success");
        } else {
            sendResponse(outputStream, "HTTP/1.1 404 Not Found", "File Not Found");
        }
    }

    private static void phpHandler(String filePath, String params, OutputStream outputStream) throws IOException {
        try {
            // Create a ProcessBuilder to run the PHP interpreter with the script file
            ProcessBuilder processBuilder = new ProcessBuilder("php", filePath, params);

            // Redirect error stream to the output stream
            processBuilder.redirectErrorStream(true);

            // Start the PHP process
            Process process = processBuilder.start();

            // Get the input stream of the PHP process (the output of the script)
            InputStream scriptOutput = process.getInputStream();

            // Create a buffer to read the script output
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Write the HTTP response headers
            String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Connection: close\r\n\r\n";
            outputStream.write(responseHeaders.getBytes());

            // Read and write the script output to the client
            while ((bytesRead = scriptOutput.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // Close the output stream
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(OutputStream outputStream, String statusLine, String message) throws IOException {
        String response = statusLine + "\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + message.length() + "\r\n" +
                "Connection: close\r\n\r\n" +
                message;
        outputStream.write(response.getBytes());
    }
}