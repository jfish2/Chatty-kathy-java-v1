import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleChatServer {

    ArrayList clientOutputStreams;

    public static void main(String[] args) {
        new SimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(5000);

            while(true) {
                Socket clientSock = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSock));
                t.start();
                System.out.println("Connection received!");
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


















    class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
