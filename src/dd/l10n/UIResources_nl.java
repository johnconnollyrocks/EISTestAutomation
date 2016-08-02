package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Dutch (nl) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_nl extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Tsjechië"},
			{"danish",					"Deens"},
			{"dutch",					"Nederlands"},
			{"english",					"Engels"},
			{"europeanEnglish",			"Europees Engels"},
			{"finnish",					"Fins"},
			{"french",					"Frans"},
			{"german",					"Duits"},
			{"hungarian",				"Hongaars"},
			{"italian",					"Italiaans"},
			{"japanese",				"Japans"},
			{"korean",					"Koreaans"},
			{"nordic",					"Scandinavisch"},
			{"norwegian",				"Noors"},
			{"polish",					"Pools"},
			{"portuguese",				"Portugees"},
			{"russian",					"Russisch"},
			{"simplifiedChinese",		"Vereenvoudigd Chinees"},
			{"spanish",					"Spaans"},
			{"swedish",					"Zweeds"},
			{"traditionalChinese",		"Traditioneel Chinees"},
			{"vietnamese",				"Vietnamees"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Wissen"},
			{"clickForMoreOptions",		"Klik voor meer opties"},
			{"contentType",				"Inhoudstype"},
			{"defaultLanguages",		"Standaardta(a)l(en)"},
			{"documentation",			"Documentatie"},
			{"downloadLogs",			"Downloadlogboeken"},
			{"downloadNow",				"Nu downloaden"},
			{"enterSearchString",		"Zoekreeks invoeren"},
			{"exportDisclosureLabel",	"Gegevens exporteren"},
			{"extras",					"Extra's"},
			{"goButton",				"Uitvoeren"},
			{"helpMeDecide",			"Help mij beslissen"},
			{"installNow",				"Nu installeren"},
			{"language",				"Taal"},
			{"languagePacks",			"Taalpakketten"},
			{"noDownloadsMatch",		"Er komen geen downloads overeen met de huidige filters"},
			{"platform",				"Platform"},
			{"serialNumbers",			"Serienummers"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"U hebt geen serienummers voor dit product en deze release"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"Downloaden via browser"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Downloads"},
			{"exportDisclosureContent",		"The software you are about to download is subject to export control laws and regulations. By downloading this software, you agree that you will not knowingly, without prior written authorization from the competent government authorities, export or reexport - directly or indirectly - any software downloaded from this website to any prohibited destination, end-user, or end-use."},
			{"legalNoticesAndTrademarks",	"Wettelijke kennisgevingen en handelsmerken"},
			{"myProfile",					"Mijn profiel"},
			{"needHelpContactUs",			"Hulp nodig? Neem contact met ons op"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Zoeken naar product"},
			{"privacyPolicy",				"Privacybeleid"},
			{"relatedInformation",			"Verwante informatie"},
			{"search",						"Zoeken"},
			{"signOut",						"Afmelden"},
			{"subscriptionHome",			"Homepage abonnementen"},
			{"welcome",						"Welkom"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Alle"},
	};
}
