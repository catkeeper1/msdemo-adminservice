*** Settings ***
Library  Screenshot
Library  String
Library  DateTime
Library  Collections
Library  Dialogs
Library  OperatingSystem
Library  org.ckr.msdemo.adminservice.apitest.keywords.UserService



*** Test Cases ***
User Service
    Get User    ABC
