*** Settings ***
Documentation    Example suite
Suite Setup      Do Something1
Suite Teardown   Do Something2
Force Tags       example
Metadata    Version        2.0
Metadata    More Info      For more information about *Robot Framework* see http://robotframework.org
Metadata    Executed At    ${HOST}

*** Variables ***
${MESSAGE1}       Hello, world!
${MESSAGE2}       Bye, world!
${HOST}           localhost

*** Keywords ***
Do Something1
    Log     ${MESSAGE1}  WARN
Do Something2
    Log     ${MESSAGE2}  WARN