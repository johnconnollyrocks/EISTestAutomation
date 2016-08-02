package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Vietnamese (vi) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_vi extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"Tiếng Séc"},
			{"danish",					"Tiếng Đan Mạch"},
			{"dutch",					"Tiếng Hà Lan"},
			{"english",					"Tiếng Anh"},
			{"europeanEnglish",			"Tiếng Anh ở Châu Âu"},
			{"finnish",					"Tiếng Phần Lan"},
			{"french",					"Tiếng Pháp"},
			{"german",					"Tiếng Đức"},
			{"hungarian",				"Tiếng Hungary"},
			{"italian",					"Tiếng Ý"},
			{"japanese",				"Tiếng Nhật"},
			{"korean",					"Tiếng Hàn"},
			{"nordic",					"Tiếng Bắc Âu"},
			{"norwegian",				"Tiếng Na Uy"},
			{"polish",					"Tiếng Ba Lan"},
			{"portuguese",				"Tiếng Bồ Đào Nha"},
			{"russian",					"Tiếng Nga"},
			{"simplifiedChinese",		"Tiếng Trung Giản Thể"},
			{"spanish",					"Tiếng Tây Ban Nha"},
			{"swedish",					"Tiếng Thụy Điển"},
			{"traditionalChinese",		"Tiếng Trung Phồn Thể"},
			{"vietnamese",				"Tiếng Việt"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"Xóa"},
			{"clickForMoreOptions",		"Nhấp để có nhiều tùy chọn hơn"},
			{"contentType",				"Loại Nội dung"},
			{"defaultLanguages",		"(Các) Ngôn ngữ Mặc định"},
			{"documentation",			"Tài liệu"},
			{"downloadLogs",			"Nhật ký Tải xuống"},
			{"downloadNow",				"Tải xuống Ngay bây giờ"},
			{"enterSearchString",		"Nhập Chuỗi Tìm kiếm"},
			{"exportDisclosureLabel",	"Xuất Chi tiết"},
			{"extras",					"Bổ sung"},
			{"goButton",				"Tìm"},
			{"helpMeDecide",			"Giúp Tôi Quyết định"},
			{"installNow",				"Cài đặt Ngay bây giờ"},
			{"language",				"Ngôn ngữ"},
			{"languagePacks",			"Gói Ngôn ngữ"},
			{"noDownloadsMatch",		"Không có tải xuống nào phù hợp với bộ lọc hiện tại"},
			{"platform",				"Nền tảng"},
			{"serialNumbers",			"Số Sêri"},
			{"software",				"Phần mềm"},
			{"youDoNotHaveSNs",			"Bạn không có Số Sêri cho Sản phẩm và Bản phát hành này"},
			
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
