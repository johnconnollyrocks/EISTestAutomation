package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Template language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_template extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					""},
			{"danish",					""},
			{"dutch",					""},
			{"english",					""},
			{"europeanEnglish",			""},
			{"finnish",					""},
			{"french",					""},
			{"german",					""},
			{"hungarian",				""},
			{"italian",					""},
			{"japanese",				""},
			{"korean",					""},
			{"nordic",					""},
			{"norwegian",				""},
			{"polish",					""},
			{"portuguese",				""},
			{"russian",					""},
			{"simplifiedChinese",		""},
			{"spanish",					""},
			{"swedish",					""},
			{"traditionalChinese",		""},
			{"vietnamese",				""},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				""},
			{"clickForMoreOptions",		""},
			{"contentType",				""},
			{"defaultLanguages",		""},
			{"documentation",			""},
			{"downloadLogs",			""},
			{"downloadNow",				""},
			{"enterSearchString",		""},
			{"exportDisclosureLabel",	""},
			{"extras",					""},
			{"goButton",				""},
			{"helpMeDecide",			""},
			{"installNow",				""},
			{"language",				""},
			{"languagePacks",			""},
			{"noDownloadsMatch",		""},
			{"platform",				""},
			{"serialNumbers",			""},
			{"software",				""},
			{"youDoNotHaveSNs",			""},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"[Not available - do not edit]"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"[Not available - do not edit]"},
			{"exportDisclosureContent",		"[Not available - do not edit]"},
			{"legalNoticesAndTrademarks",	"[Not available - do not edit]"},
			{"myProfile",					"[Not available - do not edit]"},
			{"needHelpContactUs",			"[Not available - do not edit]"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"[Not available - do not edit]"},
			{"privacyPolicy",				"[Not available - do not edit]"},
			{"relatedInformation",			"[Not available - do not edit]"},
			{"search",						"[Not available - do not edit]"},
			{"signOut",						"[Not available - do not edit]"},
			{"subscriptionHome",			"[Not available - do not edit]"},
			{"welcome",						"[Not available - do not edit]"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"[Not available - do not edit]"},
	};
}
