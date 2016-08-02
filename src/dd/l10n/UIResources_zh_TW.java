package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Traditional Chinese (zh_TW) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_zh_TW extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"捷克文"},
			{"danish",					"丹麥文"},
			{"dutch",					"荷蘭文"},
			{"english",					"英文"},
			{"europeanEnglish",			"歐洲英語"},
			{"finnish",					"芬蘭文"},
			{"french",					"法文"},
			{"german",					"德文"},
			{"hungarian",				"匈牙利文"},
			{"italian",					"義大利文"},
			{"japanese",				"日文"},
			{"korean",					"韓文"},
			{"nordic",					"北歐語"},
			{"norwegian",				"挪威文"},
			{"polish",					"波蘭文"},
			{"portuguese",				"葡萄牙文"},
			{"russian",					"俄文"},
			{"simplifiedChinese",		"簡體中文"},
			{"spanish",					"西班牙文"},
			{"swedish",					"瑞典文"},
			{"traditionalChinese",		"繁體中文"},
			{"vietnamese",				"越南文"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"清除"},
			{"clickForMoreOptions",		"按一下顯示更多選項"},
			{"contentType",				"內容類型"},
			{"defaultLanguages",		"預設語言"},
			{"documentation",			"文件"},
			{"downloadLogs",			"下載記錄"},
			{"downloadNow",				"立即下載"},
			{"enterSearchString",		"輸入搜尋字串"},
			{"exportDisclosureLabel",	"匯出詳細資料"},
			{"extras",					"其他"},
			{"goButton",				"前往"},
			{"helpMeDecide",			"協助我決定"},
			{"installNow",				"立即安裝"},
			{"language",				"語言"},
			{"languagePacks",			"語言套件"},
			{"noDownloadsMatch",		"沒有符合目前篩選的任何下載"},
			{"platform",				"平台"},
			{"serialNumbers",			"序號"},
			{"software",				"軟體"},
			{"youDoNotHaveSNs",			"您沒有此產品和發行的序號"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"瀏覽器下載"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"下載"},
			{"exportDisclosureContent",		"您即将下载的软件受美国出口管制法律法规的限制。下载本软件即表示您同意：未经主管政府机构的事先书面授权，不得故意将下载自本网站的任何软件直接或间接地出口或再出口给任何受禁目的地、最终用户或使用终端。"},
			{"legalNoticesAndTrademarks",	"法律通知與商標資訊"},
			{"myProfile",					"我的紀要"},
			{"needHelpContactUs",			"需要協助？與我們聯絡"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"產品搜尋"},
			{"privacyPolicy",				"隱私權政策"},
			{"relatedInformation",			"相關資訊"},
			{"search",						"搜尋"},
			{"signOut",						"登出"},
			{"subscriptionHome",			"Subscription 首頁"},
			{"welcome",						"歡迎"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"全部"},
	};
}
