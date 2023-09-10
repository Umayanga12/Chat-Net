import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
    BufferedReader in ;
    PrintWriter out;
    JFrame frame = new JFrame("ChatNet");
    JTextField textField = new JTextField(40);
    JTextArea textarea = new JTextArea(8,40);

    //constructor of the client class
    public client(){
        //not allow to edit since the sever approve the name is unique
        textField.setEditable(false);
        textarea.setEditable(false);
        frame.getContentPane().add(textField,"North");
        frame.getContentPane().add(new JScrollPane(textarea),"Center");
        frame.pack();

        //creating the action listener to send the message and the message box must  be clear after sending the message
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });

    }

    //getting the server ip address
    private String getserveraddress(){
        return JOptionPane.showInputDialog(
                frame,
                "Enter the Ip Address of the server : ",
                "Welcome to Chat-Net",
                JOptionPane.QUESTION_MESSAGE

        );
    }

    //getting the username from the user
    private String getusername(){
        return JOptionPane.showInputDialog(
                frame,
                "Enter the User name : ",
                "Login detal ",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void run() throws IOException{
        String serverAddress = getserveraddress();
        Socket socket = new Socket(serverAddress, 9001);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true){
            String line = in.readLine();
            
            if (line.startsWith("Username")){
                out.println(getusername());
            } else if (line.startsWith("NAME ACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("Message")) {
                textarea.append(line.substring(8)+ "\n");
                
            }

        }

    }

    public static void main(String[] args) throws Exception{
        client cli = new client();
        cli.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cli.frame.setVisible(true);
        cli.run();
    }


}
