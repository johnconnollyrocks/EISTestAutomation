package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Polish (pl) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_pl extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Czeski"},
			{"danish",					"Duński"},
			{"dutch",					"Holenderski"},
			{"english",					"Angielski"},
			{"europeanEnglish",			"Angielski europejski"},
			{"finnish",					"Fiński"},
			{"french",					"Francuski"},
			{"german",					"Niemiecki"},
			{"hungarian",				"Węgierski"},
			{"italian",					"Włoski"},
			{"japanese",				"Japoński"},
			{"korean",					"Koreański"},
			{"nordic",					"Języki nordyckie"},
			{"norwegian",				"Norweski"},
			{"polish",					"Polski"},
			{"portuguese",				"Portugalski"},
			{"russian",					"Rosyjski"},
			{"simplifiedChinese",		"Chiński (uproszczony)"},
			{"spanish",					"Hiszpański"},
			{"swedish",					"Szwedzki"},
			{"traditionalChinese",		"Chiński (tradycyjny)"},
			{"vietnamese",				"Wietnamski"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Wyczyść"},
			{"clickForMoreOptions",		"Kliknij, aby wyświetlić więcej opcji"},
			{"contentType",				"Typ zawartości"},
			{"defaultLanguages",		"Domyślny(e) język(i)"},
			{"documentation",			"Dokumentacja"},
			{"downloadLogs",			"Dzienniki pobierania"},
			{"downloadNow",				"Pobierz teraz"},
			{"enterSearchString",		"Wprowadź szukany ciąg"},
			{"exportDisclosureLabel",	"Eksportuj dane szczegółowe"},
			{"extras",					"Dodatki"},
			{"goButton",				"Przejdź"},
			{"helpMeDecide",			"Pomoc w podjęciu decyzji"},
			{"installNow",				"Zainstaluj teraz"},
			{"language",				"Język"},
			{"languagePacks",			"Pakiety językowe"},
			{"noDownloadsMatch",		"Brak materiałów do pobrania dla aktualnych filtrów"},
			{"platform",				"Platforma"},
			{"serialNumbers",			"Numery seryjne"},
			{"software",				"Oprogramowanie"},
			{"youDoNotHaveSNs",			"Brak numerów seryjnych dla tego produktu i wersji"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"Pobieranie w przeglądarce"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Zawartość do pobrania"},
			{"exportDisclosureContent",		"The software you are about to download is subject to export control laws and regulations. By downloading this software, you agree that you will not knowingly, without prior written authorization from the competent government authorities, export or reexport - directly or indirectly - any software downloaded from this website to any prohibited destination, end-user, or end-use."},
			{"legalNoticesAndTrademarks",	"Informacje prawne i znaki towarowe"},
			{"myProfile",					"Mój profil"},
			{"needHelpContactUs",			"Potrzebujesz pomocy? Skontaktuj się z nami"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Wyszukiwanie produktu"},
			{"privacyPolicy",				"Zasady zachowania prywatności"},
			{"relatedInformation",			"Powiązane informacje"},
			{"search",						"Wyszukaj"},
			{"signOut",						"Wyloguj"},
			{"subscriptionHome",			"Strona domowa subskrypcji"},
			{"welcome",						"Witamy"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Wszystko"},
	};
}
