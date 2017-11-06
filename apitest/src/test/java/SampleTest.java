import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.junit.Test;

/**
 * Created by Administrator on 2017/8/8.
 */


public class SampleTest {

    @Test
    public void testUserService() {

        UserClient userClient = (UserClient) BeanContainer.getBean(UserClient.class);

        try {
            userClient.getUser("ABC");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
