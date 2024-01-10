# Simple HTTP Server Using Java 

This is a simple HTTP server implemented in Java that serves HTML and PHP files. It can handle both GET and POST requests and execute PHP scripts.

## Prerequisites

Before running the server, ensure you have the following:

- Java Development Kit ([JDK](https://www.oracle.com/java/technologies/downloads/)) installed.
- [PHP](https://www.php.net/) installed and added to your system's PATH (for PHP script execution).
- Or else the php directory of your own wamp/mamp/xampp server should be given instead of php in line `141` of the code.
```java
ProcessBuilder processBuilder = new ProcessBuilder("php", filePath, params);
```

## How to run?

1. Clone or download this repository to your local machine.

2. Open a terminal or command prompt and navigate to the project directory.

3. Compile the `HttpServer.java` file:
```bash
javac HttpServer.java
```

4. Run the server:
```bash
java HttpServer
```

5. The server will start listening on port `2728`. You can access it in a web browser.`http://localhost:2728/`


## Project Structure

- [HttpServer.java](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/blob/main/src/HttpServer.java): The main Java server program.
- [add.php](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/blob/main/htdocs/add.php): A sample PHP script that performs addition based on POST or GET parameters.
- [index.html](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/blob/main/htdocs/index.html): A simple HTML form for submitting numbers to the [add.php](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/blob/main/htdocs/add.php) script.

## Features

- Handles GET and POST requests.
- Serves static HTML files from the [htdocs](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/tree/main/htdocs) directory.
- Executes PHP scripts and passes parameters.

## Configuration

You can modify the server's behavior by editing the [HttpServer.java](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/blob/main/src/HttpServer.java) file. For example, you can change the port number, the root directory for HTML files ([htdocs](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/tree/main/htdocs)), or add more handling logic for different HTTP methods or file types. Any other resources that you wish to include can be added to the [htdocs](https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/tree/main/htdocs) folder as well.

## License

This project is licensed under the GNU License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

This project was created as a simple example of a Java-based HTTP server with PHP support. It can be used as a starting point for more complex web server applications or educational purposes.



<p align="center">
   <a href="https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/blob/main/LICENSE">
      <img alt="License: GNU" src="https://img.shields.io/badge/License-GPLv3-blue.svg">
   </a>
    <a href="https://github.com/Buddhikanip/Simple-Http-Server-Using-Java">
      <img alt="Hits" src="https://hits.sh/github.com/Buddhikanip/Simple-Http-Server-Using-Java.svg?label=Views"/>
    </a>
    <a href="https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/actions">
      <img alt="Tests Passing" src="https://github.com/anuraghazra/github-readme-stats/workflows/Test/badge.svg" />
    </a>
    <a href="https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/graphs/contributors">
      <img alt="GitHub Contributors" src="https://img.shields.io/github/contributors/Buddhikanip/Simple-Http-Server-Using-Java" />
    </a>
    <a href="https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/issues">
      <img alt="Issues" src="https://img.shields.io/github/issues/Buddhikanip/Simple-Http-Server-Using-Java?color=0088ff" />
    </a>
    <a href="https://github.com/Buddhikanip/Simple-Http-Server-Using-Java/pulls">
      <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/Buddhikanip/Simple-Http-Server-Using-Java?color=0088ff" />
    </a>
  </p>
