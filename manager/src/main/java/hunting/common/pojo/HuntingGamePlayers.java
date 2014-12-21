package hunting.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class HuntingGamePlayers implements Serializable {

    private static final long serialVersionUID = 8936036706708262271L;

    private long id;// 表主键

    private String playerId;// 玩家编号

    private long gameInfoId;// 游戏信息表Id

    private Date createdTime;// 创建时间

    private Date lastModTime;// 最后修改时间

    private boolean online;// 是否在线

    private String longitude;//经度
    
    private String latitude;//维度
    
    private String gameCity;
    
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

    public long getGameInfoId() {
        return gameInfoId;
    }

    public void setGameInfoId(long gameInfoId) {
        this.gameInfoId = gameInfoId;
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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    
    public String getLongitude() {
        return longitude;
    }

    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    
    public String getLatitude() {
        return latitude;
    }

    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    
    public String getGameCity() {
        return gameCity;
    }

    
    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }
    

}
