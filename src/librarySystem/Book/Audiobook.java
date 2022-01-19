package librarySystem.Book;
import java.util.Date;


public class Audiobook extends Books {
	private double listeningLength;
	private String audioFormat;
	
	public Audiobook(int ISBN, String type, String title, String language, String genre, Date releaseDate, double retailPrice,
			int stockLevel, double listeningLength, String audioFormat) {
		super(ISBN, type, title, language, genre, releaseDate, retailPrice, stockLevel);
		this.listeningLength = listeningLength;
		this.audioFormat = audioFormat;
		// TODO Auto-generated constructor stub
	}

	public double getListeningLength() {
		return listeningLength;
	}

	public String getAudioFormat() {
		return audioFormat;
	}
	public String createStockString() {
        String finalStr = String.valueOf(getISBN())+", "+getType()+", "+getTitle()+", "+getLanguage()+", "+getGenre()
        	+", "+getReleaseDateStr()+", "+String.valueOf(getRetailPrice())+", "+ String.valueOf(getStockLevel())+", "
        		+String.valueOf(getListeningLength())+", "+getAudioFormat();
		return finalStr;
	}
}
