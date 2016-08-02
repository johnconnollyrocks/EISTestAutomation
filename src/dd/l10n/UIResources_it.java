package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Italian (it) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_it extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Ceco"},
			{"danish",					"Danese"},
			{"dutch",					"Olandese"},
			{"english",					"Inglese"},
			{"europeanEnglish",			"Inglese europeo"},
			{"finnish",					"Finlandese"},
			{"french",					"Francese"},
			{"german",					"Tedesco"},
			{"hungarian",				"Ungherese"},
			{"italian",					"Italiano"},
			{"japanese",				"Giapponese"},
			{"korean",					"Coreano"},
			{"nordic",					"Nordico"},
			{"norwegian",				"Norvegese"},
			{"polish",					"Polacco"},
			{"portuguese",				"Portoghese"},
			{"russian",					"Russo"},
			{"simplifiedChinese",		"Cinese semplificato"},
			{"spanish",					"Spagnolo"},
			{"swedish",					"Svedese"},
			{"traditionalChinese",		"Cinese tradizionale"},
			{"vietnamese",				"Vietnamita"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Cancella"},
			{"clickForMoreOptions",		"Fare clic per ulteriori opzioni"},
			{"contentType",				"Tipo di contenuto"},
			{"defaultLanguages",		"Lingua/e di default"},
			{"documentation",			"Documentazione"},
			{"downloadLogs",			"Registri di download"},
			{"downloadNow",				"Scarica ora"},
			{"enterSearchString",		"Immettere la stringa di ricerca"},
			{"exportDisclosureLabel",	"Esporta dettagli"},
			{"extras",					"Contenuti aggiuntivi"},
			{"goButton",				"Vai"},
		//	{"helpMeDecide",			"Suggerimenti"},
			{"helpMeDecide",			"Informazioni"},
			{"installNow",				"Installa ora"},
			{"language",				"Lingua"},
			{"languagePacks",			"Language Pack"},
			{"noDownloadsMatch",		"Nessun download corrispondente ai filtri correnti"},
			{"platform",				"Piattaforma"},
			{"serialNumbers",			"Numeri di serie"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"Non si dispone dei numeri di serie per prodotto e release"},
			
			//Field text for which L10N text is not available
		//	{"browserDownload",				"Download da browser"},
			{"browserDownload",				"Download tramite browser"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Download"},
		//	{"exportDisclosureContent",		"Il software che si sta per scaricare è soggetto a leggi e normative sul controllo delle esportazioni. Scaricando questo software l\'utente si impegna, in assenza di previa autorizzazione scritta da parte delle autorità competenti, a non esportare o riesportare in modo consapevole, direttamente o indirettamente, qualsiasi prodotto scaricato da questo sito verso utenti finali (persone fisiche o giuridiche) o destinazioni soggette ad embargo o restrizioni"},
			{"exportDisclosureContent",		"Il software che si sta per scaricare è soggetto a leggi e normative sul controllo delle esportazioni. Scaricando questo software l"+"\\"+"'utente si impegna, in assenza di previa autorizzazione scritta da parte delle autorità competenti, a non esportare o riesportare in modo consapevole, direttamente o indirettamente, qualsiasi prodotto scaricato da questo sito verso utenti finali (persone fisiche o giuridiche) o destinazioni soggette ad embargo o restrizioni"},
			{"legalNoticesAndTrademarks",	"Note legali e marchi"},
			{"myProfile",					"Profilo"},
			{"needHelpContactUs",			"Assistenza Contatta Autodesk"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Ricerca prodotti"},
			{"privacyPolicy",				"Informativa sulla privacy"},
			{"relatedInformation",			"Informazioni correlate:"},
			{"search",						"Funzione di ricerca"},
			{"signOut",						"Esci"},
			{"subscriptionHome",			"Home page di Autodesk Subscription"},
			{"welcome",						"Area di benvenuto"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Tutto"},
	};
}
