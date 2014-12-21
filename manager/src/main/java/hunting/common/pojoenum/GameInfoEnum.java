package hunting.common.pojoenum;


public enum GameInfoEnum {
    DIG_GOLD("挖金子"),HIDE_AND_SEEK("捉迷藏");
    private String text;
    private GameInfoEnum(String text) {
        this.setText(text);
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
