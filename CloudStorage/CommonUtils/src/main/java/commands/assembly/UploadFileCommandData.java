package commands.assembly;

import java.io.Serializable;

public class UploadFileCommandData implements Serializable {
    String filename;
    String filepath;
    boolean end;
    int offset;
    int len;
    byte[] buff;

    public UploadFileCommandData(String filename, String filepath, boolean end, int offset, int len, byte[] buff) {
        this.filename = filename;
        this.filepath = filepath;
        this.end = end;
        this.offset = offset;
        this.len = len;
        this.buff = buff;
    }

    public int getLen() {
        return len;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public boolean isEnd() {
        return end;
    }

    public int getOffset() {
        return offset;
    }

    public byte[] getBuff() {
        return buff;
    }
}
