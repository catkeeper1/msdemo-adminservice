package org.ckr.msdemo.adminservice.service;

import org.ckr.msdemo.adminservice.repository.RoleRepository;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JpaRestPaginationService jpaRestPaginationService;


    public void validateRoleInfo(UserServiceForm.RoleServiceForm roleForm) {
    }
}
