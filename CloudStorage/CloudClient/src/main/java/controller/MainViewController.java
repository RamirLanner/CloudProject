package controller;

import commands.Command;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import model.ClientHandler;

import java.io.File;

public class MainViewController {
    public TextField loginTextField;
    public PasswordField passwordTextField;
    public Button authButton;
    public Label usernameLabel;
    public Label statusLabel;
    public TreeView userThreeView;
    ClientHandler handler;

    public void downloadButtonHandler(ActionEvent actionEvent) {
        userCatalogThree();
        System.out.println("hkgkgkkfkdf");
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }

    public void authButtonHandler(ActionEvent actionEvent) {
        if(loginTextField.getText()!=null && passwordTextField.getText()!=null){
            handler.sendMessageToServer(Command.authCommand(loginTextField.getText(),passwordTextField.getText()));
        }
    }

    public void setUsernameLabel(String username){
        Platform.runLater(() -> usernameLabel.setText("Hello, "+ username+"!"));
    }

    public void setStatusLabel(String status){
        Platform.runLater(() -> statusLabel.setText(status));
    }

    private void userCatalogThree(){
        //File choice = new File(System.getProperty("user.home"));
        File choice = new File("E:\\01_JAVA_PROJECTS");
        // E:\01_JAVA_PROJECTS\Part2\CloudProject\_ClientDir
        Platform.runLater(()->userThreeView.setRoot(getNodesForDirectory(choice)));

//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        File choice = directoryChooser.showDialog(primaryStage);
//        if(choice == null || ! choice.isDirectory()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Could not open directory");
//            alert.setContentText("The file is invalid.");
//
//            alert.showAndWait();
//        } else {
//            userThreeView.setRoot(getNodesForDirectory(choice));
//        }
    }

    public TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<>(directory.getName());
        for(File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if(f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }


}
