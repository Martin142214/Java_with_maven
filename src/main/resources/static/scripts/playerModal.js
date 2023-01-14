function openEditModal(name, email, id) {
        document.getElementById('id02').style.display='block';
        document.getElementById('name').value = name;
        document.getElementById('email').value = email;
        document.getElementById('modalForm').setAttribute("action", "/players/edit/" + id);
    }

    function openDeleteModal(id, name){
        document.getElementById('id03').style.display='block';
        document.getElementById('h2').innerHTML = "Are you sure you want to delete player with name: " + name;
        document.getElementById('modalDeleteForm').setAttribute("action", "/players/delete/" + id);
    }