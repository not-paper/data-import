package tech.notpaper.mws.dataimport;

import java.util.Map;

public class Card extends Insertable implements Comparable<Card> {
	
	private String name;
	private String setCode;
	
	public Card(Map<String,String> data) {
		this.data = data;
		this.name = data.get("name");
		this.setCode = data.get("set_code");
	}
	
	public String getName() {
		return name;
	}
	
	public String getSetCode() {
		return setCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Card)) {
			return false;
		}
		
		Card other = (Card) obj;
		
		return this.getName().equals(other.getName()) && this.getSetCode().equals(other.getSetCode());
	}
	
	@Override
	public int hashCode() {
		return this.getName().hashCode() - this.getSetCode().hashCode();
	}

	@Override
	public int compareTo(Card other) {
		if(!(other instanceof Card)) {
			return 1;
		}
		
		int byName = this.getName().compareTo(other.getName());
		if(byName == 0) {
			return this.getSetCode().compareTo(other.getSetCode());
		} else {
			return byName;
		}
	}

	@Override
	protected String getTableName() {
		return "CARDS";
	}
}
