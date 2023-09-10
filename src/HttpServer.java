import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class HttpServer {
    public static void main(String[] args) throws IOException {
        int port = 2728;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new RequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandler extends Thread {
        private Socket socket;
        public RequestHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream outputStream = socket.getOutputStream();
//                PrintWriter serverResponse = new PrintWriter(outputStream, true);
                String request = bufferedReader.readLine();
                if (request != null) {
                    String[] requestParts = request.split(" ");
                    String method = requestParts[0];
                    String filePath = requestParts[1];
                    String[] correctFilepath = filePath.split("\\?");
                    filePath = "htdocs"+correctFilepath[0];
                    if(filePath.equals("htdocs/")){
                        filePath = "htdocs/index.php";
                    }
                    if (method.equals("GET") ) {
                        if (filePath.equals("htdocs/add.php")) {
                            String params = correctFilepath[1];
                            executePhpScript(filePath, params,outputStream);
                        } else {
                            serveFile(filePath, outputStream);
                        }
                    } else if (method.equals("POST")) {
                        handlePostRequest(bufferedReader,filePath,outputStream);
                    } else {
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

    private static void serveFile(String filePath, OutputStream outputStream) throws IOException {
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            if(filePath.endsWith(".php")){
                //Best code to render php files and get the output
            }
            else {
                //fileReader object reads the input of the file in the filePath
                BufferedReader fileReader = new BufferedReader(new FileReader(file));

                //Created a stringBuilder; s a class in Java that provides a way to efficiently construct and manipulate strings
                StringBuilder fileContent = new StringBuilder();
                String line = "";

                while ((line = fileReader.readLine()) != null) {
                    //Get all the content in the file by appending each line the file
                    fileContent.append(line).append("\n");
                }

                String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + file.length() + "\r\n" +
                        "Connection: close\r\n\r\n";

                outputStream.write(responseHeaders.getBytes());
                outputStream.write(fileContent.toString().getBytes());
//            sendResponse(outputStream,"HTTP/1.1 200 OK","Success");
            }
        } else
        {
            sendResponse(outputStream, "HTTP/1.1 404 Not Found", "File Not Found");
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

    private static void executePhpScript(String filePath,String params,OutputStream outputStream) throws IOException {
        try {
            // Create a ProcessBuilder to run the PHP interpreter with the script file
            ProcessBuilder pb = new ProcessBuilder("php", filePath ,params);

            // Redirect error stream to the output stream
            pb.redirectErrorStream(true);

            // Start the PHP process
            Process process = pb.start();

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

            // Wait for the PHP process to complete
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                // Handle PHP execution errors if needed
                // You can send an error response or log the error here
            }

            // Close the output stream
            outputStream.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void handlePostRequest(BufferedReader bufferedReader, String filePath, OutputStream outputStream) throws IOException {
        // Read and process the POST request body
        StringBuilder requestBody = new StringBuilder();
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

        params = new String(buffer);
        System.out.println(params);

        // Convert the request body to a string
        String postBody = new String(buffer);

        // Handle the POST data, you can parse and process it here
        // You might want to parse form data or handle JSON/XML data, depending on your use case

        // Respond to the POST request
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n\r\n" +
                "POST request processed successfully.";

//        outputStream.write(response.getBytes());
        executePhpScript(filePath, params,outputStream);
    }
}