package librarySystem.BookEnums;

import java.util.ArrayList;

public enum Cond {
	NEW ("new"),
	USED ("used");
	
	private String displayName;
	
	Cond(String displayName) {
	    this.displayName = displayName;
	}
	
	@Override public String toString() {
			return displayName;
			}
	
	public static ArrayList<String> createCondArray() {
		ArrayList<String> condArray = new ArrayList<String>();
		condArray.add(NEW.toString());
		condArray.add(USED.toString());
		return condArray;
		}
}
