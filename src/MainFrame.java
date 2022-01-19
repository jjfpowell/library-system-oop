import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import librarySystem.Book.Audiobook;
import librarySystem.Book.Books;
import librarySystem.Book.Ebook;
import librarySystem.Book.Paperback;
import librarySystem.User.Address;
import librarySystem.User.Admin;
import librarySystem.User.Customer;
import librarySystem.User.User;
import librarySystem.BookEnums.BookType;
import librarySystem.BookEnums.Genre;
import librarySystem.BookEnums.Language;
import librarySystem.BookEnums.Cond;
import librarySystem.BookEnums.EbookFormat;
import librarySystem.BookEnums.AudiobookFormat;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private JPanel contentPane;
	private static ArrayList<String> usernameArray;
	private JComboBox<Object> userSelect;
	private static List<User> userList;
	private static List<Books> bookList;
	private static List<Books> shoppingBasket;
	private JTextField isbnField;
	private JTextField titleField;
	private JTextField priceField;
	private JTextField pagesField;
	private JFormattedTextField dateField;
	private JLabel Status;
	private JLabel statusOfAdding;
	private JComboBox<Object> formatSelect;
	private JComboBox<Object> typeSelect;
	private JComboBox<Object> langSelect;
	private JComboBox<Object> genreSelect;
	private JTextField stockField;
	private JTextField emailField;
	private JTextField cardNumField;
	private JTextField cvvField;

	/**
	 * Launch the application.
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		generateUsers();
		importBookList();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(); // Create MainFrame for GUI
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setTitle("Library System\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		getContentPane().setLayout(new BorderLayout());
		setSize(950,800);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		contentPane.setBounds(0, 0, 950,800);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		JPanel loginPane = new JPanel();
		loginPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Login", null, loginPane, null);
		tabbedPane.setSelectedIndex(0);
		tabbedPane.setEnabledAt(0, false);
		loginPane.setSize(940, 300);
		loginPane.setLayout(null);
		
		userSelect = new JComboBox<Object>((usernameArray.toArray())); // Create drop down menu box for users
		userSelect.setBounds(359, 130, 200, 30);
		loginPane.add(userSelect);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(416, 169, 86, 45);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(User user:userList) { // For each user is in user list
					if(user.getUsername().equals(userSelect.getSelectedItem())) { // If userRole is equal the selected
						if(user.getRole().equals("admin")) { 
							tabbedPane.setSelectedIndex(1);
						}
						else if (user.getRole().equals("customer")) {
							tabbedPane.setSelectedIndex(2);
						}
					}
				};
			}
		});
		loginButton.setHorizontalAlignment(SwingConstants.CENTER);
		loginPane.add(loginButton);
		
		JLabel libraryLabel = new JLabel("Library System");
		libraryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		libraryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		libraryLabel.setBounds(359, 54, 201, 36);
		loginPane.add(libraryLabel);
		
		JLabel userLoginLabel = new JLabel("Select User:");
		userLoginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		userLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLoginLabel.setBounds(394, 102, 130, 16);
		loginPane.add(userLoginLabel);
		
		JPanel adminPane = new JPanel();
		adminPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Admin", null, adminPane, null);
		adminPane.setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel(null, new String [] {"ISBN","Type","Title","Language","Genre","Date","Price","Stock","Pages/Length","Format/Condition"});
		model.setRowCount(0);
		addRows(model,bookList);
		
		JTable table = new JTable(model);
		table.setRowHeight(20);
		table.setGridColor(Color.BLACK);
		JScrollPane scrollPaneAdmin = new JScrollPane(table);
		adminPane.add(scrollPaneAdmin);
		scrollPaneAdmin.setBounds(9, 20, 900, 400);
		
		JPanel addBookPane = new JPanel();
		addBookPane.setBackground(Color.LIGHT_GRAY);
		adminPane.add(addBookPane);
		addBookPane.setBounds(9, 430, 900, 284);
		addBookPane.setLayout(null);
		
		isbnField = new JTextField();
		isbnField.setBounds(307, 29, 140, 30);
		addBookPane.add(isbnField);
		isbnField.setColumns(10);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(234, 35, 61, 16);
		addBookPane.add(lblISBN);
		
		formatSelect = new JComboBox<Object>((AudiobookFormat.createAudioArray()).toArray());
		formatSelect.setBounds(534, 136, 140, 30);
		addBookPane.add(formatSelect);
		
		JLabel lblFormat = new JLabel("Format:");
		lblFormat.setBounds(461, 142, 75, 16);
		addBookPane.add(lblFormat);
		
		JLabel lblPages = new JLabel("Length:");
		lblPages.setBounds(461, 107, 90, 16);
		addBookPane.add(lblPages);
		
		typeSelect = new JComboBox<Object>((BookType.createBookTypeArray()).toArray());
		typeSelect.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent event) { // If Combobox is changed, update variable to match selected book type
		    	@SuppressWarnings("rawtypes")
				JComboBox typeSelect = (JComboBox) event.getSource();
		    	Object typeSelected = typeSelect.getSelectedItem();
		 
		    	if(typeSelected.equals("audiobook")) { 
		    		formatSelect.setModel(new DefaultComboBoxModel<Object>((AudiobookFormat.createAudioArray()).toArray()));
		    		lblPages.setText("Length:");
		    		lblFormat.setText("Format:");
		    	}
		    	else if(typeSelected.equals("paperback")) {
		    		formatSelect.setModel(new DefaultComboBoxModel<Object>((Cond.createCondArray()).toArray()));
		    		lblFormat.setText("Condition:");
		    		lblPages.setText("Pages:");
		    	}
		    	else if(typeSelected.equals("ebook")) {
		    		formatSelect.setModel(new DefaultComboBoxModel<Object>((EbookFormat.createEbookArray()).toArray()));
		    		lblPages.setText("Pages:");
		    		lblFormat.setText("Format:");
		    	}
		    }
		});
		typeSelect.setBounds(307, 64, 140, 30);
		addBookPane.add(typeSelect);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(234, 70, 61, 16);
		addBookPane.add(lblType);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(234, 107, 61, 16);
		addBookPane.add(lblTitle);
		
		titleField = new JTextField();
		titleField.setColumns(10);
		titleField.setBounds(307, 100, 140, 30);
		addBookPane.add(titleField);
		
		JLabel lblLanguage = new JLabel("Language:");
		lblLanguage.setBounds(234, 142, 75, 16);
		addBookPane.add(lblLanguage);
		
		langSelect = new JComboBox<Object>((Language.createLangArray()).toArray());
		langSelect.setBounds(307, 136, 140, 30);
		addBookPane.add(langSelect);
		
		JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setBounds(234, 177, 75, 16);
		addBookPane.add(lblGenre);
		
		genreSelect = new JComboBox<Object>((Genre.createGenreArray()).toArray());
		genreSelect.setBounds(307, 171, 140, 30);
		addBookPane.add(genreSelect);
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		dateField = new JFormattedTextField(df);
		dateField.setBounds(534, 30, 140, 30);
		addBookPane.add(dateField);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(461, 36, 61, 16);
		addBookPane.add(lblDate);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(461, 72, 61, 16);
		addBookPane.add(lblPrice);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(534, 65, 140, 30);
		addBookPane.add(priceField);
		
		pagesField = new JTextField();
		pagesField.setColumns(10);
		pagesField.setBounds(534, 100, 140, 30);
		addBookPane.add(pagesField);
		
		JComboBox<Object> formatSelect = new JComboBox<Object>();
		formatSelect.setBounds(305, 136, 140, 30);
		addBookPane.add(formatSelect);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(389, 201, 117, 29);
		addBookPane.add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Status.setText(addBookValidation()); // On submit validate data
				try {
					addNewBook();
					model.setRowCount(0);
					addRows(model,bookList);
					Status.setText("Success!");
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		Status = new JLabel();
		Status.setText("Status...");
		Status.setBounds(297, 235, 305, 26);
		addBookPane.add(Status);
		
		JLabel lblNewLabel = new JLabel("Add book");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel.setBounds(400, 6, 99, 16);
		addBookPane.add(lblNewLabel);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setBounds(462, 177, 90, 16);
		addBookPane.add(lblQuantity);
		
		stockField = new JTextField();
		stockField.setColumns(10);
		stockField.setBounds(535, 170, 140, 30);
		addBookPane.add(stockField);
		
		JButton btnAdminLogout = new JButton("Log out");
		btnAdminLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
			}
		});
		btnAdminLogout.setBounds(6, 1, 117, 30);
		addBookPane.add(btnAdminLogout);
		
		JPanel custoPanel = new JPanel();
		custoPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Customer", null, custoPanel, null);
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		custoPanel.setLayout(null);
		custoPanel.setSize(940, 300);
		
		JTabbedPane customerTabs = new JTabbedPane(JTabbedPane.TOP);
		customerTabs.setForeground(Color.BLACK);
		customerTabs.setBounds(0, 0, 919, 812);
		custoPanel.add(customerTabs);
		
		JPanel storePane = new JPanel();
		storePane.setBackground(Color.LIGHT_GRAY);
		storePane.setForeground(Color.WHITE);
		customerTabs.addTab("Store", null, storePane, null);
		storePane.setSize(940, 280);
		storePane.setLayout(null);
		
		JTable tableStore = new JTable(model);
		tableStore.setGridColor(Color.BLACK);
		JScrollPane scrollPaneStore = new JScrollPane(tableStore);
		storePane.add(scrollPaneStore);
		scrollPaneStore.setBounds(2, 0, 894, 400);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(2, 415, 894, 270);
		storePane.add(panel);
		panel.setLayout(null);
		
		JPanel cartPane = new JPanel();
		cartPane.setBackground(Color.LIGHT_GRAY);
		customerTabs.addTab("Cart", null, cartPane, null);
		cartPane.setSize(940, 280);
		cartPane.setLayout(null);
		
		DefaultTableModel modelCart = new DefaultTableModel(null, new String [] {"ISBN","Type","Title","Language","Genre",
				"Date","Price","Quantity","Pages/Length","Format/Condition"});
		modelCart.setRowCount(0);
		shoppingBasket = new ArrayList<Books>();
		JTable tableCart = new JTable(modelCart);
		tableCart.setGridColor(Color.BLACK);
		tableCart.setRowHeight(20);
		tableCart.setShowHorizontalLines(true);
		tableCart.setShowVerticalLines(true);
		
		JScrollPane scrollPaneCart = new JScrollPane(tableCart);
		cartPane.add(scrollPaneCart);
		scrollPaneCart.setBounds(2, 0, 894, 200);
		
		statusOfAdding = new JLabel("");
		statusOfAdding.setBounds(424, 209, 117, 16);
		panel.add(statusOfAdding);
		
		JPanel paymentPane = new JPanel();
		paymentPane.setBackground(Color.LIGHT_GRAY);
		paymentPane.setBounds(2, 215, 894, 466);
		cartPane.add(paymentPane);
		paymentPane.setLayout(null);
		
		JLabel lblTotalVal = new JLabel("");
		lblTotalVal.setBounds(460, 200, 86, 16);
		paymentPane.add(lblTotalVal);
		
		JButton btnAddCart = new JButton("Add to cart");
		btnAddCart.setBounds(377, 40, 140, 40);
		panel.add(btnAddCart);
		
		JLabel lblInstruct = new JLabel("Select a book from the table above using the mouse and then click \"Add to cart\".");
		lblInstruct.setBounds(190, 16, 513, 16);
		panel.add(lblInstruct);
		
		JLabel lblGenre_1 = new JLabel("Genre:");
		lblGenre_1.setBounds(377, 117, 46, 16);
		panel.add(lblGenre_1);
		
		JComboBox<Object> genreSelect_2 = new JComboBox<Object>((Genre.createGenreArray()).toArray());
		genreSelect_2.setBounds(421, 111, 140, 30);
		panel.add(genreSelect_2);
		
		JButton btnSorter = new JButton("Order by Genre");
		btnSorter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFilterGenre(model, tableStore, genreSelect_2.getSelectedItem(),4);
			}
		});
		btnSorter.setBounds(377, 145, 140, 29);
		panel.add(btnSorter);
		
		JButton btnSorterDefault = new JButton("Default");
		btnSorterDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableStore.setRowSorter(null); // Resets table sort
			}
		});
		
		btnSorterDefault.setBounds(377, 177, 140, 29);
		panel.add(btnSorterDefault);
		
		JLabel ordeLbl = new JLabel("Order list by:");
		ordeLbl.setBounds(406, 90, 82, 16);
		panel.add(ordeLbl);
		
		JLabel lblNewLabel_1 = new JLabel("Status:");
		lblNewLabel_1.setBounds(377, 209, 46, 16);
		panel.add(lblNewLabel_1);
		
		btnAddCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // Handles Add to cart request
				statusOfAdding.setText("");
				String selectedISBN = tableStore.getModel().getValueAt(tableStore.getSelectedRow(), 0).toString();
				addToCart(modelCart,model,selectedISBN,tableCart,shoppingBasket);
				calculateBalance(tableCart, lblTotalVal);
			}
		});
		
		JLabel lblPayment = new JLabel("Payment");
		lblPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblPayment.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblPayment.setBounds(401, 6, 92, 23);
		paymentPane.add(lblPayment);
		
		JRadioButton rdbtnPayPal = new JRadioButton("PayPal");
		rdbtnPayPal.setSelected(true);
		rdbtnPayPal.setBounds(365, 34, 71, 23);
		paymentPane.add(rdbtnPayPal);
		rdbtnPayPal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // Sets which payment fields are editable
				if(e.getSource() == rdbtnPayPal){
					emailField.setEditable(true);
					cardNumField.setEditable(false);
					cvvField.setEditable(false);
					cardNumField.setText("");
					cvvField.setText("");
		           }
		        }
		 });
		
		JRadioButton rdbtnC = new JRadioButton("Credit Card");
		rdbtnC.setBounds(460, 34, 103, 23);
		paymentPane.add(rdbtnC);
		rdbtnC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // Sets which payment fields are editable
				if(e.getSource() == rdbtnC){
					cardNumField.setEditable(true);
					cvvField.setEditable(true);
					emailField.setEditable(false);
					emailField.setText("");
		           }
		        }
		});
		
		ButtonGroup paymentGroup = new ButtonGroup();
		paymentGroup.add(rdbtnC);
		paymentGroup.add(rdbtnPayPal);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(355, 77, 61, 16);
		paymentPane.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setText("");
		emailField.setBounds(460, 75, 130, 26);
		paymentPane.add(emailField);
		emailField.setColumns(10);
		
		JLabel lblCardNumber = new JLabel("Card Number:");
		lblCardNumber.setBounds(355, 103, 97, 16);
		paymentPane.add(lblCardNumber);
		
		cardNumField = new JTextField();
		cardNumField.setColumns(10);
		cardNumField.setBounds(460, 98, 130, 26);
		paymentPane.add(cardNumField);
		cardNumField.setEditable(false);
		
		JLabel lblCVV = new JLabel("Security Code:");
		lblCVV.setBounds(355, 131, 115, 16);
		paymentPane.add(lblCVV);
		
		cvvField = new JTextField();
		cvvField.setColumns(10);
		cvvField.setBounds(460, 127, 130, 26);
		paymentPane.add(cvvField);
		cvvField.setEditable(false);
		
		JLabel lblOutputM = new JLabel("");
		lblOutputM.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblOutputM.setBounds(21, 190, 289, 26);
		paymentPane.add(lblOutputM);
		
		JButton btnPay = new JButton("Pay");
		btnPay.setBounds(335, 159, 117, 29);
		paymentPane.add(btnPay);
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // Determines which payment method is used 
				boolean validationComplete = false;
				int payType = 0;
				if(rdbtnC.isSelected()) {
					if(validCard()) {
						validationComplete = true;
						payType = 1;
					}
				}
				else if(rdbtnPayPal.isSelected()) {
					if(validEmail(emailField.getText())){
						validationComplete = true;
					}
				}
				if(validationComplete){
					updateStockFile();
					try {
						importBookList();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					model.setRowCount(0);
					addRows(model,bookList);
					writeToLog(tableCart,payType,"purchased",modelCart);
					shoppingBasket.clear();
					String bookPrice = lblTotalVal.getText();
					lblOutputM.setText("");
					if(payType==1) {
						lblOutputM.setText(bookPrice + " paid using Credit Card");
					}
					else {
						lblOutputM.setText(bookPrice + " paid using Pay Pal");
					}
				}
				else {
					lblOutputM.setText("Error with card details. Please try again.");
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel Basket");
		btnCancel.setBounds(470, 159, 117, 29);
		paymentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // Logout request
				int payType = 0;
				if(rdbtnC.isSelected()) {
						payType = 1;
					}
				writeToLog(tableCart,payType,"cancelled",modelCart);
				model.setRowCount(0);
				addRows(model,bookList);
				shoppingBasket.clear();
				lblTotalVal.setText("£0.00");
			}
		});
		
		JLabel lblTotalTxt = new JLabel("Basket Total:");
		lblTotalTxt.setBounds(355, 200, 86, 16);
		paymentPane.add(lblTotalTxt);
		
		JButton btnCustoLogout = new JButton("Log out");
		btnCustoLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // Logout request
				if(shoppingBasket.size()!=0) {
					int payType = 0;
					if(rdbtnC.isSelected()) {
							payType = 1;
						}
					writeToLog(tableCart,payType,"cancelled",modelCart);
					model.setRowCount(0);
					addRows(model,bookList);
					shoppingBasket.clear();
				}
				tabbedPane.setSelectedIndex(0);
			}
		});
		btnCustoLogout.setBounds(6, 6, 117, 30);
		panel.add(btnCustoLogout);
		
		JButton btnCustoLogout_1 = new JButton("Log out");
		btnCustoLogout_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // Logout request
				if(shoppingBasket.size()!=0) { 
					int payType = 0;
					if(rdbtnC.isSelected()) {
							payType = 1;
						}
					writeToLog(tableCart,payType,"cancelled",modelCart);
					model.setRowCount(0);
					addRows(model,bookList);
					shoppingBasket.clear();
				}
				tabbedPane.setSelectedIndex(0);
			}
		});
		btnCustoLogout_1.setBounds(6, 6, 117, 30);
		paymentPane.add(btnCustoLogout_1);
	}
	
	public static void AddFilterGenre(DefaultTableModel model, JTable tbl, Object selectedField, Integer SearchColumnIndex) { // Create the genre table filter
		final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model); // Create the table sorter
        tbl.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("^(?i)"+String.valueOf(selectedField), SearchColumnIndex));
	}

	public void calculateBalance(JTable tableCart, JLabel lblTotalVal) { // Calculate the balance for everything in the table 
		double bal = 00.00;
		for(int i = 0;i<tableCart.getModel().getRowCount();i++){ // For each row in the table model
			double price = Double.parseDouble(String.valueOf(tableCart.getModel().getValueAt(i,6)));
			int quant = Integer.parseInt(String.valueOf(tableCart.getModel().getValueAt(i,7)));
			double thisISBN = price * quant;
			bal = bal + thisISBN;
		}
		lblTotalVal.setText("£"+String.valueOf(bal)); // Set update label with balance on the cart page
	}
	
	public void clearStockFile() { // Clears the stock file
		try {
			FileWriter outputFile = new FileWriter("Stock.txt",false);
			BufferedWriter bw = new BufferedWriter(outputFile);
			bw.write("");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateStockFile() { // Writes changes made having completed a purchase
		clearStockFile();
		try {
			FileWriter outputFile = new FileWriter("Stock.txt",true);
			BufferedWriter bw = new BufferedWriter(outputFile);
			for(int i=0;i<bookList.size();i++){
				String toAdd = null;
				if(bookList.get(i).getType().equals("paperback")){
					toAdd = ((Paperback) bookList.get(i)).createStockString();
				}
				else if(bookList.get(i).getType().equals("audiobook")){
					toAdd = ((Audiobook) bookList.get(i)).createStockString();
				}
				else if(bookList.get(i).getType().equals("ebook")){
					toAdd = ((Ebook) bookList.get(i)).createStockString();
				}
				if(i!=bookList.size()-1){
					bw.write(toAdd+"\n");
				}
				else {
					bw.write(toAdd);
				}
			}
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}	
	}
	
	// Sets up variables to create a log file entry
	public void writeToLog(JTable tableCart, int payType, String status, DefaultTableModel modelCart) { 
		String logUserID, logPost, logISBN, logPrice, logQuant, logMeth,logStatus,logDate;
		
		SimpleDateFormat formatterToday = new SimpleDateFormat("dd-MM-yyyy");  
	    Date date = new Date();  
	    formatterToday.format(date);
	    
	    if(payType==0) {
	    	logMeth = "PayPal";
		}
		else {
			logMeth = "Credit Card";
		}
	    logUserID = null;
	    logPost = null;
	    for(User user:userList) {
	    	if(user.getUsername().equals(userSelect.getSelectedItem())) {
	    		logUserID = String.valueOf(user.getUserId());
	    		logPost = String.valueOf(user.getPostcode());
	    	}
	    }
	    
		logStatus = status;
		logDate = String.valueOf(formatterToday.format(date));
	    
		for(int i=0;i<shoppingBasket.size();i++) { // Iterates through the current shopping basket. Allows comparison on the next line
			for(int j = 0;j<tableCart.getModel().getRowCount();j++){
				if(Integer.parseInt((String) tableCart.getModel().getValueAt(j,0))==(shoppingBasket.get(i).getISBN())) {
					logISBN = String.valueOf(tableCart.getModel().getValueAt(j,0));
					logPrice = String.valueOf(tableCart.getModel().getValueAt(j,6));
					logQuant = String.valueOf(tableCart.getModel().getValueAt(j,7));
					createLogEntry(logUserID, logPost, logISBN, logPrice, logQuant, logMeth,logStatus,logDate); // Passes variables to be written to the file
					}
				}
		}
		if(logStatus.equals("purchased")) { // Clears cart table
			modelCart.setRowCount(0);
		}
		else if(logStatus.equals("cancelled")) {
			resetStock(tableCart); // If cancelled, reset stock levels of books
			modelCart.setRowCount(0);
		}
	}
	
	public void resetStock(JTable tableCart) { // Re-adds any stock that was added to a basket in the event of cancellation
		for(Books book:bookList) {
			if(shoppingBasket.size()==0) {
				break;
			}
			else {
				for(int i = 0;i<tableCart.getModel().getRowCount();i++){ 
					if(Integer.parseInt(String.valueOf(tableCart.getModel().getValueAt(i,0)))==book.getISBN()){
						int curr = Integer.parseInt(String.valueOf(tableCart.getModel().getValueAt(i,7))); 
						int tempSt = book.getStockLevel()+curr;
						book.setStockLevel(tempSt);
					}	
				}
			}
		}
	}
	
	// Writes the log entry to the ActivityLog.txt
	public void createLogEntry(String logUserID, String logPost, String logISBN, String logPrice, String logQuant, String logMeth, String logStatus, String logDate) {
		String logString = logUserID+", "+logPost+", "+logISBN+", "+logPrice+", "+logQuant+", "+logMeth+", "+logStatus+", "+logDate;
		try {
		FileWriter outputFile = new FileWriter("ActivityLog.txt",true);
		BufferedWriter bw = new BufferedWriter(outputFile);
		bw.write(logString+"\n");
		bw.close();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	// Regex variable to allow for email validation
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validEmail(String emailStr) { // Validate email
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
	
	public boolean validCard() { // Check if card details are vaild
		boolean validCard = false;
		try {
			Integer.parseInt(cardNumField.getText());
			Integer.parseInt(cvvField.getText());
			if((cardNumField.getText().length()!=6)||(cvvField.getText().length()!=3)) {
				validCard = false;
			}
			else {
				validCard = true; 
			}
		}
		catch (NumberFormatException e) {
			validCard = false;
		}
		return validCard;
	}
	
	// Adds a book to cart, updates stock
	public void addToCart(DefaultTableModel cartModel, DefaultTableModel fullStoreModel, String selectedISBN, JTable table, List<Books> shoppingBasket) {
		for(Books book:bookList) {
			if((book.getISBN()==Integer.parseInt(selectedISBN))&&(book.getStockLevel()!=0)) { // Find correct book from bookList and check its in stock
				if(shoppingBasket.size()==0) { // If shopping basket is empty
					shoppingBasket.add(book); // Add instance of that book to the basket/instance
					addRows(cartModel,shoppingBasket); // Add rows to Cart table
					table.getModel().setValueAt(String.valueOf(1),0,7); // Set quantity to 1
						}
				else { // If basket is not empty
					boolean alreadyAdded = false;
					for(int j = 0;j<shoppingBasket.size();j++) { // For each book already in the cart
						if(book.getISBN()==shoppingBasket.get(j).getISBN()){ // Check if book is already added
							alreadyAdded = true;
						}
					}
						List<Object> preQuants = new ArrayList<Object>(); // Create list to hold current quantities
						if(alreadyAdded==true) { // Check if book has already been added to basket
								for(int i = 0;i<table.getModel().getRowCount();i++){ // Loop through ISBN's in the table
									if(table.getModel().getValueAt(i,0).equals(selectedISBN)) { // When the ISBN equals the ISBN of the book we are trying to add
										int quantInt = (Integer.parseInt((String) table.getModel().getValueAt(i,7)))+1; // Get current value and add 1
										table.getModel().setValueAt(String.valueOf(quantInt),i,7); // Display updated quantity
										}
									}
						}
						else { // If book is not already added to the table
							shoppingBasket.add(book); // Add to the basket array
							for(int i = 0;i<table.getModel().getRowCount();i++) { // Loop through the table
								preQuants.add(table.getModel().getValueAt(i,7)); // Add to array
							}
							cartModel.setRowCount(0); // Clear existing data
							addRows(cartModel,shoppingBasket);
							preQuants.add(String.valueOf(1)); // Set new entry
							for(int i = 0;i<preQuants.size();i++) { // Rewrite previous quantities
								table.getModel().setValueAt(String.valueOf(preQuants.get(i)),i,7);
							}
						}
				}
				book.setStockLevel(book.getStockLevel()-1); // Update stock level in instance of the book
				fullStoreModel.setRowCount(0); // Update model in store and adminPane
				addRows(fullStoreModel,bookList);
				statusOfAdding.setText("Added to cart");
	    	}
		}
	}
	
	public void addNewBook() throws ParseException { // Handles the adding of a new book to global book list from admin pane
		Paperback tempPaper = null;
		Ebook tempEbook = null;
		Audiobook tempAudio = null;
		Date tempDate = null;
		tempDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateField.getText().trim());
		if (typeSelect.getSelectedItem().equals("paperback")) {
			tempPaper = new Paperback(Integer.parseInt(isbnField.getText().trim()),String.valueOf(typeSelect.getSelectedItem()),titleField.getText().trim(),String.valueOf(langSelect.getSelectedItem()),
					String.valueOf(genreSelect.getSelectedItem()),tempDate,Double.parseDouble(priceField.getText().trim()),Integer.parseInt(stockField.getText().trim()),
					Integer.parseInt(pagesField.getText().trim()),String.valueOf(formatSelect.getSelectedItem()));
			bookList.add(tempPaper);
			appendStock(tempPaper.createStockString());
			}
		else if (typeSelect.getSelectedItem().equals("ebook")) {
			tempEbook = new Ebook(Integer.parseInt(isbnField.getText().trim()),String.valueOf(typeSelect.getSelectedItem()),titleField.getText().trim(),String.valueOf(langSelect.getSelectedItem()),
					String.valueOf(genreSelect.getSelectedItem()),tempDate,Double.parseDouble(priceField.getText().trim()),Integer.parseInt(stockField.getText().trim()),
					Integer.parseInt(pagesField.getText().trim()),String.valueOf(formatSelect.getSelectedItem()));
			bookList.add(tempEbook);
			appendStock(tempEbook.createStockString());
			}
		else if (typeSelect.getSelectedItem().equals("audiobook")) {
			tempAudio = new Audiobook(Integer.parseInt(isbnField.getText().trim()),String.valueOf(typeSelect.getSelectedItem()),titleField.getText().trim(),String.valueOf(langSelect.getSelectedItem()),
					String.valueOf(genreSelect.getSelectedItem()),tempDate,Double.parseDouble(priceField.getText().trim()),Integer.parseInt(stockField.getText().trim()),
					Double.parseDouble(pagesField.getText().trim()),String.valueOf(formatSelect.getSelectedItem()));
			bookList.add(tempAudio);
			appendStock(tempAudio.createStockString());
			}
		}
	
	public void appendStock(String stockStr) { // Appends any new entry to the end of the Stock.txt
		try {
		FileWriter outputFile = new FileWriter("Stock.txt",true);
		BufferedWriter bw = new BufferedWriter(outputFile);
		bw.write("\n"+stockStr);
		bw.close();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String addBookValidation() { // Provides validation for fields when adding a new book
		ArrayList<JTextField> addInputList = new ArrayList<JTextField>(Arrays.asList(pagesField, priceField, titleField, dateField, isbnField));
		String messageOut = null;
		boolean valTesting = true;
		for(JTextField inputVar:addInputList) {
			if(inputVar.getText().equals("")) {
				messageOut="Please ensure no fields are empty";
		    	valTesting = false;
		    }
		}
		try {
		     int val = Integer.parseInt(isbnField.getText());
		     if(String.valueOf(val).length()!=8) {
		    	 messageOut="Please enter a valid ISBN";
		    	 valTesting = false;
		     }
		     if(checkValidISBN(val)!=true) {
		    	 messageOut="This ISBN already exists";
		    	 valTesting = false;
		     }
		}
		catch (NumberFormatException e) {
			messageOut="Please enter a valid ISBN";
			valTesting = false;
		}
		try {
			Integer.parseInt(isbnField.getText());
		}
		catch (NumberFormatException e) {
			messageOut="Please enter a valid quantity";
			valTesting = false;
		}
		if(checkValidFloat(priceField)!=true) {
			messageOut="Please enter a valid Price";
		}
		if(checkValidFloat(pagesField)!=true) {
			messageOut="Please enter a valid duration";
		}
		
		
		if(valTesting==true) {
			messageOut="Success!";
		}
		return messageOut;
	}
	
	public boolean checkValidISBN(int val) { // Checks ISBN is valid 
		boolean valid = true;
			for(Books book:bookList) {
				if(book.getISBN()==val) {
				valid = false;
				}
			}
		return valid;
	}
	
	public boolean checkValidFloat(JTextField inputBox) { // Checks for valid float value 
		boolean valid = true;
		try {
			Float.parseFloat(inputBox.getText());
		}
		catch (NumberFormatException e) {
			valid = false;
		}
		return valid;
	}
	
	public static List<User> generateUsers() throws FileNotFoundException { // Generates an array of users from User.txt
		userList = new ArrayList <User>();
		File userInputFile = new File("UserAccounts.txt");
		Scanner UserFileScanner = new Scanner(userInputFile);
		Admin tempAdmin = null;
		Customer tempCustomer = null;
		while (UserFileScanner.hasNextLine()) {
			String[] userInfo = UserFileScanner.nextLine().split(",");
		if (userInfo[6].equals(" admin")) {
			tempAdmin = new Admin(Integer.parseInt(userInfo[0].trim()),userInfo[1].trim(),userInfo[2].trim(), 
					new Address(Integer.parseInt(userInfo[3].trim()),userInfo[4].trim(),userInfo[5].trim()),(userInfo[6]).trim());
			userList.add(tempAdmin);
			}
		else if (userInfo[6].equals(" customer")) {
			tempCustomer = new Customer(Integer.parseInt(userInfo[0].trim()),userInfo[1].trim(),userInfo[2].trim(), 
					new Address(Integer.parseInt(userInfo[3].trim()),userInfo[4].trim(),userInfo[5].trim()),userInfo[6].trim());
			userList.add(tempCustomer);
			}
		}
		UserFileScanner.close();
		usernameArray = new ArrayList<String>();
		for(User user:userList) {
			usernameArray.add(user.getUsername());
		}
		return userList;
	}
	public static List<Books> importBookList() throws ParseException, FileNotFoundException { // Generates an array of books from Stock.txt
		bookList = new ArrayList <Books>();
		File bookInputFile = new File("Stock.txt");
		Scanner BookFileScanner = new Scanner(bookInputFile);
		Paperback tempPaper = null;
		Ebook tempEbook = null;
		Audiobook tempAudio = null;
		Date tempDate = null;
		while (BookFileScanner.hasNextLine()) {
			String[] bookInfo = BookFileScanner.nextLine().split(",");
		if ((bookInfo[1].trim()).equals("paperback")) {
			tempDate = new SimpleDateFormat("dd-MM-yyyy").parse(bookInfo[5].trim());
			tempPaper = new Paperback(Integer.parseInt(bookInfo[0].trim()),bookInfo[1].trim(),bookInfo[2].trim(),bookInfo[3].trim(),bookInfo[4].trim(),
					tempDate,Double.parseDouble(bookInfo[6].trim()),Integer.parseInt(bookInfo[7].trim()),
					Integer.parseInt(bookInfo[8].trim()),bookInfo[9].trim());
			bookList.add(tempPaper);
			}
		else if ((bookInfo[1].trim()).equals("ebook")) {
			tempDate = new SimpleDateFormat("dd-MM-yyyy").parse(bookInfo[5].trim());
			tempEbook = new Ebook(Integer.parseInt(bookInfo[0].trim()),bookInfo[1].trim(),bookInfo[2].trim(),bookInfo[3].trim(),bookInfo[4].trim(),
					tempDate,Double.parseDouble(bookInfo[6].trim()),Integer.parseInt(bookInfo[7].trim()),
					Integer.parseInt(bookInfo[8].trim()),bookInfo[9].trim());
			bookList.add(tempEbook);
			}
		else if ((bookInfo[1].trim()).equals("audiobook")) {
			tempDate = new SimpleDateFormat("dd-MM-yyyy").parse(bookInfo[5].trim());
			tempAudio = new Audiobook(Integer.parseInt(bookInfo[0].trim()),bookInfo[1].trim(),bookInfo[2].trim(),bookInfo[3].trim(),bookInfo[4].trim(),
					tempDate,Double.parseDouble(bookInfo[6].trim()),Integer.parseInt(bookInfo[7].trim()),
					Double.parseDouble(bookInfo[8].trim()),bookInfo[9].trim());
			bookList.add(tempAudio);
			}
		}
		BookFileScanner.close();
		return bookList;
	}
	
	// Adds each entry of Stock.txt to a table which makes up the store display
	public static void addRows(DefaultTableModel model, List<Books> aBookList) { 
		List<Audiobook> audioBookList = new ArrayList<Audiobook>();
	    List<Paperback> paperBookList = new ArrayList<Paperback>();
	    List<Ebook> eBookList = new ArrayList<Ebook>();
	    for(Books book:aBookList) {
	    	if(book.getType().equals("audiobook")) {
	    		audioBookList.add((Audiobook) book);
	    	}          
	    	else if(book.getType().equals("paperback")) {
	    		paperBookList.add((Paperback) book);
	    	}
	    	else if(book.getType().equals("ebook")) {
	    		eBookList.add((Ebook) book);
	    	}   
	    }
	    for(Audiobook book:audioBookList) {
	    	String [] row = {Integer.toString(book.getISBN()),book.getType(),book.getTitle(),book.getLanguage(),book.getGenre(),
	        		book.getReleaseDateStr(),Double.toString(book.getRetailPrice()),Integer.toString(book.getStockLevel()),
	        		Double.toString(book.getListeningLength()),book.getAudioFormat()};
	    	model.addRow(row);
	    }
	    for(Paperback book:paperBookList) {
	    	String [] row = {Integer.toString(book.getISBN()),book.getType(),book.getTitle(),book.getLanguage(),book.getGenre(),
	        		book.getReleaseDateStr(),Double.toString(book.getRetailPrice()),Integer.toString(book.getStockLevel()),
	        		Integer.toString(book.getNumPages()),book.getBookCond()};
	    	model.addRow(row);
	    }
	    for(Ebook book:eBookList) {
	    	String [] row = {Integer.toString(book.getISBN()),book.getType(),book.getTitle(),book.getLanguage(),book.getGenre(),
	        		book.getReleaseDateStr(),Double.toString(book.getRetailPrice()),Integer.toString(book.getStockLevel()),
	        		Double.toString(book.getNumPages()),book.geteFormat()};
	    	model.addRow(row);
	    }
    }
}