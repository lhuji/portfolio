$(function () {
    "use strict";
    $("#grid1").jqGrid({
        colModel: [
            { name: "name", label: "Intérprete", width: 200 },
            { name: "Canción", label: "Cáncion", width: 200 },
            { name: "descargas", label: "Nº Descargas", width: 100, template: "number" },
            { name: "precio", label: "Precio", width: 60, template: "number" },
            { name: "versionado", label: "Versionado", width: 100, template: "booleanCheckbox", firstsortorder: "desc" },
            { name: "ultima", label: "Últ. Descarga", width: 90, align: "center", sorttype: "text"}
        ],
        data: [
            { id: "10", name: "Joan Manuel Serrat", Canción: "Mediterráneo", descargas: 1334987,precio: 1.25, versionado: false, ultima:"02-Sep-2021"},
            { id: "20", name: "Natalia Dicenta", Canción: "Summertime", descargas: 827994,precio: 1.05, versionado: true, ultima:"12-Oct-2021"},
            { id: "30", name: "Andrea Motis Quartet", Canción: "He's funny that way", descargas: 672910,precio: 0.80, versionado: true, ultima:"28-Jul-2021" },
            { id: "40", name: "Los Ronaldos", Canción: "Adiós Papá", descargas: 513845,precio: 1.00, versionado: false, ultima:"05-Aug-2021" },
            { id: "50", name: "Pablo Alborán", Canción: "Inséparables", descargas: 421990,precio: 0.90, versionado: false, ultima:"14-May-2021" },
            { id: "60", name: "Rozalén", Canción: "80 veces", descargas: 355682,precio: 0.95, versionado: false, ultima:"30-Aug-2021" },
            { id: "70", name: "Pedro Guerra", Canción: "Niños", descargas: 202347,precio: 1.15, versionado: false, ultima:"23-Sep-2021" },
            { id: "80", name: "Rosalía", Canción: "Me quedo contigo", descargas: 117906,precio: 1.20, versionado: true, ultima:"31-Jul-2021" } ],

        iconSet: "fontAwesome",
        idPrefix: "g5_",
        sortname: "invdate",
        sortorder: "desc",
        threeStateSort: true,
        sortIconsBeforeText: true,
        headertitles: true,
        pager: true,
        rowNum: 5,
        viewrecords: true,
        searching: {
            defaultSearch: "cn"
        },
        caption: "Tabla de intérpretes con sus canciones más escuchadas"
    }).jqGrid("filterToolbar");
});



