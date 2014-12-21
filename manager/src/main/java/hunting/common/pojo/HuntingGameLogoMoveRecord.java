package hunting.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class HuntingGameLogoMoveRecord implements Serializable {

    private static final long serialVersionUID = -8968717222370263352L;

    private long id;// 表主键

    private long gameInfoId;// 游戏表的主键id

    private String LogoLongtitude;// 标的经度

    private String logoLatitude;// 标的纬度

    private Date createdTime;// 创建时间

    private String gameCity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameInfoId() {
        return gameInfoId;
    }

    public void setGameInfoId(long gameInfoId) {
        this.gameInfoId = gameInfoId;
    }

    public String getLogoLongtitude() {
        return LogoLongtitude;
    }

    public void setLogoLongtitude(String logoLongtitude) {
        LogoLongtitude = logoLongtitude;
    }

    public String getLogoLatitude() {
        return logoLatitude;
    }

    public void setLogoLatitude(String logoLatitude) {
        this.logoLatitude = logoLatitude;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    public String getGameCity() {
        return gameCity;
    }

    
    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }

}
