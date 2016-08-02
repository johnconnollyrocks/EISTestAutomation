
Local $contractnumber=$CmdLine[1]
		 sleep(5000)
		 WinActivate("Opening " & $contractnumber &".csv","You have chosen")
		 If WinExists("Opening " & $contractnumber &".csv") Then

				  sleep(500)
				  Send("!s")
				  sleep(500)
				  Send("{ENTER}")
				  sleep(10000)
		 Else
		 EndIf