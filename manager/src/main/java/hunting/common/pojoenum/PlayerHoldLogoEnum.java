package hunting.common.pojoenum;


public enum PlayerHoldLogoEnum {
    HOLD("持有"),UNHOLD("丢失");
    private String text;
    private PlayerHoldLogoEnum(String text) {
        this.setText(text);
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
