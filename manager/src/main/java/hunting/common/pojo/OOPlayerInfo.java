package hunting.common.pojo;

import java.io.Serializable;

/**
 * OO平台上玩家的信息
 * 
 * @author yunan.zheng
 * 
 */
public class OOPlayerInfo extends HuntingGamePlayers implements Serializable {

    private static final long serialVersionUID = 3029364447463801268L;

    private String playerId;

    private String playerName;

    private String playerPhone;

    private String playerNickname;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPhone() {
        return playerPhone;
    }

    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone;
    }

    
    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

}
