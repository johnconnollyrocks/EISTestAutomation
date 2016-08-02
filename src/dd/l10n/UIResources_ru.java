package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Russian (ru) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_ru extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Чешский"},
			{"danish",					"Норвежский"},
			{"dutch",					"Датский"},
			{"english",					"Английский"},
			{"europeanEnglish",			"Европейский английский"},
			{"finnish",					"Финский"},
			{"french",					"Французский"},
			{"german",					"Немецкий"},
			{"hungarian",				"Венгерский"},
			{"italian",					"Итальянский"},
			{"japanese",				"Японский"},
			{"korean",					"японский"},
			{"nordic",					"Скандинавские"},
			{"norwegian",				"Норвежский"},
			{"polish",					"Польский"},
			{"portuguese",				"португальский"},
			{"russian",					"русский"},
			{"simplifiedChinese",		"китайский (упрощенный)"},
			{"spanish",					"испанский"},
			{"swedish",					"Шведский"},
			{"traditionalChinese",		"китайский (традиционный)"},
			{"vietnamese",				"[Not available - do not edit]"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Очистить"},
			{"clickForMoreOptions",		"Щелкните для дополнительных параметров"},
			{"contentType",				"Тип содержимого"},
			{"defaultLanguages",		"Язык(и) по умолчанию"},
			{"documentation",			"Документация"},
			{"downloadLogs",			"Журналы загрузок"},
			{"downloadNow",				"Загрузить сейчас"},
			{"enterSearchString",		"Введите строку поиска"},
			{"exportDisclosureLabel",	"Экспортировать сведения"},
			{"extras",					"Дополнения"},
			{"goButton",				"Перейти"},
			{"helpMeDecide",			"Помочь в выборе"},
			{"installNow",				"Установить сейчас"},
			{"language",				"Язык"},
			{"languagePacks",			"Языковые пакеты"},
			{"noDownloadsMatch",		"Ни одна загрузка не соответствует текущим фильтрам"},
			{"platform",				"Платформа"},
			{"serialNumbers",			"Серийные номера"},
			{"software",				"Программное обеспечение"},
			{"youDoNotHaveSNs",			"У вас нет серийных номеров для данного продукта и версии"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"Загрузка с помощью браузера"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"Загрузки"},
			{"exportDisclosureContent",		"The software you are about to download is subject to export control laws and regulations. By downloading this software, you agree that you will not knowingly, without prior written authorization from the competent government authorities, export or reexport - directly or indirectly - any software downloaded from this website to any prohibited destination, end-user, or end-use."},
			{"legalNoticesAndTrademarks",	"Официальные уведомления и товарные знаки"},
			{"myProfile",					"Мой профиль"},
			{"needHelpContactUs",			"Требуется помощь? Свяжитесь с нами"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"Поиск продукта"},
			{"privacyPolicy",				"Политика защиты конфиденциальной информации"},
			{"relatedInformation",			"Связанная информация"},
			{"search",						"Поиск"},
			{"signOut",						"Выйти"},
			{"subscriptionHome",			"Домашняя страница подписки"},
			{"welcome",						"Добро пожаловать"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"Все"},
	};
}
