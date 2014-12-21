package hunting.common.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

@Service
public class InitCacheManager implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object obj, String arg1)
            throws BeansException {
        if(obj instanceof GameInfoInit) {  
            ((GameInfoInit)obj).cache();
        }  else if (obj instanceof BlackListInit) {
            ((BlackListInit)obj).cache();
        }else if (obj instanceof LogoMoveRecordInit) {
            ((LogoMoveRecordInit)obj).cache();
        }else if (obj instanceof PlayerMoveRecordInit) {
            ((PlayerMoveRecordInit)obj).cache();
        }else if (obj instanceof PlayerHoldLogoInit) {
            ((PlayerHoldLogoInit)obj).cache();
        }
        return obj;
    }

    public Object postProcessBeforeInitialization(Object arg0, String arg1)
            throws BeansException {
        return arg0;
    }

}
