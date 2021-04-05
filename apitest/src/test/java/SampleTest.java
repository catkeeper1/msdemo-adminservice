import feign.codec.Decoder;
import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Base64;

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
            UserDetailView result = userClient.queryUserById("ABC");

            System.out.println(result);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
