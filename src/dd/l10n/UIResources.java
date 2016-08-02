package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Default (English - en) resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"britishEnglish",			"British English"},
			{"czech",					"Czech"},
			{"danish",					"Danish"},
			{"dutch",					"Dutch"},
			{"english",					"English"},
			{"europeanEnglish",			"European English"},
			{"finnish",					"Finnish"},
			{"french",					"French"},
			{"german",					"German"},
			{"hungarian",				"Hungarian"},
			{"italian",					"Italian"},
			{"japanese",				"Japanese"},
			{"korean",					"Korean"},
			{"nordic",					"Nordic"},
			{"norwegian",				"Norwegian"},
			{"polish",					"Polish"},
			{"portuguese",				"Portuguese"},
			{"russian",					"Russian"},
			{"simplifiedChinese",		"Simplified Chinese"},
			{"spanish",					"Spanish"},
			{"swedish",					"Swedish"},
			{"traditionalChinese",		"Traditional Chinese"},
			{"vietnamese",				"Vietnamese"},
			
			//Other text
			{"browserDownload",				"Browser Download"},
			{"clearButton",					"Clear"},
			{"clickForMoreOptions",			"Click for more options"},
			{"contentType",					"Content Type"},
			{"defaultLanguages",			"Default Language(s)"},
			{"documentation",				"Documentation"},
			{"downloadLogs",				"Download Logs"},
			{"downloadNow",					"Download Now"},
			{"downloadPreference",			"Download Preference"},
			{"downloads",					"Downloads"},
			{"enterSearchString",			"Enter Search String"},
			{"exportDisclosureContent",		"The software you are about to download is subject to export control laws and regulations. By downloading this software, you agree that you will not knowingly, without prior written authorization from the competent government authorities, export or reexport - directly or indirectly - any software downloaded from this website to any prohibited destination, end-user, or end-use."},
			{"exportDisclosureLabel",		"Export Disclosure"},
			{"extras",						"Extras"},
			{"goButton",					"Go"},
			{"helpMeDecide",				"Help me decide"},
			{"installNow",					"Install Now"},
			{"language",					"Language"},
			{"languagePacks",				"Language Packs"},
			{"legalNoticesAndTrademarks",	"Legal Notices and Trademarks"},
			{"myProfile",					"My Profile"},
			{"needHelpContactUs",			"Need Help? Contact Us"},
			{"noDownloadsMatch",			"No downloads match the current filters"},
			{"nothingDownloadedToDate",		"Nothing Downloaded To Date"},
			{"platform",					"Platform"},
			{"privacyPolicy",				"Privacy Policy"},
			{"productSearch",				"Product Search"},
			{"relatedInformation",			"Related Information:"},
			{"search",						"Search"},
			{"serialNumbers",				"Serial Numbers"},
			{"signOut",						"Sign Out"},
			{"software",					"Software"},
			{"subscriptionHome",			"Subscription Home"},
			{"youDoNotHaveSNs",				"You do not have Serial Numbers for this Product and Release"},
			{"welcome",						"Welcome"},
			
			//Other text
			{"platformOptionAll",			"All"},
	};
}
