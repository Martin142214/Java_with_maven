function searchFunction() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toLowerCase();
    table = document.getElementById("tableList");
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < table.rows.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if(td){
            txtValue = td.textContent || td.innerText;
               if (txtValue.toLowerCase().indexOf(filter) > -1) {
                   tr[i].style.display = "";
               } else {
                   tr[i].style.display = "none";
               }
        }
    }
}