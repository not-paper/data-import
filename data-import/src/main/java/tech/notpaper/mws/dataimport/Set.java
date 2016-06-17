package tech.notpaper.mws.dataimport;

import java.util.Map;

public class Set extends Insertable implements Comparable<Set> {
	
	private String setName;
	private String setCode;
	
	public Set(Map<String,String> data) {
		this.data = data;
		this.setName = data.get("set_name");
		this.setCode = data.get("set_code");
	}
	
	public String getName() {
		return this.setName;
	}
	
	public String getSetCode() {
		return this.setCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Set)) {
			return false;
		}
		
		Set other = (Set) obj;
		
		return this.getSetCode().equals(other.getSetCode());
	}
	
	@Override
	public int hashCode() {
		return this.getSetCode().hashCode();
	}

	@Override
	public int compareTo(Set other) {
		if(!(other instanceof Set)) {
			return 1;
		}
		
		return this.getName().compareTo(other.getName());
	}

	@Override
	protected String getTableName() {
		return "SETS";
	}
}
