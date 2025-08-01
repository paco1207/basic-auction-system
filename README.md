# Basic Auction System (SCC311)

This is a basic auction system implemented in Java for a university project (SCC311). The system allows clients to connect to a server and bid on auction items. It includes AES encryption for secure communication.

## 🧠 Features

- Java client-server system using sockets
- AES-encrypted communication
- Auction items can be listed, bid on, and updated
- Includes a key generation tool

## 📁 Project Structure

```
SCC311 Basic Auction System/
├── client/               # Client-side Java code
├── server/               # Server-side Java code
├── keys/                 # AES key file
├── KeyGeneratorTool.java # Tool to generate AES key
├── server.sh             # Script to start the server
├── Part_A.pdf            # Project documentation
```

## 🚀 How to Run

1. Compile all Java files:
   ```bash
   javac server/*.java client/*.java *.java
   ```

2. Run server:
   ```bash
   sh server.sh
   ```

3. Run client:
   ```bash
   java client.Client
   ```

> Java must be installed.

## 📜 License

This project is licensed under the MIT License. See `LICENSE` for details.
