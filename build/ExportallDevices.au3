		 sleep(500)
		 WinWait("Opening Device-List.csv")
		 WinActivate("Opening Device-List.csv")
	  If WinExists("Opening Device-List.csv") Then
		ConsoleWrite("Download window found")
		 sleep(500)
		 Send("{F6}")
		 sleep(500)
		 Send("!s")
		 sleep(500)
		 Send("{ENTER}")
	  Else
		ConsoleWrite("Download window not found")
	  	EndIf
