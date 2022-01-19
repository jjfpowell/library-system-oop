package librarySystem.Book;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Books {
	private int ISBN;
	private String type;
	private String title;
	private String language;
	private String genre;
	private Date releaseDate;
	private double retailPrice;
	private int stockLevel;

	public Books(int ISBN, String type, String title, String language, String genre, Date releaseDate, double retailPrice, int stockLevel) {
		this.ISBN = ISBN;
		this.type = type;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.retailPrice = retailPrice;
		this.stockLevel = stockLevel;
	}

	public int getISBN() {
		return ISBN;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getLanguage() {
		return language;
	}

	public String getGenre() {
		return genre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}
	public String getReleaseDateStr() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        String strDate = dateFormat.format(releaseDate);
        return strDate;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public int getStockLevel() {
		return stockLevel;
	}
	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}
}
