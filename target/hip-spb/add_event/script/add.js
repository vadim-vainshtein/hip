let count = 0;

function AddPerformer() {

    console.log("AddPerformer()");
    let div = document.createElement("div");
    let nameLabel = document.createElement("label");
    let performerInput = document.createElement("input");
    let instrumentLabel = document.createElement("label");
    let addInstrumentButton = document.createElement("input");
    
    div.id = "performer_div" + count;
    div.className = "performer_div";
    
    nameLabel.innerHTML = "Имя: ";
    
    performerInput.name = "performer";
    performerInput.id = "performer" + count;
    performerInput.setAttribute("list", "performers_list");
    
    instrumentLabel.innerHTML = " Инструмент: ";
    
    addInstrumentButton.type = "button";
    addInstrumentButton.value = "+";
    /* 
        the addInstrumentButton.id is used to store the index of the current performer
        maybe I should find a more clear way to store that...
    */
    addInstrumentButton.id = count;
    addInstrumentButton.onclick = function() {
        AddInstrument(this);
    };
    
    div.append(nameLabel);
    div.append(performerInput);
    div.append(instrumentLabel);
    div.append(addInstrumentButton);

    document.getElementById("performers").append(div);
    count++;
}

function AddInstrument(addButton) {
    
    let instrumentInput = document.createElement("input");
    // get the id of a performer from the button id
    instrumentInput.name = "instrument" + addButton.id;
    document.getElementById("performer_div" + addButton.id).insertBefore(instrumentInput, addButton);
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

    AddPerformer();
}


document.addEventListener("DOMContentLoaded", ready);
