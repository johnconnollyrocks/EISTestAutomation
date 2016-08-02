package common;

/**
 * Representation of an address, as implemented in SFDC objects.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class Address implements Cloneable {
	private String street;
	private String city;
	private String stateProvince;
	private String zipPostalCode;
	private String country;
	
	public Address(String street, String city, String stateProvince, String zipPostalCode, String country) {
		this.street = street;
		this.city = city;
		this.stateProvince = stateProvince;
		this.zipPostalCode = zipPostalCode;
		this.country = country;
	}
	
	public Address(Address anAddress) {
		this.street = anAddress.getStreet();
		this.city = anAddress.getCity();
		this.stateProvince = anAddress.getStateProvince();
		this.zipPostalCode = anAddress.getZipPostalCode();
		this.country = anAddress.getCountry();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getZipPostalCode() {
		return zipPostalCode;
	}

	public void setZipPostalCode(String zipPostalCode) {
		this.zipPostalCode = zipPostalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city
				+ ", stateProvince=" + stateProvince + ", zipPostalCode=" + zipPostalCode
				+ ", country=" + country + "]";
	}
}
