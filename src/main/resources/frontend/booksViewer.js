
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("booksTable");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

document.addEventListener("DOMContentLoaded", function() { 

 var books =  [
        {
            "id": 1,
            "rating":2,
            "title": "Loop me to the end of Love",
            "authors": [
                {
                    "id": 1,
                    "name": "Garfunkel",
                    "given-name": "Loop And"
                }
            ],
            "date" : {
                "year": 2019,
                "month": 10,
                "day": 14
            },
            "domain": {
                "id": 1,
                "name": "Science - IT - Loop Theory"
            },
            "published" : {
                "publisher": {
                    "id": 1,
                    "name": "Top Informatics Press"
                },
                "location-id": {
                    "eidtype": "doi",
                    "v": 1,
                    "value": "00.0000/t.info.2019.10.001"
                }
            }
        },
        {
            "id": 2,
            "rating":4,
            "title": "The Design of Powerful Bibliographic Systems",
            "authors": [
                {
                    "id": 1,
                    "name": "Garfunkel",
                    "given-name": "Loop And"
                },
                {
                    "id": 2,
                    "name": "C",
                    "given-name": "A B"
                },
                {
                    "id": 3,
                    "name": "Dreistein",
                    "given-name": "MC3"
                }
            ],
            "date" : {
                "year": 2019,
                "month": 10,
                "day": 14
            },
            "domain": {
                "id": 1,
                "name": "Science - IT - Loop Theory"
            },
            "published" : {
                "publisher": {
                    "id": 2,
                    "name": "Interplanetary Press"
                },
                "location-id": {
                    "eidtype": "doi",
                    "v": 1,
                    "value": "00.0000/t.info.2019.10.002"
                }
            }
        }
    ];

const authorReducer = (accumulator, currentValue) => accumulator + ',' + currentValue.name + " " + currentValue['given-name'];

function getStringAuthors(authors) {
    var first = authors.shift();
    var firstAuthorString = first.name + " " + first['given-name'];

    return authors.reduce(authorReducer, firstAuthorString);

}
function addDataToTbody(nl, data) { // nl -> NodeList, data -> array with objects
  data.forEach((d, i) => {
    var tr = nl.insertRow(i);
    var j=0;
    var titleCell = tr.insertCell(j++);
    titleCell.innerHTML = d.title;

    var authorCell = tr.insertCell(j++);
    authorCell.innerHTML = getStringAuthors(d.authors);

    var ratingCell = tr.insertCell(j++);
    ratingCell.innerHTML = d.rating;

    var dateCell = tr.insertCell(j++);
    dateCell.innerHTML = d.date.day+'-'+d.date.month+'-'+d.date.year;

    var domainCell = tr.insertCell(j++);
    domainCell.innerHTML = d.domain.name;

    var publisherCell = tr.insertCell(j++);
    publisherCell.innerHTML = d.published.publisher.name;

    nl.appendChild(tr);
  })
}

var booksToBody = document.getElementById("booksTable");

addDataToTbody(booksToBody, books);
  $.ajax({
            url: 'http://127.0.0.1/books',
            type: "Get", // Any URL
        
            success: function (data) {
                // books = data TODO UNCOMMENT IT
                addDataToTbody(booksToBody, books);
            },
            error: function (xhr, text, error) {              // If 40x or 50x; errors
                alert('Error: the book cannot be added');
            }});         

});