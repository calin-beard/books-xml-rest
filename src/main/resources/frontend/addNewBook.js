
document.addEventListener("DOMContentLoaded", function() { 

 	var form = document.querySelector('#newBookForm');
    form.addEventListener('submit', function (event) {
        try {
            var data = new FormData(form);
            console.log(data);
            $.ajax({
                url: 'http://localhost:8080/books/new',
                type: "POST", // Any URL
                enctype: 'application/x-www-form-urlencoded',
                data: data,
                processData: false,  // Important!
                contentType: false,
                cache: false,                // Serialize the form data
                success: function (data) {
                  	alert('Book added with success');
                },
                error: function (xhr, text, error) {              // If 40x or 50x; errors
                    alert('Error: the book cannot be added');
                }});               
        } catch (e) {
            console.log(e);
        }
        event.preventDefault();
    });

});