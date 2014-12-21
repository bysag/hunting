package hunting.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class HuntingGamePlayerMoveRecord implements Serializable {

    private static final long serialVersionUID = 1462143501268753712L;

    private long id;// 表主键

    private String playerId;// 玩家编号

    private String moveLongtitude;// 移动的经度

    private String moveLatitude;// 移动的纬度

    private Date created;// 创建时间

    private long gameInfoId;// 游戏信息表Id

    private String gameCity;

    private double dist;

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

    public String getMoveLongtitude() {
        return moveLongtitude;
    }

    public void setMoveLongtitude(String moveLongtitude) {
        this.moveLongtitude = moveLongtitude;
    }

    public String getMoveLatitude() {
        return moveLatitude;
    }

    public void setMoveLatitude(String moveLatitude) {
        this.moveLatitude = moveLatitude;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getGameInfoId() {
        return gameInfoId;
    }

    public void setGameInfoId(long gameInfoId) {
        this.gameInfoId = gameInfoId;
    }

    public String getGameCity() {
        return gameCity;
    }

    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

}
