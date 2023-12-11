function truncateText(el){
    let paragraph = el.innerHTML;
    el.dataset.fulltext = paragraph;
    let words = paragraph.split(' ');
    if (words.length > 40)  {
        el.innerHTML = `${words.slice(0,65).join(' ')}... <span class="leermas" id="-${el.id.split("-").pop()}"></span>`
    }
}

document.addEventListener("DOMContentLoaded", function (){
    let paragraphs = document.querySelectorAll('.parrafo p');
    paragraphs.forEach(truncateText);

    document.addEventListener('click', e=> {
        if (e.target.classList.contains('leermas')) {
            let id = e.target.id.split('-').pop();
            let paragraph = document.querySelector(`#parrafo-${id}`);
            if (e.target.innerHTML === 'Leer más...'){
                e.target.innerHTML = 'Leer menos';
                paragraph.innerHTML = paragraph.dataset.fulltext;
                $(this).css("max-height","100%");
            } else {
                e.target.innerHTML = 'Leer más...';
                truncateText(paragraph);
                $(this).css("max-height","210px");
            }
        }
    });
});

