package pl.sebastian.ideas100.common.utils;

public enum SortDirectionEnum {
    ASC("asc"),
    DESC("desc");

    private final String reprText;

    private SortDirectionEnum(String reprText) {
        this.reprText = reprText;
    }

    public String getReprText() {
        return this.reprText;
    }
}
