package commands;

public enum CommandType {
    AUTH,
    AUTH_OK,
    AUTH_ERROR,
    DISCONNECT,

    UPDATE_CATALOG_TREE,
    UPLOAD_FILE,
    DOWNLOAD_FILE,
    CREATE_DIR,

    STATUS_COMMAND
}
