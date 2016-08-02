#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile=..\EISTestAutomation\src\common\DismissModalDialog.exe
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****

;USAGE: Dismiss_quote_modal_dialog_ff expectedTitle buttonName timeoutSeconds
;	expectedTitle can be a regex, and can match as a substring (see discussion on options below)
;	buttonName must be either ok or cancel (case-insensitive)

$exitCodeSuccess = 0
$exitCodeTimeoutOnOpen = 101
$exitCodeCouldNotActivate = 111
$exitCodeTimeoutOnClose = 121
$exitCodeInvalidArgs = 999

;Set the select mode to select using regex
AutoItSetOption("WinTitleMatchMode", "4")
;Use option 4 to match on regex; syntax is (quotes included):
;	"[REGEXPTITLE:The page at.*says]"
;Use option 2 to match on substring; syntax is (quotes included):
;		"The page at"
;Use option 3 for exact match
;Negate the option for case-sensitive match

;Note that using the 'text' option in the WinWait and WinWaitActive methods
;	slows it down significantly (so we won't use it)
;AutoItSetOption("WinTextMatchMode", "2")

;Note that after every successful window-related operation, the script will pause for the length of
;  time specified by the WinWaitDelay option (default = 250ms), so hard-coded sleeps of 250ms or less
;  are unnecessary

;Note that the text of the dialog is NOT retrievable, so we will not expect search text to be passed!
If ($CmdLine[0] <> 3) Then
	Exit $exitCodeInvalidArgs
EndIf

$titleSearch = "[REGEXPTITLE:" & $CmdLine[1] & "]"
$key = $CmdLine[2]
$waitTime = $CmdLine[3]

If Not WinWait($titleSearch, "", $waitTime) Then
	Exit $exitCodeTimeoutOnOpen
EndIf

$titleActual = WinGetTitle($titleSearch)

$isActive = WinActivate($titleActual)

;It appears that WinActivate and WinActive always return 0, even when they succeed.  The following test for
;  zero is true, meaning the WinActivate call supposedly failed.  But it did not fail - if I comment out the
;  following test for zero, and just proceed, the dialog gets handled correctly.
;if ($isActive == 0) Then
;	Exit $exitCodeCouldNotActivate
;EndIf

;Note that neither the number, types, or names of controls on this dialog can be retrieved, so we will
;  blindly pass keystrokes to the left and right buttons
;NOTE that it may be okay (and better practice?) to send {ENTER} to the button on the right (the Cancel
;  button) instead instead of {ESCAPE}.  But, perhaps sending {ESCAPE} is better because it may still
;  dismiss the dialog even if the arrangement of the buttons gets changed
If (StringCompare($key, "ok", 0) == 0) Then
	;ControlSend($titleActual, "", "MozillaWindowClass1", "{ENTER}")
	ControlClick($titleActual, "", "[TEXT:OK]")
ElseIf (StringCompare($key, "cancel", 0) == 0) Then
	;ControlSend($titleActual, "", "MozillaWindowClass1", "{ESCAPE}")
	ControlClick($titleActual, "", "[TEXT:Cancel]")
Else
	Exit $exitCodeInvalidArgs
EndIf

If Not WinWaitClose($titleActual, "", $waitTime) Then
	Exit $exitCodeTimeoutOnClose
EndIf

Exit $exitCodeSuccess