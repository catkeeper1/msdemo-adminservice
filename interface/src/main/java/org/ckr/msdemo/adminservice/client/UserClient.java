package org.ckr.msdemo.adminservice.client;

import org.ckr.msdemo.adminservice.spec.UserSpecification;
import org.springframework.cloud.netflix.feign.FeignClient;

import static org.ckr.msdemo.adminservice.client.ServiceNameConstant.ADMIN_SERVICE;

/**
 * Created by Administrator on 2017/8/5.
 */
@FeignClient(ADMIN_SERVICE)
public interface UserClient extends UserSpecification{
}
