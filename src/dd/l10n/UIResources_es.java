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
			{"danish",					"Dan�s"},
			{"dutch",					"Neerland�s"},
			{"english",					"Ingl�s"},
			{"europeanEnglish",			"Ingl�s europeo"},
			{"finnish",					"Fin�s"},
			{"french",					"Franc�s"},
			{"german",					"Alem�n"},
			{"hungarian",				"H�ngaro"},
			{"italian",					"Italiano"},
			{"japanese",				"Japon�s"},
			{"korean",					"Coreano"},
			{"nordic",					"N�rdico"},
			{"norwegian",				"Noruego"},
			{"polish",					"Polaco"},
			{"portuguese",				"portugu�s"},
			{"russian",					"Ruso"},
			{"simplifiedChinese",		"Chino simplificado"},
			{"spanish",					"Espa�ol"},
			{"swedish",					"Sueco"},
			{"traditionalChinese",		"Chino tradicional"},
			{"vietnamese",				"Vietnamita"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Borrar"},
			{"clickForMoreOptions",		"Haga clic para ver m�s opciones"},
			{"contentType",				"Tipo de contenido"},
			{"defaultLanguages",		"Idioma(s) predeterminado(s)"},
			{"documentation",			"Documentaci�n"},
			{"downloadLogs",			"Registros de descargas"},
			{"downloadNow",				"Descargar ahora"},
			{"enterSearchString",		"Introduzca la cadena de b�squeda"},
			{"exportDisclosureLabel",	"Exportar informaci�n"},
			{"extras",					"Extras"},
			{"goButton",				"Ir"},
		//	{"helpMeDecide",			"Consejos para decidirme"},
			{"helpMeDecide",			"Obtener m�s informaci�n para tomar una decisi�n"},
			{"installNow",				"Instalar ahora"},
			{"language",				"Idioma"},
			{"languagePacks",			"Paquetes de idiomas"},
			{"noDownloadsMatch",		"No hay descargas que coincidan con los filtros actuales"},
			{"platform",				"Plataforma"},
			{"serialNumbers",			"N�meros de serie"},
			{"software",				"Software"},
			{"youDoNotHaveSNs",			"No dispone de n�meros de serie para este producto y su versi�n"},
			
			//Field text for which L10N text is not available
			//{"browserDownload",				"Descarga del explorador"},
			{"browserDownload",				"Descarga con navegador"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Descargas"},
			{"exportDisclosureContent",		"El software que se dispone a descargar est� sujeto a las leyes y normativas sobre control de exportaciones. Al descargar este software, acepta que no acceder� a, sin obtener la previa autorizaci�n por escrito de las autoridades gubernamentales competentes, exportar o volver a exportar (directa o indirectamente) ning�n software descargado de este sitio Web a ning�n destino, usuario final o uso final prohibidos"},
			{"legalNoticesAndTrademarks",	"Avisos legales y marcas comerciales"},
			{"myProfile",					"Mi perfil"},
			{"needHelpContactUs",			"�Necesita ayuda? Cont�ctenos"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"B�squeda de productos"},
			{"privacyPolicy",				"Pol�tica de privacidad"},
			{"relatedInformation",			"Informaci�n relacionada:"},
			{"search",						"B�squeda"},
			{"signOut",						"Salir"},
			{"subscriptionHome",			"Inicio de suscripci�n"},
			{"welcome",						"Pantalla de bienvenida"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Todos"},
	};
}
