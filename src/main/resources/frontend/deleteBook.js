document.addEventListener("DOMContentLoaded", function() { 

 	var form = document.querySelector('#deleteBookForm');
    form.addEventListener('submit', function (event) {
        try {
            var data = new FormData(form);
            console.log(data);
            $.ajax({
                    url: 'http://127.0.0.1/books/delete-book',
                    type: "POST", // Any URL
                	enctype: 'application/x-www-form-urlencoded',
                	data: data,
                	processData: false,  // Important!
                	contentType: false,
                	cache: false, 
                    success: function (data) {
                        alert('Delete operation successfull!');
                    },
                    error: function (xhr, text, error) {              // If 40x or 50x; errors
                        alert('Error: the book could not be deleted!');
                    }});         

        } catch (e) {
            console.log(e);
        }
        event.preventDefault();
    });

});
