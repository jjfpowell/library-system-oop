package librarySystem.Book;
import java.util.Date;

public class Paperback extends Books{
	private int numPages;
	private String bookCond;

	public Paperback(int ISBN, String type, String title, String language, String genre, Date releaseDate, double retailPrice,
			int stockLevel, int numPages, String bookCond) {
		super(ISBN, type, title, language, genre, releaseDate, retailPrice, stockLevel);
		this.numPages = numPages;
		this.bookCond = bookCond;
		// TODO Auto-generated constructor stub
	}
	public int getNumPages() {
		return numPages;
	}

	public String getBookCond() {
		return bookCond;
	}
	
	public String createStockString() {
        String finalStr = String.valueOf(getISBN())+", "+getType()+", "+getTitle()+", "+getLanguage()+", "+getGenre()
        	+", "+getReleaseDateStr()+", "+String.valueOf(getRetailPrice())+", "+ String.valueOf(getStockLevel())+", "
        		+String.valueOf(getNumPages())+", "+getBookCond();
		return finalStr;
	}

}




