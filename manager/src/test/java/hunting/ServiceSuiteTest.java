/**
 * 
 */
package hunting;

import hunting.manager.service.BlackListServiceTest;
import hunting.manager.service.GameInfoServiceTest;
import hunting.manager.service.GamePlayerServiceTest;
import hunting.manager.service.GlobalConfigServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * @author yunan.zheng
 *
 */
@RunWith(Suite.class)
@SuiteClasses({BlackListServiceTest.class,GameInfoServiceTest.class,GamePlayerServiceTest.class,GlobalConfigServiceTest.class})
public class ServiceSuiteTest {

}
