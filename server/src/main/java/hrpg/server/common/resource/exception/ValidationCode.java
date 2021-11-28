package hrpg.server.common.resource.exception;

public enum ValidationCode {
    MAX_SIZE("maxSize"),
    MIN_SIZE("minSize"),
    INSUFFICIENT_COINS("insufficientCoins"),
    INSUFFICIENT_LEVEL("insufficientLevel"),
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
