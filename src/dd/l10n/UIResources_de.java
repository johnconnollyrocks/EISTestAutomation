package dd.l10n;

import java.util.ListResourceBundle;

/**
 * German (de) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_de extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Tschechisch"},
			{"danish",					"Dänisch"},
			{"dutch",					"Holländisch"},
			{"english",					"Englisch"},
			{"europeanEnglish",			"Englisch (Europa)"},
			{"finnish",					"Finnisch"},
			{"french",					"Französisch"},
			{"german",					"Deutsch"},
			{"hungarian",				"Ungarisch"},
			{"italian",					"Italienisch"},
			{"japanese",				"Japanisch"},
			{"korean",					"Koreanisch"},
			{"nordic",					"Nordisch"},
			{"norwegian",				"Norwegisch"},
			{"polish",					"Polnisch"},
			{"portuguese",				"Portugiesisch"},
			{"russian",					"Russisch"},
			{"simplifiedChinese",		"Vereinfachtes Chinesisch"},
			{"spanish",					"Spanisch"},
			{"swedish",					"Schwedisch"},
			{"traditionalChinese",		"Traditionelles Chinesisch"},
			{"vietnamese",				"Vietnamesisch"},
			
			//TODO  Update this!!!
			//Language text for which L10N text is not available.  I am using the text
			//  I saw in the UI, but we need to have the product team confirm it.
			{"britishEnglish",			"Englisch (Großbritannien)"},

			
			//Field text
			{"clearButton",					"Löschen"},
			{"clickForMoreOptions",			"Klicken, um weitere"},
			{"contentType",					"Inhaltstyp"},
			{"defaultLanguages",			"Standardsprache(n)"},
			{"documentation",				"Dokumentation"},
			{"downloadLogs",				"Download-Protokolle"},
			{"downloadNow",					"Jetzt herunterladen"},
			{"enterSearchString",			"Suchzeichenfolge eingeben"},
			{"exportDisclosureLabel",		"Spezifikationen exportieren"},
			{"extras",						"Extras"},
			{"goButton",					"Weiter"},
			{"helpMeDecide",				"Entscheidungshilfe"},
			{"installNow",					"Jetzt installieren"},
			{"language",					"Sprache"},
			{"languagePacks",				"Sprachpakete"},
			{"noDownloadsMatch",			"Keine Downloads entsprechen den aktuellen Filtern"},
			{"platform",					"Plattform"},
			{"serialNumbers",				"Seriennummern"},
			{"software",					"Software"},
			{"youDoNotHaveSNs",				"Sie verfügen nicht über Seriennummern für dieses Produkt und diese Version"},
			
			//TODO  Update this!!!
			//Field text for which L10N text is not available.  In some cases I am using the text
			//  I saw in the UI, but we need to have the product team confirm them.  In some other cases
			//  I know the text is not L10N-ized, but I am using it anyway, just so the test runs.
			{"browserDownload",				"Browser-Download"},
			{"downloadPreference",			"Download-Einstellungen"},
			{"downloads",					"Downloads"},
			{"exportDisclosureContent",		"Die Software, die Sie herunterladen möchten, unterliegt Gesetzen und Regelungen zur Exportkontrolle. Indem Sie diese Software herunterladen, erklären Sie sich damit einverstanden, dass Sie nicht bewusst ohne vorherige schriftliche Genehmigung der zuständigen staatlichen Einrichtungen Software, die Sie von dieser Website heruntergeladen haben, auf direkte oder indirekte Weise an ein untersagtes Ziel, an untersagte Endbenutzer oder zu verbotenem Endverbrauch exportieren oder reexportieren werden"},
			{"legalNoticesAndTrademarks",	"Rechtliche Hinweise und Marken"},
			{"myProfile",					"Mein Profil"},
			{"needHelpContactUs",			"Wünschen Sie Hilfe? Kontakt"},
			{"nothingDownloadedToDate",		"Bisher nichts heruntergeladen"},
			{"productSearch",				"Produktsuche"},
			{"privacyPolicy",				"Datenschutzrichtlinien"},
			{"relatedInformation",			"Zugehörige Informationen:"},
			{"search",						"Suche"},
			{"signOut",						"Abmelden"},
			{"subscriptionHome",			"Subscriptions-Startseite"},
			{"welcome",						"Willkommen"},

			
			//Other text
			//TODO  Update this!!!
			//Other text for which L10N text is not available.  I am using the text
			//  I saw in the UI, but we need to have the product team confirm it.
			{"platformOptionAll",			"Alle"},
	};
}
