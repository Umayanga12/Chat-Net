import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Main {
    //assigning the port number
    private static final int PORT = 9001;
    //creating the hash set - hash sets get the unique item only
    private static HashSet<String> names = new HashSet<String>();
    //creating the print Writer - allow to users to broadcast  the messages
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running ");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true){
                Socket socket = listener.accept();
                //creating the new thread
                Thread HandlerThread = new Thread(new Handler(socket));
                HandlerThread.start();
            }
        }finally {
            listener.close();
        }
    }

    //creating the handle  - handling the broadcasting massages with single client
    private static class  Handler implements Runnable{
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true){
                    out.println("Submit name : ");
                    name = in.readLine();
                    if (name == null){
                        return;
                    }
                    //if the name that enterd by the user is unique then add it to the hash set
                    if(!names.contains(names)){
                        names.add(name);
                        break;
                    }
                }

                //if name added
                out.println("Name Accepted");
                writers.add(out);

                //if the user is log out then cannot send the message to the user
                while (true){
                    String input = in.readLine();
                    if(input ==null){
                        return;
                    }else {
                        for (PrintWriter writer: writers){
                            writer.println("Message  "+ name+ ":" + input );
                        }
                    }

                }

            } catch (IOException e) {
                System.out.println(e);
            }finally {
                // if the user log out from the application need to remove the related sockets,names and etc
                if (names != null){
                    names.remove(name);
                }

                if (out != null){
                    writers.remove(out);
                }
                try {
                    socket.close();
                }catch (IOException e){
                    System.out.println(e);
                }
            }

        }
    }


}