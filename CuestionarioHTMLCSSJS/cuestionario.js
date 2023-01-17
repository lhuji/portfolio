addEventListener("click",formulario)
var opcion =document.getElementsByTagName("Input");

function formulario(){
    sumA = 0;
    sumB = 0;
    sumC = 0;
    sumD = 0;
    for (var i = 0; i < opcion.length; i++){
        if(opcion[i].value === "A" && opcion[i].checked){
        sumA ++;
        }
        if(opcion[i].value === "B" && opcion[i].checked){
        sumB ++;
        }
        if(opcion[i].value === "C" && opcion[i].checked){
        sumC ++;
        }
        if(opcion[i].value === "D" && opcion[i].checked){
        sumD ++;    
        }
        console.log(sumA, sumB, sumC, sumD)
    }

    document.getElementById("totsumA").innerHTML= sumA;
    document.getElementById("totsumB").innerHTML= sumB;
    document.getElementById("totsumC").innerHTML= sumC;
    document.getElementById("totsumD").innerHTML= sumD;
}
