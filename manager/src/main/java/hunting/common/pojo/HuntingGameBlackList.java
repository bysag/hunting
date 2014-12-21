package hunting.common.pojo;

import hunting.common.pojoenum.BlackListEnum;

import java.io.Serializable;
import java.util.Date;

public class HuntingGameBlackList implements Serializable {

    private static final long serialVersionUID = 6375092069381857558L;

    private long id;// 表主键

    private String playerId;// 玩家编号

    private BlackListEnum type;// 类型:VALID/INVAILD

    private Date createdTime;// 创建时间

    private Date lastModTime;// 最后修改时间

    private String operator;// 操作者

    private String gameCity;// 游戏所在城市

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

    public BlackListEnum getType() {
        return type;
    }

    public void setType(BlackListEnum type) {
        this.type = type;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModTime() {
        return lastModTime;
    }

    public void setLastModTime(Date lastModTime) {
        this.lastModTime = lastModTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getGameCity() {
        return gameCity;
    }

    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }

}
