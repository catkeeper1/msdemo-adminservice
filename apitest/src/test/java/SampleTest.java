import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Administrator on 2017/8/8.
 */

//skip this so that it will not break the build. uncomment
//        this when need to do auto api test
@Ignore
public class SampleTest {

    @Test
    public void testUserService() {

        UserClient userClient = (UserClient) BeanContainer.getBean(UserClient.class);

        try {
            userClient.queryUserById("ABC");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
