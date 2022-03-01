package nettyRpcClient.protocol;

public enum ProtocolConfig {
    DEFAULT_HEADER_SIZE(122)
    ;

    public int value;

    ProtocolConfig(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
