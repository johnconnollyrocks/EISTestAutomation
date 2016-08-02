package eurp;

import java.util.Map;

public class UserDetailsDTO {
	
	private String userName;
	private String emailAddrs;
	private Map<String, String> contractWithType;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailAddrs() {
		return emailAddrs;
	}
	public void setEmailAddrs(String emailAddrs) {
		this.emailAddrs = emailAddrs;
	}
	public Map<String, String> getContractWithType() {
		return contractWithType;
	}
	public void setContractWithType(Map<String, String> contractWithType) {
		this.contractWithType = contractWithType;
	}
	@Override
	public String toString() {
		return "UserDetailsDTO [userName=" + userName + ", emailAddrs="
				+ emailAddrs + ", contractWithType=" + contractWithType + "]";
	}
	
	
	

}
