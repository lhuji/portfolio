let header = document.getElementsByClassName("header");
let h1 = document.getElementById("h1");
let elementos = document.getElementsByClassName("nav-links");
function secundario() {
    header.setAttribute("style", "height: 100px;");
    h1.setAttribute("style", "color: #F2F2F2; margin: 15px; font-size: 30px;");
    elementos.setAttribute("style", "font-size:");
    }    
function principal() {
    h1.setAttribute("style", "color: #F2F2F2; margin: 15px;font-size: 50px;")
    elementos.setAttribute("style", "font-size:20");   
    header.setAttribute("style", "height: 200px;");  
    }
