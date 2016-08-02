package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Spanish (es) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_es extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Checo"},
			{"danish",					"Danés"},
			{"dutch",					"Neerlandés"},
			{"english",					"Inglés"},
			{"europeanEnglish",			"Inglés europeo"},
			{"finnish",					"Finés"},
			{"french",					"Francés"},
			{"german",					"Alemán"},
			{"hungarian",				"Húngaro"},
			{"italian",					"Italiano"},
			{"japanese",				"Japonés"},
			{"korean",					"Coreano"},
			{"nordic",					"Nórdico"},
			{"norwegian",				"Noruego"},
			{"polish",					"Polaco"},
			{"portuguese",				"portugués"},
			{"russian",					"Ruso"},
			{"simplifiedChinese",		"Chino simplificado"},
			{"spanish",					"Español"},
			{"swedish",					"Sueco"},
			{"traditionalChinese",		"Chino tradicional"},
			{"vietnamese",				"Vietnamita"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Borrar"},
			{"clickForMoreOptions",		"Haga clic para ver más opciones"},
			{"contentType",				"Tipo de contenido"},
			{"defaultLanguages",		"Idioma(s) predeterminado(s)"},
			{"documentation",			"Documentación"},
			{"downloadLogs",			"Registros de descargas"},
			{"downloadNow",				"Descargar ahora"},
			{"enterSearchString",		"Introduzca la cadena de búsqueda"},
			{"exportDisclosureLabel",	"Exportar información"},
			{"extras",					"Extras"},
			{"goButton",				"Ir"},
		//	{"helpMeDecide",			"Consejos para decidirme"},
			{"helpMeDecide",			"Obtener más información para tomar una decisión"},
			{"installNow",				"Instalar ahora"},
			{"language",				"Idioma"},
			{"languagePacks",			"Paquetes de idiomas"},
			{"noDownloadsMatch",		"No hay descargas que coincidan con los filtros actuales"},
			{"platform",				"Plataforma"},
			{"serialNumbers",			"Números de serie"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"No dispone de números de serie para este producto y su versión"},
			
			//Field text for which L10N text is not available
			//{"browserDownload",				"Descarga del explorador"},
			{"browserDownload",				"Descarga con navegador"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Descargas"},
			{"exportDisclosureContent",		"El software que se dispone a descargar está sujeto a las leyes y normativas sobre control de exportaciones. Al descargar este software, acepta que no accederá a, sin obtener la previa autorización por escrito de las autoridades gubernamentales competentes, exportar o volver a exportar (directa o indirectamente) ningún software descargado de este sitio Web a ningún destino, usuario final o uso final prohibidos"},
			{"legalNoticesAndTrademarks",	"Avisos legales y marcas comerciales"},
			{"myProfile",					"Mi perfil"},
			{"needHelpContactUs",			"¿Necesita ayuda? Contáctenos"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Búsqueda de productos"},
			{"privacyPolicy",				"Política de privacidad"},
			{"relatedInformation",			"Información relacionada:"},
			{"search",						"Búsqueda"},
			{"signOut",						"Salir"},
			{"subscriptionHome",			"Inicio de suscripción"},
			{"welcome",						"Pantalla de bienvenida"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Todos"},
	};
}
