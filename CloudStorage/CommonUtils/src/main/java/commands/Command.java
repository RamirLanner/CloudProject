package commands;

import commands.assembly.*;

import java.io.Serializable;

public class Command implements Serializable {
    private CommandType type;
    private Object data;

    public CommandType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public static Command authCommand(String login, String password){
        Command command =new Command();
        command.type = CommandType.AUTH;
        command.data = new AuthCommandData(login, password);
        return command;
    }
    public static Command authErrorCommand(){
        Command command =new Command();
        command.type = CommandType.AUTH_ERROR;
        command.data = new AuthErrorCommandData();
        return command;
    }
    public static Command authOkCommand(String userName){
        Command command =new Command();
        command.type = CommandType.AUTH_OK;
        command.data = new AuthOkCommandData(userName);
        return command;
    }
    public static Command createDirCommand(){
        Command command =new Command();
        command.type = CommandType.CREATE_DIR;
        command.data = new CreateDirCommandData();
        return command;
    }
    public static Command disconnectCommand(){
        Command command =new Command();
        command.type = CommandType.DISCONNECT;
        command.data = new DisconnectCommandData();
        return command;
    }
    public static Command downloadFileCommand(){
        Command command =new Command();
        command.type = CommandType.DOWNLOAD_FILE;
        command.data = new DownloadFileCommandData();
        return command;
    }
    public static Command uploadFileCommand(){
        Command command =new Command();
        command.type = CommandType.UPLOAD_FILE;
        command.data = new UploadFileCommandData();
        return command;
    }
    public static Command updateCatalogTreeCommand(){
        Command command =new Command();
        command.type = CommandType.UPDATE_CATALOG_TREE;
        command.data = new UpdateCatalogTreeCommandData();
        return command;
    }
    public static Command statusCommand(){
        Command command =new Command();
        command.type = CommandType.STATUS_COMMAND;
        command.data = new StatusCommandData();
        return command;
    }
    public static Command packetCommand(){
        Command command =new Command();
        command.type = CommandType.PACKET;
        command.data = new PacketCommandData();
        return command;
    }

}
