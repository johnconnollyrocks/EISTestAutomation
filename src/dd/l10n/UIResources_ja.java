package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Template language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_ja extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"チェコ語"},
			{"danish",					"デンマーク語"},
			{"dutch",					"オランダ語"},
			{"english",					"英語"},
			{"europeanEnglish",			"ヨーロッパ英語"},
			{"finnish",					"フィンランド語"},
			{"french",					"フランス語"},
			{"german",					"ドイツ語"},
			{"hungarian",				"ハンガリー語"},
			{"italian",					"イタリア語"},
			{"japanese",				"日本語"},
			{"korean",					"韓国語"},
			{"nordic",					"スカンジナビア語"},
			{"norwegian",				"ノルウェー語"},
			{"polish",					"ポーランド語"},
			{"portuguese",				"ポルトガル語"},
			{"russian",					"ロシア語"},
			{"simplifiedChinese",		"簡体字中国語"},
			{"spanish",					"スペイン語"},
			{"swedish",					"スウェーデン語"},
			{"traditionalChinese",		"繁体字中国語"},
			{"vietnamese",				"ベトナム語"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"クリア"},
			{"clickForMoreOptions",		"その他のオプションについてはクリックしてください"},
			{"contentType",				"コンテンツ タイプ"},
			{"defaultLanguages",		"既定の言語"},
			{"documentation",			"ドキュメント"},
			{"downloadLogs",			"ダウンロード ログ"},
			{"downloadNow",				"今すぐダウンロード"},
			{"enterSearchString",		"検索文字列を入力"},
			{"exportDisclosureLabel",	"開示のエクスポート"},
			{"extras",					"エクストラ"},
			{"goButton",				"移動"},
			{"helpMeDecide",			"[Not available - do not edit]"},
			{"installNow",				"今すぐインストール"},
			{"language",				"言語"},
			{"languagePacks",			"[Not available - do not edit]"},
			{"noDownloadsMatch",		"現在のフィルタに一致するダウンロードはありません"},
			{"platform",				"プラットフォーム"},
			{"serialNumbers",			"シリアル番号"},
			{"software",				"ソフトウェア"},
			{"youDoNotHaveSNs",			"この製品とリリースのシリアル番号がない"},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"ブラウザ ダウンロード"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"ダウンロード"},
			{"exportDisclosureContent",		"ダウンロードするソフトウェアは、輸出管理法令の対象になります。本ソフトウェアをダウンロードすると、意図的であるかどうかを問わず、管轄権を有する政府機関からの事前の書面による承認なく、本 Web サイトからダウンロードしたソフトウェアを直接的にまたは間接的に禁止された対象地、エンドユーザ、または最終用途のために輸出または再輸出しないことに同意することになります。"},
			{"legalNoticesAndTrademarks",	"法務からのお知らせと商標"},
			{"myProfile",					"マイ プロファイル"},
			{"needHelpContactUs",			"お問い合わせ先お問い合わせ"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"製品検索"},
			{"privacyPolicy",				"プライバシー ポリシー"},
			{"relatedInformation",			"関連情報"},
			{"search",						"検索"},
			{"signOut",						"サイン アウト"},
			{"subscriptionHome",			"Subscription ホーム"},
			{"welcome",						"ようこそ"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"ã�™ã�¹ã�¦"},
	};
}
