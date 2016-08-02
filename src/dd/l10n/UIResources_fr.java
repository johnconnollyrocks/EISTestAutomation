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
			{"czech",					"Tch�que"},
			{"danish",					"Danois"},
			{"dutch",					"N�erlandais"},
			{"english",					"Anglais"},
			{"europeanEnglish",			"Anglais europ�en"},
			{"finnish",					"Finnois"},
			{"french",					"Fran�aise"},
			{"german",					"Allemand"},
			{"hungarian",				"Hongrois"},
			{"italian",					"Italien"},
			{"japanese",				"Japonais"},
			{"korean",					"Cor�en"},
			{"nordic",					"Nordiques"},
			{"norwegian",				"Norv�gien"},
			{"polish",					"Polonaise"},
			{"portuguese",				"Portugais"},
			{"russian",					"Russe"},
			{"simplifiedChinese",		"Chinois simplifi�"},
			{"spanish",					"Espagnol"},
			{"swedish",					"Su�dois"},
			{"traditionalChinese",		"Chinois traditionnel"},
			{"vietnamese",				"Vietnamien"},
			
			//TODO  Update this!!!
			//Language text for which L10N text is not available.
			{"britishEnglish",			"?????"},

			
			//Field text
			//TODO  NOTE that this list may not be complete!!!  See the German (de) version for a full list
			//{"browserDownload",				"T�l�chargement via le navigateur"},
			{"browserDownload",				"T�l�charger via navigateur"},
			{"clearButton",					"Effacer"},
			{"clickForMoreOptions",			"Cliquez pour plus d'options"},
			{"contentType",					"Type de contenu"},
			{"defaultLanguages",			"Langue(s) par d�faut"},
			{"documentation",				"Documentation"},
			{"downloadLogs",				"Journaux de t�l�chargement"},
			{"downloadNow",					"T�l�charger maintenant"},
			{"downloadPreference",			"?????"},
			{"downloads",					"T�l�chargements"},
			{"enterSearchString",			"Entrez une cha�ne de recherche"},
			{"exportDisclosureLabel",		"Exporter les d�tails"},
			{"exportDisclosureContent",		"Le logiciel que vous souhaitez t�l�charger est soumis aux lois et r�glementations relatives au contr�le des exportations. En le t�l�chargeant, vous renoncez � exporter ou r�exporter en connaissance de cause, directement ou indirectement, tout logiciel t�l�charg� depuis ce site Web vers une destination, une utilisation ou un utilisateur final interdits sans accord �crit pr�alable des autorit�s gouvernementales comp�tentes"},
			{"extras",						"Suppl�ments"},
			{"goButton",					"OK"},
			//{"helpMeDecide",				"Aide � la prise de d�cision"},
			{"helpMeDecide",				"M'aider � effectuer ma s�lection"},
			{"installNow",					"Installer maintenant"},
			{"language",					"Langue"},
			{"languagePacks",				"Packs linguistiques"},
			{"legalNoticesAndTrademarks",	"Mentions l�gales et marques d�pos�es"},
			{"myProfile",					"Mon profil"},
			{"needHelpContactUs",			"Besoin d'aide�? Contact"},
			{"noDownloadsMatch",			"Aucun t�l�chargement ne correspond aux filtres actuels"},
			{"nothingDownloadedToDate",		"?????"},
			{"platform",					"Plate-forme"},
			{"privacyPolicy",				"Politique de confidentialit�"},
			{"productSearch",				"Recherche de produits"},
			{"relatedInformation",			"Informations connexes:"},
			{"search",						"Recherche"},
			{"serialNumbers",				"Num�ros de s�rie"},
			{"signOut",						"D�connexion"},
			{"software",					"Logiciel"},
			{"subscriptionHome",			"Accueil de l'abonnement"},
			{"youDoNotHaveSNs",				"Vous ne disposez pas de num�ros de s�rie pour ce produit et cette version"},
			{"welcome",						"Bienvenue"},
			
			//Other text
			{"platformOptionAll",			"Tout"},
	};
}
