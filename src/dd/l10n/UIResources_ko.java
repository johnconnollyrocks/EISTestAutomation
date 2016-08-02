package dd.l10n;

import java.util.ListResourceBundle;

/**
 * Korean (ko) language resource bundle for the Digital Delivery application.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class UIResources_ko extends ListResourceBundle {
	@Override
	public Object[][] getContents() {
		return contents;
	}
	
	private Object[][] contents = {
			//Languages
			{"czech",					"체코어"},
			{"danish",					"덴마크어"},
			{"dutch",					"네덜란드어"},
			{"english",					"영어"},
			{"europeanEnglish",			"유럽 영어"},
			{"finnish",					"핀란드어"},
			{"french",					"프랑스어"},
			{"german",					"독일어"},
			{"hungarian",				"헝가리어"},
			{"italian",					"이탈리아어"},
			{"japanese",				"일본어"},
			{"korean",					"한국어"},
			{"nordic",					"북유럽어"},
			{"norwegian",				"노르웨이어"},
			{"polish",					"폴란드어"},
			{"portuguese",				"포르투갈어"},
			{"russian",					"러시아어"},
			{"simplifiedChinese",		"중국어 간체"},
			{"spanish",					"스페인어"},
			{"swedish",					"스웨덴어"},
			{"traditionalChinese",		"중국어 번체"},
			{"vietnamese",				"베트남어"},
			
			//Language text for which L10N text is not available.
			{"britishEnglish",			"[Not available - do not edit]"},

			
			//Field text
			{"clearButton",				"지우기"},
			{"clickForMoreOptions",		"자세한 옵션을 보려면 클릭"},
			{"contentType",				"컨텐츠 유형:"},
			{"defaultLanguages",		"기본 언어"},
			{"documentation",			"설명서"},
			{"downloadLogs",			"다운로드 로그"},
			{"downloadNow",				"지금 다운로드"},
			{"enterSearchString",		"검색 문자열 입력"},
			{"exportDisclosureLabel",	"명세서 내보내기"},
			{"extras",					"기타"},
			{"goButton",				"이동"},
			{"helpMeDecide",			"지정 방법 표시"},
			{"installNow",				"지금 설치"},
			{"language",				"언어"},
			{"languagePacks",			"언어 팩"},
			{"noDownloadsMatch",		"현재 필터와 일치하는 다운로드 없음"},
			{"platform",				"플랫폼"},
			{"serialNumbers",			"일련 번호"},
			{"software",				"소프트웨어"},
			{"youDoNotHaveSNs",			"이 제품 및 릴리스에 대한 일련 번호가 없습니다."},
			
			//Field text for which L10N text is not available
			{"browserDownload",				"브라우저 다운로드"},
			{"downloadPreference",			"[Not available - do not edit]"},
			{"downloads",					"다운로드"},
			{"exportDisclosureContent",		"다운로드하려는 소프트웨어에는 수출 규제 법률 및 규정이 적용됩니다. 이 소프트웨어를 다운로드하면, 이 웹 사이트에서 다운로드한 모든 소프트웨어를 관할 정부 당국의 사전 서면 인증 없이 직접 또는 간접적으로 금지된 모든 대상, 최종 사용자 또는 최종 사용처에 고의로 수출하거나 재수출하지 않을 것임에 동의하게 됩니다."},
			{"legalNoticesAndTrademarks",	"법적 고지 사항 및 상표 정보"},
			{"myProfile",					"내 프로파일"},
			{"needHelpContactUs",			"도움이 필요하십니까? 연락처"},
			{"nothingDownloadedToDate",		"[Not available - do not edit]"},
			{"productSearch",				"제품 검색"},
			{"privacyPolicy",				"개인 정보 보호 정책"},
			{"relatedInformation",			"관련 정보"},
			{"search",						"검색"},
			{"signOut",						"로그아웃"},
			{"subscriptionHome",			"Subscription 홈"},
			{"welcome",						"환영"},

			
			//Other text
			//Other text for which L10N text is not available
			{"platformOptionAll",			"모두"},
	};
}
