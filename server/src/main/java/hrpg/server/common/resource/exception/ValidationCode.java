package hrpg.server.common.resource.exception;

public enum ValidationCode {
    MAX_SIZE("max Size"),
    INSUFFICIENT_COINS("insufficientCoins");

    private String code;

    ValidationCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
