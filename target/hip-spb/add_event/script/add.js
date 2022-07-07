let count = 0;

function AddPerformer() {

    console.log("AddPerformer()");
    let div = document.createElement("div");
    let nameLabel = document.createElement("label");
    let performerInput = document.createElement("input");
    let instrumentLabel = document.createElement("label");
    let instrumentInput = document.createElement("input");
    let addInstrumentButton = document.createElement("input");
    
    div.id = "performer_div" + count;
    div.className = "performer_div";
    
    nameLabel.innerHTML = "Имя: ";
    
    performerInput.name = "performer";
    performerInput.id = "performer" + count;
    performerInput.setAttribute("list", "performers_list");
    
    instrumentLabel.innerHTML = " Инструмент: ";
    
    instrumentInput.name = "instrument" + count;
    instrumentInput.value = "instrument";
    
    addInstrumentButton.type = "button";
    addInstrumentButton.value = "+";
    addInstrumentButton.id = count;
    addInstrumentButton.onclick = function() {
        AddInstrument(this);
    };
    
    div.append(nameLabel);
    div.append(performerInput);
    div.append(instrumentLabel);
    div.append(instrumentInput);
    div.append(addInstrumentButton);

    document.getElementById("performers").append(div);
    count++;
}

function AddInstrument(addButton) {
    
    let instrumentInput = document.createElement("input");
    instrumentInput.name = "instrument" + addButton.id;
    instrumentInput.value = "instrument" + addButton.id;
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


function ready() {

    AddPerformer();
}


document.addEventListener("DOMContentLoaded", ready);
