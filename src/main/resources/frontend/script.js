
document.addEventListener("DOMContentLoaded", function() { 
  	document
			.getElementById('newBook')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='addNewBook.html';
			}); 

 document
			.getElementById('mainPage')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='mainContent.html';
			}); 

document
			.getElementById('showBooks')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='booksViewer.html';
			}); 

document
			.getElementById('showBooksByAuthor')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='authSearch.html';
			}); 

document
			.getElementById('showBooksByTitle')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='titleSearch.html';
			}); 

document
			.getElementById('showBooksByDomain')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='domainSearch.html';
			}); 

document
			.getElementById('showBooksByYear')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='yearSearch.html';
			}); 

document
			.getElementById('updateBook')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='updateBook.html';
			}); 

document
			.getElementById('deleteBook')
			.addEventListener("click", function (event) {
				document
					.getElementById('contentFrame').src='deleteBook.html';
			}); 
});