

Local $browser=$CmdLine[1]
Local $scenario=$CmdLine[2]
Local $numOfProducts=$CmdLine[3]

If $scenario="save" Then
	  if $browser="ie" Then
		 Local $windHandle=WinGetHandle("[Class:IEFrame]", "")
		 Local $winTitle = "[HANDLE:" & $windHandle & "]";
		 ;get coordinates of default HWND
		 Local $ctlText=ControlGetPos ($winTitle, "", "[Class:DirectUIHWND;INSTANCE:1]")

		 ; wait till the notification bar is displayed
		 Local $color= PixelGetColor ($ctlText[0],$ctlText[1])
		 while $color <> 0
			sleep(500)
			$ctlText=ControlGetPos ($winTitle, "", "[Class:DirectUIHWND;INSTANCE:1]")
			$color= PixelGetColor ($ctlText[0],$ctlText[1])
		 wend
		 ; Select save as option
		 WinActivate ($winTitle, "")
		 Send("{F6}")
		 sleep(500)
		 Send("{TAB}")
		 sleep(500)
		 Send("{ENTER}")
	  EndIf

	  if $browser="firefox" Then
		 WinWaitActive("Opening AdApplicationManager-installer.exe","You have chosen",2)
		 If WinExists("Opening AdApplicationManager-installer.exe") Then
			Send("{TAB}")
			Send("{ENTER}")
			Send("{ENTER}")
			Send("{ENTER}")
		 EndIf
	  EndIf
EndIf

If $scenario="count" Then
	  if $browser="ie" Then

		 If WinExists("View Downloads - Windows Internet Explorer") Then
				  ConsoleWrite("Download window found")
				  WinClose("View Downloads - Windows Internet Explorer")
		 else
			   Local $windHandle=WinGetHandle("[Class:IEFrame]", "")
			   Local $winTitle = "[HANDLE:" & $windHandle & "]";
			   ;get coordinates of default HWND
			   Local $ctlText=ControlGetPos ($winTitle, "", "[Class:DirectUIHWND;INSTANCE:1]")

			   ; wait till the notification bar is displayed
			   Local $color= PixelGetColor ($ctlText[0],$ctlText[1])
			   while $color <> 0
			   sleep(500)
			   $ctlText=ControlGetPos ($winTitle, "", "[Class:DirectUIHWND;INSTANCE:1]")
			   $color= PixelGetColor ($ctlText[0],$ctlText[1])

			   wend
			   ; Select save as option
			   If WinExists($winTitle) Then
				  ConsoleWrite("Download window found" & $i & @LF)
				  ConsoleWrite("Download window found")
			   EndIf
				  WinActivate ($winTitle, "")
				  Send("{F6}")
				  sleep(500)
				  Send("{TAB}")
				  sleep(500)
				  Send("{TAB}")
				  sleep(500)
				  Send("{ENTER}")
				  sleep(10000)
		 EndIf

	  EndIf

	  if $browser="firefox" Then
			For $i = 1 To $numOfProducts Step 1
			   WinWaitActive("Opening AdApplicationManager-installer.exe","You have chosen",2)

			   If WinExists("Opening AdApplicationManager-installer.exe") Then
				  ConsoleWrite("Download window found" & $i & @LF)
				  WinWaitActive("Opening AdApplicationManager-installer.exe","You have chosen",1)
				  WinClose("Opening AdApplicationManager-installer.exe")
				  sleep(10000)
			   Else
				  ConsoleWrite("Download window not found")
			   EndIf

			Next
	  EndIf
EndIf
