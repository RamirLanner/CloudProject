import controller.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.NettyNetwork;

public class ClientApp extends Application {
    private Stage primaryStage;
    private MainViewController viewController;

    @Override
    public void start(Stage stage) throws Exception {
        NettyNetwork network = new NettyNetwork("localhost", 8189);
        Thread connectionThread = new Thread(() -> {
            network.connect();
        });
        connectionThread.setDaemon(true);
        connectionThread.start();

        Thread.sleep(5000);//тайминговая задержка на создание подключения и возврат статуса
        if (network.isConnected()) {
            FXMLLoader mainLoader = new FXMLLoader();
            mainLoader.setLocation(getClass().getResource("view/MainView.fxml"));
            Parent root = mainLoader.load();
            viewController = mainLoader.getController();
            this.primaryStage = stage;
            primaryStage.setScene(new Scene(root));//, 300, 275
            primaryStage.setResizable(false);
            primaryStage.setTitle("Cloud client");
            //траблы с передачей комманд от хэндлера контроллеру и обратно, все объединил через нетворк.
            //это единственное что мне пришло в голову в час ночи
            network.setViewController(viewController);
            network.getHandler().setViewController(viewController);
            viewController.setHandler(network.getHandler());

            Platform.runLater(() -> primaryStage.show());

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

    public static void showErrorWindow(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Network Error");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }
}
