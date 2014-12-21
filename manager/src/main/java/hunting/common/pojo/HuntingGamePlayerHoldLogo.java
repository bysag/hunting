package hunting.common.pojo;

import hunting.common.pojoenum.PlayerHoldLogoEnum;

import java.io.Serializable;
import java.util.Date;


public class HuntingGamePlayerHoldLogo implements Serializable {

    private static final long serialVersionUID = -7661932313096763480L;
    
    private long id;//表主键
    private String playerId;//玩家编号
    private Date holdLogoStartTime;//玩家持有时间点
    private Date holdLogoEndTime;//玩家丢下时间点
    private long gameInfoId;//游戏信息表Id    
    private PlayerHoldLogoEnum holdType;//车标持有者
    private String gameCity;//
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
    
    public Date getHoldLogoStartTime() {
        return holdLogoStartTime;
    }
    
    public void setHoldLogoStartTime(Date holdLogoStartTime) {
        this.holdLogoStartTime = holdLogoStartTime;
    }
    
    public Date getHoldLogoEndTime() {
        return holdLogoEndTime;
    }
    
    public void setHoldLogoEndTime(Date holdLogoEndTime) {
        this.holdLogoEndTime = holdLogoEndTime;
    }
    
    public long getGameInfoId() {
        return gameInfoId;
    }
    
    public void setGameInfoId(long gameInfoId) {
        this.gameInfoId = gameInfoId;
    }

    
    public PlayerHoldLogoEnum getHoldType() {
        return holdType;
    }

    
    public void setHoldType(PlayerHoldLogoEnum holdType) {
        this.holdType = holdType;
    }

    
    public String getGameCity() {
        return gameCity;
    }

    
    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }

    
}
