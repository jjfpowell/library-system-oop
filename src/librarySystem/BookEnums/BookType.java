package librarySystem.BookEnums;

import java.util.ArrayList;

public enum BookType {
	AUDIOBOOK ("audiobook"),
	PAPERBACK ("paperback"),
	EBOOK ("ebook");

	private String displayName;
	
	BookType(String displayName) {
	    this.displayName = displayName;
	}
	
	@Override public String toString() { // https://stackoverflow.com/questions/8389150/java-enum-elements-with-spaces
			return displayName;
			}
		public static ArrayList<String> createBookTypeArray() {
			ArrayList<String> bookTypeArray = new ArrayList<String>();
			bookTypeArray.add(AUDIOBOOK.toString());
			bookTypeArray.add(PAPERBACK.toString());
			bookTypeArray.add(EBOOK.toString());
			return bookTypeArray;
		}
}
