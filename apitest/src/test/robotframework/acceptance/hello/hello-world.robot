*** Settings ***
Library  Screenshot
Library  String
Library  DateTime
Library  Collections
Library  Dialogs
Library  OperatingSystem
Library  org.ckr.msdemo.adminservice.apitest.keywords.HelloWorld    WITH NAME   H
Library  org.ckr.msdemo.adminservice.apitest.keywords.HelloWorld    WITH NAME   W
Library  org.ckr.msdemo.adminservice.apitest.keywords.HelloEarth    WITH NAME   E
Force Tags      req-42
Default Tags    owner-john    smoke

*** Variables ***
${HOST}         10.0.1.42

*** Test Cases ***
My Test 1
    H.Say
    W.Say
    E.Speak
    E.Say
    E.Sing
    ${ABC} =   E.Whale Say
    Should Be Equal    ${ABC}    I'm a whale.
My Test 2
    Log	Hello, world!			# Normal INFO message.
    Log	Warning, world!	WARN		# Warning.
    Log	<b>Hello</b>, world!	html=yes		# INFO message as HTML.
    Log	<b>Hello</b>, world!	HTML		# Same as above.
    Log	<b>Hello</b>, world!	DEBUG	html=true	# DEBUG as HTML.
    Log	Hello, console!	console=yes		# Log also to the console.
    Log	Hyvä \x00	repr=yes		# Log 'Hyv\xe4 \x00'.
    Log To Console	Hello, console!
    Log To Console	Hello, stderr!	STDERR
    Log To Console	Message starts here and is	no_newline=true
    Log To Console	continued without newline.
    Run Keyword If	os.sep == '\'	Log	on Windows
My Test 3
    Take Screenshot			# LOGDIR/screenshot_1.jpg (index automatically incremented)
    Take Screenshot	mypic		# LOGDIR/mypic_1.jpg (index automatically incremented)
    Take Screenshot	${TEMPDIR}/mypic		# /tmp/mypic_1.jpg (index automatically incremented)
    Take Screenshot	pic.jpg		# LOGDIR/pic.jpg (always uses this file)
    Take Screenshot	login.jpg	80%	# Specify both name and width.
    Take Screenshot	width=550px		# Specify only width.
My Test 4
    ${str1} =	Convert To Lowercase	ABC
    ${str2} =	Convert To Lowercase	1A2c3D
    Should Be Equal	${str1}	abc
    Should Be Equal	${str2}	1a2c3d

    ${str1} =	Convert To Uppercase	abc
    ${str2} =	Convert To Uppercase	1a2C3d
    Should Be Equal	${str1}	ABC
    Should Be Equal	${str2}	1A2C3D

My Test 5
    ${ret} =	Generate Random String
    ${low} =	Generate Random String	12	[LOWER]
    ${bin} =	Generate Random String	8	01
    ${hex} =	Generate Random String	4	[NUMBERS]abcdef

My Test 678
    My Test 6
    My Test 7
    My Test 8

#Normal Error
#    Fail    This is a rather boring example...

Simple
    [Documentation]    Simple documentation
    No Operation

Formatting
    [Documentation]    *This is bold*, _this is italic_  and here is a link: http://robotframework.org
    No Operation

Variables
    [Documentation]    Executed at ${HOST} by ${USER}
    No Operation

Splitting
    [Documentation]    This documentation    is split    into multiple columns
    No Operation

Many lines
    [Documentation]    Here we have
    ...                an automatic newline
    No Operation
No own tags
    [Documentation]    This test has tags owner-john, smoke and req-42.
    No Operation

With own tags
    [Documentation]    This test has tags not_ready, owner-mrx and req-42.
    [Tags]    owner-mrx    not_ready
    No Operation

Own tags with variables
    [Documentation]    This test has tags host-10.0.1.42 and req-42.
    [Tags]    host-${HOST}
    No Operation

Empty own tags
    [Documentation]    This test has only tag req-42.
    [Tags]
    No Operation

Set Tags and Remove Tags Keywords
    [Documentation]    This test has tags mytag and owner-john.
    Set Tags    mytag
    Remove Tags    smoke    req-*
Template with embedded arguments
    [Tags]      template
    [Template]    The result of ${calculation} should be ${expected}
    1 + 1    2
    1 + 2    3


*** Keywords ***
The result of ${calculation} should be ${expected}
    ${result} =    Calculate    ${calculation}
    Should Be Equal    ${result}     ${expected}


My Test 6
    @{L1}=  Create List     a
    @{L2}=  Create List     a, b
    @{L3}=  Create List     a, b, c
    Append To List	${L1}	xxx
    Append To List	${L2}	x	y	z

    ${x} =	Combine Lists	${L1}	${L2}
    ${y} =	Combine Lists	${L1}	${L2}	${L1}

    ${x} =	Count Values In List	${L3}	b

My Test 7
    ${date1} =	Convert Date	2014-06-11 10:07:42.000
    ${date2} =	Convert Date	20140611 100742	result_format=timestamp
    Should Be Equal	${date1}	${date2}
    ${date} =	Convert Date	20140612 12:57	exclude_millis=yes
    Should Be Equal	${date}	2014-06-12 12:57:00


    ${date} =	Add Time To Date	2014-05-28 12:05:03.111	7 days
    Should Be Equal	${date}	2014-06-04 12:05:03.111
    ${date} =	Add Time To Date	2014-05-28 12:05:03.111	01:02:03:004
    Should Be Equal	${date}	2014-05-28 13:07:06.115

#    ${username} =	Get Selection From User	Select user name	user1	user2	admin
#    ${username} =	Get Value From User	Input user name	default
#    ${password} =	Get Value From User	Input password	hidden=yes
#    Pause Execution	message=Test execution paused. Press OK to continue.

My Test 8
    [Documentation]    Another dummy test
    [Tags]    dummy    owner-johndoe
    Append To Environment Variable	NAME	first
    Should Be Equal	%{NAME}	first
    Append To Environment Variable	NAME	second	third
    Should Be Equal	%{NAME}	first${:}second${:}third
    Append To Environment Variable	NAME2	first	separator=-
    Should Be Equal	%{NAME2}	first
    Append To Environment Variable	NAME2	second	separator=-
    Should Be Equal	%{NAME2}	first-second