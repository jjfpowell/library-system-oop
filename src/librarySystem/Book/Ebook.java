package librarySystem.Book;
import java.util.Date;


public class Ebook extends Books {
	private int numPages;
	private String eFormat;

	public Ebook(int ISBN, String type, String title, String language, String genre, Date releaseDate, double retailPrice,
			int stockLevel, int numPages, String eFormat) {
		super(ISBN, type, title, language, genre, releaseDate, retailPrice, stockLevel);
		this.numPages = numPages;
		this.eFormat = eFormat;
	}

	public int getNumPages() {
		return numPages;
	}

	public String geteFormat() {
		return eFormat;
	}
	
	public String createStockString() {
        String finalStr = String.valueOf(getISBN())+", "+getType()+", "+getTitle()+", "+getLanguage()+", "+getGenre()
        	+", "+getReleaseDateStr()+", "+String.valueOf(getRetailPrice())+", "+ String.valueOf(getStockLevel())+", "
        		+String.valueOf(getNumPages())+", "+geteFormat();
		return finalStr;
	}

}