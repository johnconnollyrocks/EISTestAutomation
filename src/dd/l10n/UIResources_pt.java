package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Portuguese (pt) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_pt extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Tcheco"},
			{"danish",					"Dinamarqu�s"},
			{"dutch",					"Holand�s"},
			{"english",					"Ingl�s"},
			{"europeanEnglish",			"Ingl�s europeu"},
			{"finnish",					"Finland�s"},
			{"french",					"Franc�s"},
			{"german",					"Alem�o"},
			{"hungarian",				"H�ngaro"},
			{"italian",					"Italiano"},
			{"japanese",				"Japon�s"},
			{"korean",					"Coreano"},
			{"nordic",					"N�rdico"},
			{"norwegian",				"Noruegu�s"},
			{"polish",					"Polon�s"},
			{"portuguese",				"Portugu�s"},
			{"russian",					"Russo"},
			{"simplifiedChinese",		"Chin�s simplificado"},
			{"spanish",					"Espanhol"},
			{"swedish",					"Sueco"},
			{"traditionalChinese",		"Chin�s tradicional"},
			{"vietnamese",				"Vietnamita"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Limpar"},
			{"clickForMoreOptions",		"Clique para ver mais op��es"},
			{"contentType",				"Tipo de Conte�do"},
			{"defaultLanguages",		"Idioma(s) padr�o"},
			{"documentation",			"Documenta��o"},
			{"downloadLogs",			"Logs de download"},
			{"downloadNow",				"Fazer download agora"},
			{"enterSearchString",		"Digite a sequ�ncia de caracteres de pesquisa"},
			{"exportDisclosureLabel",	"Exportar divulga��o"},
			{"extras",					"Extras"},
			{"goButton",				"Ir"},
			{"helpMeDecide",			"Ajude-me a decidir"},
			{"installNow",				"Instalar agora"},
			{"language",				"Idioma"},
			{"languagePacks",			"Pacotes de Idioma"},
			{"noDownloadsMatch",		"Nenhum download corresponde aos filtros atuais"},
			{"platform",				"Plataforma"},
			{"serialNumbers",			"N�meros de s�rie"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"Voc� n�o tem N�meros de s�rie para este Produto e Vers�o"},
			
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
