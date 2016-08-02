package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Czech (cs) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_cs extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Čeština"},
			{"danish",					"Dánština"},
			{"dutch",					"Holandština"},
			{"english",					"Angličtina"},
			{"europeanEnglish",			"Evropská angličtina"},
			{"finnish",					"Finština"},
			{"french",					"Francouzština"},
			{"german",					"Němčina"},
			{"hungarian",				"Maďarština"},
			{"italian",					"Italština"},
			{"japanese",				"Japonština"},
			{"korean",					"Korejština"},
			{"nordic",					"Skandinávský"},
			{"norwegian",				"Norština"},
			{"polish",					"Polština"},
			{"portuguese",				"Portugalština"},
			{"russian",					"Ruština"},
			{"simplifiedChinese",		"Zjednodušená čínština"},
			{"spanish",					"Španělština"},
			{"swedish",					"Švédština"},
			{"traditionalChinese",		"Tradiční čínština"},
			{"vietnamese",				"Vietnamština"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Vymazat"},
			{"clickForMoreOptions",		"Kliknutím zobrazíte více možností"},
			{"contentType",				"Typ obsahu"},
			{"defaultLanguages",		"Výchozí jazyky"},
			{"documentation",			"Dokumentace"},
			{"downloadLogs",			"Protokoly stahování"},
			{"downloadNow",				"Stáhnout nyní"},
			{"enterSearchString",		"Zadejte řetězec ke hledání"},
			{"exportDisclosureLabel",	"Exportovat podrobnosti"},
			{"extras",					"Další materiály"},
			{"goButton",				"Přejít"},
			{"helpMeDecide",			"Potřebuji poradit s výběrem"},
			{"installNow",				"Instalovat nyní"},
			{"language",				"Jazyk"},
			{"languagePacks",			"Jazykové balíčky"},
			{"noDownloadsMatch",		"Žádné položky ke stažení nesplňují aktuální filtry"},
			{"platform",				"Platforma"},
			{"serialNumbers",			"Sériová čísla"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"K tomuto produktu a verzi nemáte sériová čísla"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"Stažení prohlížeče"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Stahování"},
			{"exportDisclosureContent",		"The software you are about to download is subject to export control laws and regulations. By downloading this software, you agree that you will not knowingly, without prior written authorization from the competent government authorities, export or reexport - directly or indirectly - any software downloaded from this website to any prohibited destination, end-user, or end-use."},
			{"legalNoticesAndTrademarks",	"Právní informace a ochranné známky"},
			{"myProfile",					"Můj profil"},
			{"needHelpContactUs",			"Potřebujete nápovědu? Kontakt"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Hledání produktu"},
			{"privacyPolicy",				"Ochrana soukromí"},
			{"relatedInformation",			"Související informace"},
			{"search",						"Vyhledat"},
			{"signOut",						"Odhlásit"},
			{"subscriptionHome",			"Subscription – Domů"},
			{"welcome",						"Vítejte"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Vše"},
	};
}
