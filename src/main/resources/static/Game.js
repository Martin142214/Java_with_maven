function playGame() {
        var dropdown1 = document.getElementById("player1dropdown");
        var value1 = dropdown1.value;
        var text1 = dropdown1.options[dropdown1.selectedIndex].text;
        var dropdown2 = document.getElementById("player2dropdown");
        var value2 = dropdown2.value;
        var text2 = dropdown2.options[dropdown2.selectedIndex].text;

        document.getElementById('game').style.display='block';
        document.getElementById('player1').innerHTML = text1;
        document.getElementById('player2').innerHTML = text2;
        var seconds = 10;
        var timer = document.getElementById('timer');
        timer.innerHTML = '';
        setInterval(function(){
          seconds--;
          document.getElementById('timer').innerHTML = seconds;
          if(seconds==0){
            return;
          }
        }, 1000);
    }

    function checkInput() {
        var p1dropdown = document.getElementById('player1dropdown');
        var p2dropdown = document.getElementById('player2dropdown');
        setInterval(function(){
        p1dropdown = document.getElementById('player1dropdown');
                p2dropdown = document.getElementById('player2dropdown');
            if (p1dropdown.value != "noSelected" && p2dropdown.value != "noSelected")
               {
                  document.getElementById('tournament').style.display='block';
               }
               /*else if(value1 != "noSelected" || value2 != "noSelected")
               {
                  document.getElementById('tournament').style.display='none';
               }
            if(value1 == "noSelected" || value2 == "noSelected"){
                document.getElementById('tournament').style.display='none';
            }*/

            }, 50);
        disableOption(p1dropdown, p2dropdown);
    }

    function disableOption(p1dropdown, p2dropdown, playerName){
        var p1dropdown = document.getElementById('player1dropdown');
        var p2dropdown = document.getElementById('player2dropdown');

        var p1elements = document.querySelectorAll('#player1Option');
        var p2elements = document.querySelectorAll('#player1Option');

        if (p1dropdown.value != "noSelected" && p2dropdown.value == "noSelected"){
            //p2dropdown.options[p1dropdown.value].setAttribute('disabled', '');
            p2elements.forEach(function(element){
                    if(element.value == playerName)
                       element.setAttribute("disabled", "");
                    });
        }
        else if(p1dropdown.value == "noSelected" && p2dropdown.value != "noSelected"){
            p1elements.forEach(function(element){
                if(element.value == playerName)
                    element.setAttribute("disabled", "");
                });
        }
    }

    function playBtnAppear(){
    var tournamentName = document.getElementById('tournamentDropdown').value;
        if(tournamentName != 'noSelected'){
            document.getElementById('playBtn').style.display='block';
        }
        else{
            document.getElementById('playBtn').style.display='none';
        }
    }

    function hideElements(){
        document.getElementById('tournament').style.display='none';
        document.getElementById('tournamentDropdown').value='noSelected';
        document.getElementById('playBtn').style.display='none';
    }

    function showImage(id, div){
        var imageDiv = document.getElementById(div);
        var img = document.createElement("img");
        var playerName = document.getElementById(id).value;
        if(imageDiv.hasChildNodes()){
            imageDiv.removeChild(imageDiv.firstChild);
        }
        if(playerName != "noSelected"){
            img.src = "/images/" + playerName.toLowerCase() + ".jpg";
            //loadImage(img, passImageName());
            img.style.width = '400px';
            img.style.height = 'auto';
            img.style.display = 'block';
            img.alt = "player image";
            imageDiv.appendChild(img);
        }
        else{
            hideElements();
        }
    }

    function passImageName(imageName){
        return imageName;
    }

    function incognitoImage(img){
        img.src = "/images/noImage.jpg";
    }

    function loadImage(img, playerImage){
        img.src = "/images/" + playerImage.toLowerCase();
    }