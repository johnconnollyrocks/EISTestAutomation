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
			{"danish",					"Dinamarquês"},
			{"dutch",					"Holandês"},
			{"english",					"Inglês"},
			{"europeanEnglish",			"Inglês europeu"},
			{"finnish",					"Finlandês"},
			{"french",					"Francês"},
			{"german",					"Alemão"},
			{"hungarian",				"Húngaro"},
			{"italian",					"Italiano"},
			{"japanese",				"Japonês"},
			{"korean",					"Coreano"},
			{"nordic",					"Nórdico"},
			{"norwegian",				"Norueguês"},
			{"polish",					"Polonês"},
			{"portuguese",				"Português"},
			{"russian",					"Russo"},
			{"simplifiedChinese",		"Chinês simplificado"},
			{"spanish",					"Espanhol"},
			{"swedish",					"Sueco"},
			{"traditionalChinese",		"Chinês tradicional"},
			{"vietnamese",				"Vietnamita"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Limpar"},
			{"clickForMoreOptions",		"Clique para ver mais opções"},
			{"contentType",				"Tipo de Conteúdo"},
			{"defaultLanguages",		"Idioma(s) padrão"},
			{"documentation",			"Documentação"},
			{"downloadLogs",			"Logs de download"},
			{"downloadNow",				"Fazer download agora"},
			{"enterSearchString",		"Digite a sequência de caracteres de pesquisa"},
			{"exportDisclosureLabel",	"Exportar divulgação"},
			{"extras",					"Extras"},
			{"goButton",				"Ir"},
			{"helpMeDecide",			"Ajude-me a decidir"},
			{"installNow",				"Instalar agora"},
			{"language",				"Idioma"},
			{"languagePacks",			"Pacotes de Idioma"},
			{"noDownloadsMatch",		"Nenhum download corresponde aos filtros atuais"},
			{"platform",				"Plataforma"},
			{"serialNumbers",			"Números de série"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"Você não tem Números de série para este Produto e Versão"},
			
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
