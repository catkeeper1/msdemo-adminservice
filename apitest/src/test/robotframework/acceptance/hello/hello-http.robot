*** Settings ***
Library    HttpRequestLibrary
Library   org.ckr.msdemo.adminservice.apitest.keywords.UserService

*** Variables ***
${HOST}         http://localhost:8082/admin_service/

*** Test Cases ***

#HTTP1
#    Create Session  local   ${HOST}
#    ${resp}=  Get Request  local  /
#    Should Be Equal As Strings  ${resp.status_code}  200
#
#
#HTTP2
#    [documentation]  http://localhost:8082/admin_service/user/ABC
#    Create Session  local   ${HOST}
#    ${resp}=     Get Request  local  /user/ABC
#    Should Be Equal As Strings  ${resp.status_code}  200
#    ${jsonresp}=    Pretty Print Json   ${resp}
#    LOG     ${jsonresp} HTML

User Service
    Get User    ABC
    Create User     haiyan  haiyan lu   haiyangpassword     true

    Update Role To User     haiyan  SECURITY_ADMIN  USER_ADMIN
    Get User    haiyan
    Delete User     haiyan
