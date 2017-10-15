package org.ckr.msdemo.adminservice;


import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.exception.util.ExceptionTestUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


import static org.assertj.core.api.Assertions.fail;

/**
 * Created by Administrator on 2017/10/15.
 */
public class TestUtil {

    private static MessageSource messageSource = null;

    public static MessageSource getMessageSource() {

        if(messageSource != null) {
            return messageSource;
        }

        ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();

        msgSource.setBasenames("classpath:/messages/SysMessage",
                               "classpath:/messages/SecurityMessage");

        msgSource.setCacheSeconds(-1);
        msgSource.setUseCodeAsDefaultMessage(true);

        messageSource = msgSource;

        return messageSource;
    }

    public static void checkErrorMsg(ApplicationException appExp,
                                     String expectedMsgCode,
                                     String expectedMessage) {

        boolean msgExist = ExceptionTestUtil.checkErrorMsg(getMessageSource(),
                                                           appExp,
                                                           expectedMsgCode,
                                                           expectedMessage);

        if(!msgExist) {
            fail("Cannot find error message. Expected message code = " +
                    expectedMsgCode +
                    ". Expected message = " +
                    expectedMessage
            );
        }

    }
}
