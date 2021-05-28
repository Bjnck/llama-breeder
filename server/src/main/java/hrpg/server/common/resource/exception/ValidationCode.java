package hrpg.server.common.resource.exception;

public enum ValidationCode {
    MAX_SIZE("maxSize"),
    INSUFFICIENT_COINS("insufficientCoins"),
    INVALID_VALUE("invalidValue"),
    CONFLICT("conflict");

    private String code;

    ValidationCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
