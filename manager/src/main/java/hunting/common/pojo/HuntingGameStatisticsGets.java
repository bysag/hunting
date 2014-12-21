package hunting.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class HuntingGameStatisticsGets implements Serializable {

    private static final long serialVersionUID = 5688438551640795112L;

    private long id;

    private String gameCity;

    private String playerId;

    private int gets;

    private int sort;

    private Date createdTime;

    private Date sortDate;

    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameCity() {
        return gameCity;
    }

    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getGets() {
        return gets;
    }

    public void setGets(int gets) {
        this.gets = gets;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }
    
    

}
