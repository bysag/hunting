package hunting.common.pojo;

import hunting.common.pojoenum.GameInfoEnum;

import java.io.Serializable;
import java.util.Date;


public class HuntingGameInfo implements Serializable {

    private static final long serialVersionUID = 8724033408229005113L;
    
    private long id ;//表主键
    private GameInfoEnum gameType;//类型:DIG_GOLD/HIDE_AND_SEEK
    private String gameCity;//城市
    private String gameLogoName;//车标名称
    private String gameLogoUri;//车标图标
    private long gameGrabLogoDistance;//抢标距离
    private long gameHideTime;//隐身时间,单位秒
    private long gameHideTimeInvalid;//游戏结束前时间隐身无效
    private Date gameStartDate;//开始日期
    private Date gameEndDate;//结束日期
    private Date gameStartTime;//开始时间
    private Date gameEndTime;//结束时间
    private String gameCenterLongitude;//游戏中心区经度
    private String gameCenterLatitude;//游戏中心区纬度
    private String gameCenterAddress;//游戏中心区地址
    private String gameCenterRadius;//游戏中心区半径
    private String gameLogoPutInPointLongitude;//车标投放点经度
    private String gameLogoPutInPointLatitude;//车标投放点纬度
    private String gameLogoPutInPointAddress;//车标投放地址
    private String gameLogoDisplayRadius;//车标显示半径
    private int gameRobGoldTimes;//挖金子次数
    private long gameMaxMoveSpeed;//最大移动速度
    private long gameMaxGetSpeed;//最大点击速度
    private String gameShareTextTemplate;//分享文本设置
    private Date createdTime;//创建时间
    private Date lastModTime;//最后修改时间
    private String operator;//操作者
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public GameInfoEnum getGameType() {
        return gameType;
    }
    
    public void setGameType(GameInfoEnum gameType) {
        this.gameType = gameType;
    }
    
    public String getGameCity() {
        return gameCity;
    }
    
    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }
    
    public String getGameLogoName() {
        return gameLogoName;
    }
    
    public void setGameLogoName(String gameLogoName) {
        this.gameLogoName = gameLogoName;
    }
    
    public String getGameLogoUri() {
        return gameLogoUri;
    }
    
    public void setGameLogoUri(String gameLogoUri) {
        this.gameLogoUri = gameLogoUri;
    }
    
    public long getGameGrabLogoDistance() {
        return gameGrabLogoDistance;
    }
    
    public void setGameGrabLogoDistance(long gameGrabLogoDistance) {
        this.gameGrabLogoDistance = gameGrabLogoDistance;
    }
    
    public long getGameHideTime() {
        return gameHideTime;
    }
    
    public void setGameHideTime(long gameHideTime) {
        this.gameHideTime = gameHideTime;
    }
    
    public long getGameHideTimeInvalid() {
        return gameHideTimeInvalid;
    }
    
    public void setGameHideTimeInvalid(long gameHideTimeInvalid) {
        this.gameHideTimeInvalid = gameHideTimeInvalid;
    }
    
    public Date getGameStartDate() {
        return gameStartDate;
    }
    
    public void setGameStartDate(Date gameStartDate) {
        this.gameStartDate = gameStartDate;
    }
    
    public Date getGameEndDate() {
        return gameEndDate;
    }
    
    public void setGameEndDate(Date gameEndDate) {
        this.gameEndDate = gameEndDate;
    }
    
    public Date getGameStartTime() {
        return gameStartTime;
    }
    
    public void setGameStartTime(Date gameStartTime) {
        this.gameStartTime = gameStartTime;
    }
    
    public Date getGameEndTime() {
        return gameEndTime;
    }
    
    public void setGameEndTime(Date gameEndTime) {
        this.gameEndTime = gameEndTime;
    }
    
    public String getGameCenterLongitude() {
        return gameCenterLongitude;
    }
    
    public void setGameCenterLongitude(String gameCenterLongitude) {
        this.gameCenterLongitude = gameCenterLongitude;
    }
    
    public String getGameCenterLatitude() {
        return gameCenterLatitude;
    }
    
    public void setGameCenterLatitude(String gameCenterLatitude) {
        this.gameCenterLatitude = gameCenterLatitude;
    }
    
    public String getGameCenterAddress() {
        return gameCenterAddress;
    }
    
    public void setGameCenterAddress(String gameCenterAddress) {
        this.gameCenterAddress = gameCenterAddress;
    }
    
    public String getGameCenterRadius() {
        return gameCenterRadius;
    }
    
    public void setGameCenterRadius(String gameCenterRadius) {
        this.gameCenterRadius = gameCenterRadius;
    }
    
    public String getGameLogoPutInPointLongitude() {
        return gameLogoPutInPointLongitude;
    }
    
    public void setGameLogoPutInPointLongitude(String gameLogoPutInPointLongitude) {
        this.gameLogoPutInPointLongitude = gameLogoPutInPointLongitude;
    }
    
    public String getGameLogoPutInPointLatitude() {
        return gameLogoPutInPointLatitude;
    }
    
    public void setGameLogoPutInPointLatitude(String gameLogoPutInPointLatitude) {
        this.gameLogoPutInPointLatitude = gameLogoPutInPointLatitude;
    }
    
    public String getGameLogoPutInPointAddress() {
        return gameLogoPutInPointAddress;
    }

    
    public void setGameLogoPutInPointAddress(String gameLogoPutInPointAddress) {
        this.gameLogoPutInPointAddress = gameLogoPutInPointAddress;
    }

    public String getGameLogoDisplayRadius() {
        return gameLogoDisplayRadius;
    }
    
    public void setGameLogoDisplayRadius(String gameLogoDisplayRadius) {
        this.gameLogoDisplayRadius = gameLogoDisplayRadius;
    }
    
    public int getGameRobGoldTimes() {
        return gameRobGoldTimes;
    }
    
    public void setGameRobGoldTimes(int gameRobGoldTimes) {
        this.gameRobGoldTimes = gameRobGoldTimes;
    }
    
    public long getGameMaxMoveSpeed() {
        return gameMaxMoveSpeed;
    }
    
    public void setGameMaxMoveSpeed(long gameMaxMoveSpeed) {
        this.gameMaxMoveSpeed = gameMaxMoveSpeed;
    }
    
    public long getGameMaxGetSpeed() {
        return gameMaxGetSpeed;
    }
    
    public void setGameMaxGetSpeed(long gameMaxGetSpeed) {
        this.gameMaxGetSpeed = gameMaxGetSpeed;
    }
    
    public String getGameShareTextTemplate() {
        return gameShareTextTemplate;
    }
    
    public void setGameShareTextTemplate(String gameShareTextTemplate) {
        this.gameShareTextTemplate = gameShareTextTemplate;
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

    
}
