package hunting.common.pojo;

import hunting.common.pojoenum.RewardsEnum;

import java.io.Serializable;
import java.util.Date;

public class HuntingGameRewardsRecord implements Serializable {

    private static final long serialVersionUID = -5067429456569390834L;

    private long id;// 表主键

    private RewardsEnum rewardsType;// 奖品类型:LU_DIAN/DIAN_JUAN

    private float rewardsAmount;// 数量

    private String operator;// 操作者

    private String rewardsId;// 奖品编号

    private String playerId;// 获奖者编号

    private Date createdTime;// 创建时间

    private long gameInfoId;// 游戏信息表Id

    private String playerName;

    private String playerPhone;
    
    private String gameCity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RewardsEnum getRewardsType() {
        return rewardsType;
    }

    public void setRewardsType(RewardsEnum rewardsType) {
        this.rewardsType = rewardsType;
    }

    public float getRewardsAmount() {
        return rewardsAmount;
    }

    public void setRewardsAmount(float rewardsAmount) {
        this.rewardsAmount = rewardsAmount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRewardsId() {
        return rewardsId;
    }

    public void setRewardsId(String rewardsId) {
        this.rewardsId = rewardsId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public long getGameInfoId() {
        return gameInfoId;
    }

    public void setGameInfoId(long gameInfoId) {
        this.gameInfoId = gameInfoId;
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

    
    public String getGameCity() {
        return gameCity;
    }

    
    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }
    

}
