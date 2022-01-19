package librarySystem.BookEnums;

import java.util.ArrayList;

public enum EbookFormat {
	EPUB ("EPUB"),
	MOBI ("MOBI"),
	AZW3 ("AZW3"),
	PDF ("PDF");
	private String displayName;
	
	EbookFormat(String displayName) {
	    this.displayName = displayName;
	}
	
	@Override public String toString() {
			return displayName;
			}
	public static ArrayList<String> createEbookArray() {
		ArrayList<String> ebookArray = new ArrayList<String>();
		ebookArray.add(EPUB.toString());
		ebookArray.add(MOBI.toString());
		ebookArray.add(AZW3.toString());
		ebookArray.add(PDF.toString());
		return ebookArray;
		}
}