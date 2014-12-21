/**
 * 
 */
package hunting;

import hunting.common.oo.CurrentAccountFacadeTest;
import hunting.common.oo.PlayerInfoFacadeTest;
import hunting.common.oo.RewardsFacadeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author yunan.zheng
 *
 */
@RunWith(Suite.class)
@SuiteClasses({CurrentAccountFacadeTest.class,PlayerInfoFacadeTest.class,RewardsFacadeTest.class})
public class OoSuiteTest {

}
