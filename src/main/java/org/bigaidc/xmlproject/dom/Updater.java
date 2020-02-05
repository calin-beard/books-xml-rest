package dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import data.AuthorObj;
import data.AuthorsDict;
import data.BookObj;
import data.BoolPair;
import data.DomainMap;
import data.GetMapsINTF;
import data.PublisherMap;
import data.PublisherObj;
// import sax.BIB_NODES;

public class Updater {
	
	protected final PublisherMap mapPublishers;
	protected final DomainMap mapDomains;
	protected final AuthorsDict mapAuthors;
	
	protected final Vector<BookObj> vBooks;
	
	protected final Document document;
	
	// XPATH
	protected static final String sXP_AUTHORS    = "/BibManagement/a:Authors";
	protected static final String sXP_PUBLISHERS = "/BibManagement/p:Publishers";
	protected static final String sXP_DOMAINS    = "/BibManagement/Domains";
	// Books
	protected static final String sXP_BOOKS = "/BibManagement/Books";
	protected static final String sXP_BOOK  = sXP_BOOKS + "/Book[@idBook=$Book]";
	// Authors Book
	protected static final String sXP_BOOK_AUTHORS_ROOT = sXP_BOOKS + "/Book[@idBook=$Book]/Authors";
	// Publisher Book
	protected static final String sXP_BOOK_PUBLISHER_ROOT = sXP_BOOKS + "/Book[@idBook=$Book]/Published";
	protected static final String sXP_BOOK_PUBLISHER  = sXP_BOOK_PUBLISHER_ROOT + "/Publisher";
	
	public Updater(final Document document, final Vector<BookObj> vBooks,
			final AuthorsDict mapAuthors,
			final DomainMap mapDomains,
			final PublisherMap mapPublishers) {
		this.document = document;
		this.vBooks   = vBooks;
		this.mapPublishers = mapPublishers;
		this.mapDomains  = mapDomains;
		this.mapAuthors  = mapAuthors;
	}
	public Updater(final Document document, final GetMapsINTF maps) {
		this(document, maps.GetBooks(),
				maps.GetAuthorsDict(), maps.GetDomainsMap(), maps.GetPublisherMap());
	}
	
	// +++++++ MEMBER FUNCTIONS +++++++
	
	public String Fill(final String sBase, final String sVar, final String sVal) {
		return sBase.replaceAll(sVar, sVal);
	}
	
	public void Write(final File fileName) {
		final OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		final XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(fileName), format);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ++++ Find Functions ++++	
	// are moved to class Controller
	
	// ++++ Update Functions ++++
	
	// ++++ Add Functions ++++
	// Book
	public void AddBook(final BookObj book) {
		final Element rootBooks = (Element) document.selectSingleNode(sXP_BOOKS);
		if(rootBooks == null) {
			return; // fatal error
		}
		// 1.) add xml Book node
		final Element nodeBook = DocumentHelper.createElement(BIB_NODES.BOOK.GetNode());
		book.idBook = 10; // TODO: proper ID
		nodeBook.addAttribute("idBook", book.idBook.toString());
		rootBooks.add(nodeBook);
		// 1.a.) Title
		final Element nodeTitle = DocumentHelper.createElement(BIB_NODES.BOOK_TITLE.GetNode());
		nodeTitle.addText(book.sTitle);
		nodeBook.add(nodeTitle);
		
		// 2.) add Authors
		final Element nodeAuthors = DocumentHelper.createElement(BIB_NODES.BOOK_AUTHORS_LIST.GetNode());
		nodeBook.add(nodeAuthors);
		this.AddAuthor(book, nodeAuthors);
		
		// 3.) add Date
		final Element nodeDate = DocumentHelper.createElement(BIB_NODES.DATE.GetNode());
		nodeBook.add(nodeDate);
		final Element nodeDateYear = DocumentHelper.createElement(BIB_NODES.DATE_YEAR.GetNode());
		nodeDateYear.addText("" + book.iYear);
		nodeDate.add(nodeDateYear);
		
		// 4.) add Publisher
		this.AddPublisher(book.publisher);
		final Element nodePublisherRoot = DocumentHelper.createElement(BIB_NODES.BOOK_PUBLISHER_BASE.GetNode());
		nodeBook.add(nodePublisherRoot);
		final Element nodePublisher = DocumentHelper.createElement(BIB_NODES.BOOK_PUBLISHER.GetNode());
		nodePublisher.addAttribute("idPub", book.publisher.id.toString());
		nodePublisherRoot.add(nodePublisher);
		final Element nodePublisherLocation = DocumentHelper.createElement(BIB_NODES.BOOK_PUBLISHER_LocationID.GetNode());
		nodePublisherLocation.addAttribute("EIdType", "doi");
		nodePublisherRoot.add(nodePublisherLocation);
		// TODO: add text
		
	}
	
	// Publisher
	public Integer AddPublisher(final PublisherObj publisher) {
		final Integer idPublisher = mapPublishers.GetOrSetPublisher(publisher);
		// get xml node
		final Element rootPublishers = (Element) document.selectSingleNode(sXP_PUBLISHERS);
		if(rootPublishers == null) {
			return null; // fatal error
		}

		// add xml node
		final Element nodePublisher = DocumentHelper.createElement(BIB_NODES.PUBLISHER.GetNode());
		nodePublisher.addAttribute("idPub", idPublisher.toString());
		//
		final Element nodeName = DocumentHelper.createElement(BIB_NODES.PUBLISHER_NAME.GetNode());
		nodeName.addText(publisher.sName);
		nodePublisher.add(nodeName);
		//
		final Element nodeAddress = DocumentHelper.createElement(BIB_NODES.PUBLISHER_ADDRESS.GetNode());
		nodeAddress.addText(publisher.sAddress);
		nodePublisher.add(nodeAddress);
		//
		rootPublishers.add(nodePublisher);
		
		return idPublisher;
	}
	
	// ++++ Update Author
	
	public void AddAuthor(final AuthorObj author) {
		// get xml node: Authors root
		final Element rootAuthors = (Element) document.selectSingleNode(sXP_AUTHORS);
		if(rootAuthors == null) {
			return; // fatal Error
		}
		// add xml node
		final Element nodeAuthor = DocumentHelper.createElement(BIB_NODES.AUTHOR.GetNode());
		nodeAuthor.addAttribute("idAffil", author.idAuthor.toString());
		//
		final Element nodeName = DocumentHelper.createElement(BIB_NODES.AUTHOR_NAME.GetNode());
		nodeName.addText(author.sName);
		nodeAuthor.add(nodeName);
		//
		final Element nodeGName = DocumentHelper.createElement(BIB_NODES.AUTHOR_GNAME.GetNode());
		nodeGName.addText(author.sGivenName);
		nodeAuthor.add(nodeGName);
		//
		rootAuthors.add(nodeAuthor);
	}
	public void AddAuthor(final BookObj book) {
		// get xml node: Book Authors root
		final String sXP_BOOK_AUTHORS_ROOT = this.Fill(Updater.sXP_BOOK_AUTHORS_ROOT, "\\$Book", book.idBook.toString());
		final Element rootAuthors = (Element) document.selectSingleNode(sXP_BOOK_AUTHORS_ROOT);
		if(rootAuthors == null) {
			return; // fatal Error // TODO
		}
		this.AddAuthor(book, rootAuthors);
	}
	public void AddAuthor(final BookObj book, final Element rootAuthors) {
		// Authors
		for(final AuthorObj author : book.vAuthors) {
			final BoolPair<Integer> idAuthor = mapAuthors.GetOrSetAuthor(author);
			if(idAuthor.isE) {
				// add new Author to xml
				this.AddAuthor(author);
			}
			final Element elAuthor = (Element) rootAuthors.selectSingleNode("Author[@idAuthor=" + author.idAuthor + "]");
			if(elAuthor == null) {
				// add xml node
				final Element nodeAuthor = DocumentHelper.createElement(BIB_NODES.BOOK_AUTHOR.GetNode());
				nodeAuthor.addAttribute("idAuthorRef", author.idAuthor.toString());
				rootAuthors.add(nodeAuthor);
			}
		}
	}
	
	// ++++ Update Publishers
	
	public void AddPublisher(final BookObj book, final PublisherObj publisher) {
		// TODO
		if(publisher.id == null) {
			this.AddPublisher(publisher);
		} else {
			// TODO: update existing Publisher
		}
		// TODO: update Book Info
		// get xml node
		final String sXP_BOOK_PUBLISHER_ROOT = this.Fill(Updater.sXP_BOOK_PUBLISHER_ROOT, "\\$Book", book.idBook.toString());
		Element rootBookPub = (Element) document.selectSingleNode(sXP_BOOK_PUBLISHER_ROOT);
		if(rootBookPub == null) {
			// add Publisher node
			rootBookPub = DocumentHelper.createElement(BIB_NODES.BOOK_PUBLISHER_BASE.GetNode());
			final String sXP_BOOK = this.Fill(Updater.sXP_BOOK, "\\$Book", book.idBook.toString());
			Element rootBook = (Element) document.selectSingleNode(sXP_BOOK);
			rootBook.add(rootBookPub); // TODO: proper order
		}
		
		final String sXP_BOOK_PUBLISHER = this.Fill(Updater.sXP_BOOK_PUBLISHER, "\\$Book", book.idBook.toString());
		Element elBookPub = (Element) document.selectSingleNode(sXP_BOOK_PUBLISHER);
		if(elBookPub == null) {
			// add xml node
			elBookPub = DocumentHelper.createElement(BIB_NODES.BOOK_PUBLISHER.GetNode());
			elBookPub.addAttribute("idPubRef", publisher.id.toString());
			rootBookPub.add(elBookPub);
		} else {
			elBookPub.remove(elBookPub.attribute("idPubRef"));
			elBookPub.addAttribute("idPubRef", publisher.id.toString());
		}
	}
}
