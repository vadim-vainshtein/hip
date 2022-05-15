let count = 1;

function AddPerformer() {

    let performerInput = document.createElement("input");
    performerInput.name = "performer";
    performerInput.id = "performer" + count;
    performerInput.value = "performer" + count;

    document.getElementById("performers").append(performerInput);
    count++;
}