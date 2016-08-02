#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_UseX64=y
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
#include <IE.au3>

$exitCodeSuccess = 0
$exitCodeLinkNotFound = 107
$exitCodeInvalidArgs = 999

If ($CmdLine[0] < 2) Then
	Exit $exitCodeInvalidArgs
EndIf

$URL = $CmdLine[1]
$linkText = $CmdLine[2]
$oIE = 0

;MsgBox(0, "Link text", $linkText)

$oIE = _IECreate($URL, 1)
   ;MsgBox(0, "Create Error", @error)
   ;MsgBox(0, "Create Extended", @extended)

Sleep(1000)

_IELinkClickByText($oIE, $linkText, 0, 1)
If @error <> $_IEStatus_Success Then
   ;MsgBox(0, "Click Error", @error)
   ;MsgBox(0, "Click Extended", @extended)
   Exit $exitCodeLinkNotFound
EndIf

Exit $exitCodeSuccess
