import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

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

        //getting the server address
        

    }
}
