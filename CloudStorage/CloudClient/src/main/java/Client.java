import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static ObjectEncoderOutputStream os;
    private static ObjectDecoderInputStream is;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8189);
            os = new ObjectEncoderOutputStream(socket.getOutputStream());
            is = new ObjectDecoderInputStream(socket.getInputStream());
            new Thread(() -> {
                boolean status=true;
                while (status) {
                    String str= in.nextLine();
                    if(str.equals("close")){
                        status=false;
                        in.close();
                    }
                    try {
                        os.writeObject(str);
                        os.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
