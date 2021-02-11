package commands.assembly;

import java.io.Serializable;

public class StatusCommandData implements Serializable {
    String sts;

    public StatusCommandData(String sts) {
        this.sts = sts;
    }

    public String getSts() {
        return sts;
    }
}
