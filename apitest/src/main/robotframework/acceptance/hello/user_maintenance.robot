*** Settings ***
Library   org.ckr.msdemo.adminservice.apitest.keywords.UserKeyword

*** Variables ***

*** Test Cases ***
create user
    Query User    ABC
    Create User     haiyan  haiyan lu   haiyangpassword     true

    Update Role To User     haiyan   SECURITY_ADMIN
    Query User    haiyan
    Delete User     haiyan
