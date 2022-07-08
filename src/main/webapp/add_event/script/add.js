let performersCount = 0;
let ensemblesCount = 1;

function AddPerformer(button) {

    let div = document.createElement("div");
    let nameLabel = document.createElement("label");
    let performerInput = document.createElement("input");
    let instrumentLabel = document.createElement("label");
    let addInstrumentButton = document.createElement("input");
    
    div.id = "performer_div" + performersCount;
    div.className = "performer_div";
    
    nameLabel.innerHTML = "Имя: ";
    
    performerInput.name = "performer";
    performerInput.id = "performer" + performersCount;
    performerInput.setAttribute("list", "performers_list");
    
    instrumentLabel.innerHTML = " Инструмент: ";
    
    addInstrumentButton.type = "button";
    addInstrumentButton.value = "+";
    addInstrumentButton.setAttribute('data-instrument-id', performersCount);
    addInstrumentButton.onclick = function() {
        AddInstrument(this);
    };
    
    div.append(nameLabel);
    div.append(performerInput);
    div.append(instrumentLabel);
    div.append(addInstrumentButton);

    button.parentNode.append(div);
    performersCount++;
}

function AddEnsemble(button) {

    let fieldSet = document.createElement("fieldset");
    let legend = document.createElement("legend");
    let ensembleNameP = document.createElement("p");
    let ensemleInput = document.createElement("input");
    let performersWrapperDiv = document.createElement("div");
    let addPerformerButton = document.createElement("input");

    fieldSet.id = "ensemble" + ensemblesCount;
    legend.innerHTML = "Ансамбль №" + (ensemblesCount + 1);
    ensembleNameP.innerHTML = "Название ансамбля: ";
    ensemleInput.name = "ensemble" + ensemblesCount;
    
    addPerformerButton.type = "button";
    addPerformerButton.value = "Добавить исполнителя";
    addPerformerButton.onclick = function() {
        AddPerformer(this);
    }

    ensembleNameP.append(ensemleInput);
    performersWrapperDiv.append(addPerformerButton);
    
    fieldSet.append(legend);
    fieldSet.append(ensembleNameP);
    fieldSet.append(performersWrapperDiv);

    button.parentNode.append(fieldSet);
    ensemblesCount++;
}

function AddInstrument(addButton) {
    
    let instrumentInput = document.createElement("input");
    console.log(addButton.dataset.instrumentId);
    instrumentInput.name = "instrument" + addButton.dataset.instrumentId;
    document.getElementById("performer_div" + addButton.dataset.instrumentId).insertBefore(instrumentInput, addButton);
}


function httpGetAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            callback(xmlHttp.responseText);
    };
    
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}

function FillAddress(address) {
    
    let addressInput = document.getElementById("address");
    addressInput.value = address;
}

function AutoFillAddress(name) {

    let query = "add?act=1&name=" + name;
    httpGetAsync(query, FillAddress);
}

function ready() {

    //AddPerformer();
}


document.addEventListener("DOMContentLoaded", ready);
