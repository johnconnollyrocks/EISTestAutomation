package bornincloud;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;


import common.EISTestBase;
import common.Util;

import edu.umass.cs.benchlab.har.HarEntries;
import edu.umass.cs.benchlab.har.HarEntry;
import edu.umass.cs.benchlab.har.HarLog;
import edu.umass.cs.benchlab.har.HarPage;
import edu.umass.cs.benchlab.har.HarQueryParam;
import edu.umass.cs.benchlab.har.HarQueryString;
import edu.umass.cs.benchlab.har.tools.HarFileReader;


public class readHARFiles {

	public void readHARFile() throws Exception {
		String siteCatalystQueryValue="manage:en:products-services";
		//String pageNameVal="pageName";
		boolean foundSiteCatalystQueryVal=false;
		File harFileToRead= new File( Util.getTestRootDir()+"\\logs\\CaptureNetworkTraffic");
		String harFilePath=getHarFileToRead(harFileToRead);
		File harFileToPar= new File(harFilePath);
		HarFileReader harReader= new HarFileReader();

		HarLog log= harReader.readHarFile(harFileToPar);		
		HarEntries hEntries= log.getEntries();
		List<HarEntry> eachHarEntry= hEntries.getEntries();
		List<HarPage> myPages= log.getPages().getPages();
		for (HarPage myHarPage: myPages){

			/*System.out.println(myHarPage.getId());
			System.out.println(myHarPage.getTitle());*/

			//with in the pages get each entry
			for(HarEntry harEntry:eachHarEntry) {
				/*System.out.println(harEntry.getRequest().getQueryString());
				System.out.println(harEntry.getRequest().getUrl());*/
				String siteCatalystUrl=harEntry.getRequest().getUrl();
				//find out if it matches with smetrics site
				// get the query params and check for the response against the request
				if (siteCatalystUrl.startsWith("https://smetrics.autodesk.com/")){
				// for that HarEntry get the relevant Response
					
					String pageNameUrl_r=null;
					HarQueryString hRequestQuery= harEntry.getRequest().getQueryString();

					List<HarQueryParam> eachQueryParams= hRequestQuery.getQueryParams();

					/*for(HarQueryParam myQueryParam: eachQueryParams){*/
					for(int i=0;i<eachQueryParams.size();i++){
						
						String queryValue=eachQueryParams.get(i).getValue();
						//String queryName=eachQueryParams.get(i).getName();
						//if (queryValue.equalsIgnoreCase(siteCatalystQueryValue) && queryName.equalsIgnoreCase(pageNameVal)){
						if (queryValue.equalsIgnoreCase(siteCatalystQueryValue)){
							foundSiteCatalystQueryVal=true;
							//also get the next value of 'r' i.e URL info for the pageName key ( next entry)
							pageNameUrl_r=eachQueryParams.get(i+1).getValue();
							break;	//come out
							/*		System.out.println(queryName);
					System.out.println(queryValue);*/
					}
					//get the corresponding response
					if (foundSiteCatalystQueryVal){
						int respStatus=harEntry.getResponse().getStatus();	//should be 200*/
						switch(respStatus){
						case 200:{				
							Util.printInfo("****SUCCESS ***:The response code for the request of Sitecatalyst param: "+siteCatalystQueryValue+" for pageURL value: "+pageNameUrl_r+" is :"+respStatus);
							break;
						}
						case 302:{
							Util.printWarning("The response code for the request of Sitecatalyst param: "+siteCatalystQueryValue+"  for pageURL value: "+pageNameUrl_r+" is :"+respStatus+" please check");
							break;
						}

						case 0:{
							
							Util.printInfo("The response code for the request of Sitecatalyst param: "+siteCatalystQueryValue+"  for logout pageURL value: "+pageNameUrl_r+" is :"+respStatus+" please ignore");
							break;
						}


						default:{
							EISTestBase.fail("****FAILED ****:The request for the Sitecatalyst param: "+siteCatalystQueryValue+"  for pageURL value: "+pageNameUrl_r+"  is failed, as the response code found as :"+respStatus);
							Util.printError("****FAILED ****:The request for the Sitecatalyst param: "+siteCatalystQueryValue+"  for pageURL value: "+pageNameUrl_r+"  is failed, as the response code found as :"+respStatus);
							break;
						}
						}

					}
				}
			}

			}
		}

	}
	
	public static void main(String[] args) throws Exception{
		String jqueryScript="var myDatatrackingEle=new Array(); $('ul[class=\"nav\"]> li >a').each(function(){ myDatatrackingEle.push($(this).attr(\"data-tracking\"));}); return myDatatrackingEle.length;";
		//ArrayList<String> lsList=(ArrayList<String>) ((JavascriptExecutor)(driver)).executeScript(jqueryScript);
		new readHARFiles().readHARFile();
	}
	public String getHarFileToRead(File harFileToRead) {
		String harFileAbsPath=null;
		File[] myHarFiles=harFileToRead.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File harFileToRead, String harFileExtn) {				
				return harFileExtn.endsWith(".har");
			}
		});

		for(File myHarfile:myHarFiles){
			//read the each har file and pull the one has name containing 'customer'
			if(myHarfile.getName().startsWith("customer")){
				harFileAbsPath= myHarfile.getAbsolutePath();
			}
		}
		return harFileAbsPath;
	}


}
