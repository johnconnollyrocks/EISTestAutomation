package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Hungarian (hu) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_hu extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Cseh"},
			{"danish",					"Dán"},
			{"dutch",					"Holland"},
			{"english",					"Angol"},
			{"europeanEnglish",			"Európai angol"},
			{"finnish",					"Finn"},
			{"french",					"Francia"},
			{"german",					"Német"},
			{"hungarian",				"Magyar"},
			{"italian",					"Olasz"},
			{"japanese",				"Japán"},
			{"korean",					"Koreai"},
			{"nordic",					"Észak-európai"},
			{"norwegian",				"Norvég"},
			{"polish",					"Lengyel"},
			{"portuguese",				"Portugál"},
			{"russian",					"Orosz"},
			{"simplifiedChinese",		"Egyszerűsített kínai"},
			{"spanish",					"Spanyol"},
			{"swedish",					"Svéd"},
			{"traditionalChinese",		"Hagyományos kínai"},
			{"vietnamese",				"Vietnámi"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Törlés"},
			{"clickForMoreOptions",		"További beállításokért kattintson ide"},
			{"contentType",				"Tartalom típusa"},
			{"defaultLanguages",		"Alapértelmezett nyelv(ek)"},
			{"documentation",			"Dokumentáció"},
			{"downloadLogs",			"Letöltési naplók"},
			{"downloadNow",				"Letöltés"},
			{"enterSearchString",		"Keresési karaktersor megadása"},
			{"exportDisclosureLabel",	"Beszámoló exportálása"},
			{"extras",					"Extrák"},
			{"goButton",				"Indítás"},
			{"helpMeDecide",			"Segítség a döntésben"},
			{"installNow",				"Telepítés most"},
			{"language",				"Nyelv"},
			{"languagePacks",			"Nyelvi csomagok"},
			{"noDownloadsMatch",		"A jelenlegi szűrőknek egy letöltés sem felel meg"},
			{"platform",				"Platform"},
			{"serialNumbers",			"Sorozatszámok"},
			{"software",				"Szoftver"},
			{"youDoNotHaveSNs",			"Nem rendelkezik sorozatszámmal ehhez a termékhez és kiadáshoz"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"Letöltés böngészőben"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Letöltések"},
			{"exportDisclosureContent",		"The software you are about to download is subject to export control laws and regulations. By downloading this software, you agree that you will not knowingly, without prior written authorization from the competent government authorities, export or reexport - directly or indirectly - any software downloaded from this website to any prohibited destination, end-user, or end-use."},
			{"legalNoticesAndTrademarks",	"Jogi közlemények és védjegyekre vonatkozó megjegyzések"},
			{"myProfile",					"Saját profil"},
			{"needHelpContactUs",			"Segítségre van szüksége? Kapcsolatfelvétel"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Termékkeresés"},
			{"privacyPolicy",				"Adatvédelmi nyilatkozat"},
			{"relatedInformation",			"Kapcsolódó információ"},
			{"search",						"Keresés"},
			{"signOut",						"Kijelentkezés"},
			{"subscriptionHome",			"Szoftverkövetés kezdőoldal"},
			{"welcome",						"Üdvözöljük!"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Mind"},
	};
}
