package hunting.common.pojoenum;


public enum GlobalConfigEnum {
    GAME_CITY("游戏城市"), GAME_RULE("游戏规则");
    private String text;
    private GlobalConfigEnum(String text) {
        this.setText(text);
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
