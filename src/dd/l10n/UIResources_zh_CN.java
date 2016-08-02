package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Simplified Chinese (zh_CN) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_zh_CN extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"捷克语"},
			{"danish",					"丹麦语"},
			{"dutch",					"荷兰语"},
			{"english",					"英语"},
			{"europeanEnglish",			"欧洲英语"},
			{"finnish",					"芬兰语"},
			{"french",					"法语"},
			{"german",					"德语"},
			{"hungarian",				"匈牙利语"},
			{"italian",					"意大利语"},
			{"japanese",				"日语"},
			{"korean",					"朝鲜语"},
			{"nordic",					"北欧语"},
			{"norwegian",				"挪威语"},
			{"polish",					"波兰语"},
			{"portuguese",				"葡萄牙语"},
			{"russian",					"俄语"},
			{"simplifiedChinese",		"简体中文"},
			{"spanish",					"西班牙语"},
			{"swedish",					"瑞典语"},
			{"traditionalChinese",		"繁体中文"},
			{"vietnamese",				"越南语"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"清除"},
			{"clickForMoreOptions",		"单击查看更多选项"},
			{"contentType",				"内容类型"},
			{"defaultLanguages",		"默认语言"},
			{"documentation",			"文档"},
			{"downloadLogs",			"下载日志"},
			{"downloadNow",				"立即下载"},
			{"enterSearchString",		"输入搜索字符串"},
			{"exportDisclosureLabel",	"导出详细信息"},
			{"extras",					"额外操作"},
			{"goButton",				"执行"},
			{"helpMeDecide",			"帮我决定"},
			{"installNow",				"立即安装"},
			{"language",				"语言"},
			{"languagePacks",			"语言包"},
			{"noDownloadsMatch",		"没有符合当前过滤的任何下载"},
			{"platform",				"平台"},
			{"serialNumbers",			"序列号"},
			{"software",				"软件"},
			{"youDoNotHaveSNs",			"您没有此产品和版本的序列号"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"浏览器下载"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"下载"},
			{"exportDisclosureContent",		"您即將下載之軟體受出口管制法規所規範。一經下載此軟體，即表示您同意倘事先未經管轄政府機關書面授權，絕不會故意將下載自本網站之任何軟體循直接或間接途徑出口或轉出口予任何明令禁止之目的地、終端用戶或最終用途。"},
			{"legalNoticesAndTrademarks",	"法律声明与商标"},
			{"myProfile",					"我的概况"},
			{"needHelpContactUs",			"需要帮助？联系我们"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"产品搜索"},
			{"privacyPolicy",				"隐私保护政策"},
			{"relatedInformation",			"相关信息"},
			{"search",						"搜索"},
			{"signOut",						"注销"},
			{"subscriptionHome",			"Subscription 主页"},
			{"welcome",						"欢迎"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"所有"},
	};
}
