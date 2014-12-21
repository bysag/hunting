package hunting.common.pojoenum;


public enum BlackListEnum {
    VALID("有效"),INVALID("无效");
    private String text;
    private BlackListEnum(String text) {
        this.setText(text);
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
