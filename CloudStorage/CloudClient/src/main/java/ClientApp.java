import commands.Command;
import controller.MainViewController;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.NettyNetwork;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp extends Application {
    private Stage primaryStage;
    private Stage authDialogStage;
    private MainViewController viewController;

//    private static ObjectEncoderOutputStream os;
//    private static ObjectDecoderInputStream is;

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        try {
//            Socket socket = new Socket("localhost", 8189);
//            os = new ObjectEncoderOutputStream(socket.getOutputStream());
//            is = new ObjectDecoderInputStream(socket.getInputStream());
//            new Thread(() -> {
//                boolean status=true;
//                while (status) {
//                    String str= in.nextLine();
//                    if(str.equals("close")){
//                        status=false;
//                        in.close();
//                    }
//                    try {
//                        os.writeObject(Command.authCommand());
//                        os.flush();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                }
//            }).start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public void start(Stage stage) throws Exception {
        //создаем окно чата оно является основным но мы его не показываем до того как будет выполнена авторизация
//        FXMLLoader mainLoader = new FXMLLoader();
//        mainLoader.setLocation(ClientApp.class.getResource("MainView.fxml"));
//        Parent root = mainLoader.load();


        //MainViewController controller = mainLoader.getController();
        //viewController = mainLoader.getController();

        NettyNetwork network = new NettyNetwork("localhost", 8189);
        Thread connectionThread = new Thread(() -> {
            network.connect();
        });
        connectionThread.setDaemon(true);
        connectionThread.start();

        Thread.sleep(5000);//тайминговая задержка на создание подключения и возврат статуса
        if (network.isConnected()) {
            Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
            this.primaryStage = stage;
            primaryStage.setScene(new Scene(root));//, 300, 275
            primaryStage.setResizable(false);
            primaryStage.setTitle("Cloud client");

            Platform.runLater(() -> primaryStage.show());
            //MainViewController mainDialogController = mainLoader.getController();

            //authWindowStart(primaryStage);//вызываем авторизацию
            //mainDialogController.setNetwork(network);
            primaryStage.setOnCloseRequest(event -> {
                network.close();
            });//при закрытии окна закрываем сокет
        }
        else {
            showErrorWindow("", "Ошибка подключения к серверу");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

//    public Window getPrimaryStage() {
//        return primaryStage;
//    }

    public static void showErrorWindow(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Network Error");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }
}
