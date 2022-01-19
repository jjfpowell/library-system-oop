package librarySystem.BookEnums;

import java.util.ArrayList;

public enum AudiobookFormat {
	MP3 ("MP3"),
	WMA ("WMA"),
	ACC ("ACC");
	
	private String displayName;
	
	AudiobookFormat(String displayName) {
	    this.displayName = displayName;
	}
	
	@Override public String toString() {
			return displayName;
			}
	public static ArrayList<String> createAudioArray() {
		ArrayList<String> audioArray = new ArrayList<String>();
		audioArray.add(MP3.toString());
		audioArray.add(WMA.toString());
		audioArray.add(ACC.toString());
		return audioArray;
		}
}
