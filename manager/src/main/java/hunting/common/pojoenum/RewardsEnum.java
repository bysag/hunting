package hunting.common.pojoenum;


public enum RewardsEnum {
    LU_DIAN("绿点"),DIAN_JUAN("点卷");  
    private String text;
    private RewardsEnum(String text) {
        this.setText(text);
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
