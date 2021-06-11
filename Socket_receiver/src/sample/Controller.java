package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
String message2= "null";
    @FXML
    TextField Message;
    @FXML
    TextField Signature;


    public void GetData(ActionEvent actionEvent) throws IOException {
       // Controller t1=new Controller();
       // t1.start();

        String[] tokens = message2.split(",");
        BigInteger check ;
        BigInteger mod2 = new BigInteger(String.valueOf(tokens[0]));
        BigInteger pow = new BigInteger(tokens[1]);
        BigInteger mod = new BigInteger(tokens[2]);
        BigInteger sign = new BigInteger(tokens[4]);
        System.out.println(sign);
        check= mod2.modPow(pow,mod);
          if(check.equals(sign)){
            Message.setText(tokens[3]);
          Signature.setText(String.valueOf(sign));
        }

        System.out.println(check);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("thread is running...");
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ServerSocket awaiting connections...");
        Socket socket = null; // blocking call, this will wait until a connection is attempted on this port.
        try {
            socket = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection from " + socket + "!");

        // get the input stream from the connected socket
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create a DataInputStream so we can read data from it.
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        // read the message from the socket
        String message = null;
        try {
            message = dataInputStream.readUTF();
            message2=message;
            System.out.println(message2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The message sent from the socket was: " + message);

        System.out.println("Closing sockets.");
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
