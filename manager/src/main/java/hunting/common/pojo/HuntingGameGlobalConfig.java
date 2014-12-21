package hunting.common.pojo;

import hunting.common.pojoenum.GlobalConfigEnum;

import java.io.Serializable;
import java.util.Date;

public class HuntingGameGlobalConfig implements Serializable {

    private static final long serialVersionUID = -5862621069968784090L;

    private long id;// 表主键

    private String configKey;// key

    private String configValue;// 值

    private Date createdTime;// 创建时间

    private GlobalConfigEnum configType;// 类型

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getConfigKey() {
        return configKey;
    }

    
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    
    public String getConfigValue() {
        return configValue;
    }

    
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    
    public Date getCreatedTime() {
        return createdTime;
    }

    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    public GlobalConfigEnum getConfigType() {
        return configType;
    }

    
    public void setConfigType(GlobalConfigEnum configType) {
        this.configType = configType;
    }
    
}
