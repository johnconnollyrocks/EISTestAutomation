package dd.l10n;

import java.util.ListResourceBundle;

/**
 * French (fr) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_fr extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Tchèque"},
			{"danish",					"Danois"},
			{"dutch",					"Néerlandais"},
			{"english",					"Anglais"},
			{"europeanEnglish",			"Anglais européen"},
			{"finnish",					"Finnois"},
			{"french",					"Française"},
			{"german",					"Allemand"},
			{"hungarian",				"Hongrois"},
			{"italian",					"Italien"},
			{"japanese",				"Japonais"},
			{"korean",					"Coréen"},
			{"nordic",					"Nordiques"},
			{"norwegian",				"Norvégien"},
			{"polish",					"Polonaise"},
			{"portuguese",				"Portugais"},
			{"russian",					"Russe"},
			{"simplifiedChinese",		"Chinois simplifié"},
			{"spanish",					"Espagnol"},
			{"swedish",					"Suédois"},
			{"traditionalChinese",		"Chinois traditionnel"},
			{"vietnamese",				"Vietnamien"},
			
			//TODO  Update this!!!
			//Language text for which L10N text is not available.
			{"britishEnglish",			"?????"},

			
			//Field text
			//TODO  NOTE that this list may not be complete!!!  See the German (de) version for a full list
			//{"browserDownload",				"Téléchargement via le navigateur"},
			{"browserDownload",				"Télécharger via navigateur"},
			{"clearButton",					"Effacer"},
			{"clickForMoreOptions",			"Cliquez pour plus d'options"},
			{"contentType",					"Type de contenu"},
			{"defaultLanguages",			"Langue(s) par défaut"},
			{"documentation",				"Documentation"},
			{"downloadLogs",				"Journaux de téléchargement"},
			{"downloadNow",					"Télécharger maintenant"},
			{"downloadPreference",			"?????"},
			{"downloads",					"Téléchargements"},
			{"enterSearchString",			"Entrez une chaîne de recherche"},
			{"exportDisclosureLabel",		"Exporter les détails"},
			{"exportDisclosureContent",		"Le logiciel que vous souhaitez télécharger est soumis aux lois et réglementations relatives au contrôle des exportations. En le téléchargeant, vous renoncez à exporter ou réexporter en connaissance de cause, directement ou indirectement, tout logiciel téléchargé depuis ce site Web vers une destination, une utilisation ou un utilisateur final interdits sans accord écrit préalable des autorités gouvernementales compétentes"},
			{"extras",						"Suppléments"},
			{"goButton",					"OK"},
			//{"helpMeDecide",				"Aide à la prise de décision"},
			{"helpMeDecide",				"M'aider à effectuer ma sélection"},
			{"installNow",					"Installer maintenant"},
			{"language",					"Langue"},
			{"languagePacks",				"Packs linguistiques"},
			{"legalNoticesAndTrademarks",	"Mentions légales et marques déposées"},
			{"myProfile",					"Mon profil"},
			{"needHelpContactUs",			"Besoin d'aide ? Contact"},
			{"noDownloadsMatch",			"Aucun téléchargement ne correspond aux filtres actuels"},
			{"nothingDownloadedToDate",		"?????"},
			{"platform",					"Plate-forme"},
			{"privacyPolicy",				"Politique de confidentialité"},
			{"productSearch",				"Recherche de produits"},
			{"relatedInformation",			"Informations connexes:"},
			{"search",						"Recherche"},
			{"serialNumbers",				"Numéros de série"},
			{"signOut",						"Déconnexion"},
			{"software",					"Logiciel"},
			{"subscriptionHome",			"Accueil de l'abonnement"},
			{"youDoNotHaveSNs",				"Vous ne disposez pas de numéros de série pour ce produit et cette version"},
			{"welcome",						"Bienvenue"},
			
			//Other text
			{"platformOptionAll",			"Tout"},
	};
}
