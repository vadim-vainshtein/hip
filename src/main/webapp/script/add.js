let fieldCount = 0;

function AddForm() {

    let programNameLabel = document.createTextNode("Программа: ");
    let concertDateLabel = document.createTextNode("Дата: ");
    let concertTimeLabel = document.createTextNode("Время: ");

    let performersCount = 0;

   let fieldSet = document.createElement('fieldset');
   let performersForm = document.createElement('fieldset');
   let programNameInput = document.createElement('input');
   let concertDateInput = document.createElement('input');
   let concertTimeInput = document.createElement('input');
   let performerInput = document.createElement('input');
   let addPerformerButton = document.createElement('input');

   programNameInput.id = "programName" + fieldCount;
   programNameInput.type = "text";

   concertDateInput.id = "concertDate" + fieldCount;
   concertDateInput.type = "date";

   concertTimeInput.id = "concertTime" + fieldCount;
   concertTimeInput.type = "time";

   addPerformerButton.type = "button";
   addPerformerButton.id = "add_performer";
   addPerformerButton.value = "Добавить исполнителя: ";
   addPerformerButton.onclick = function() {
        let performerInput = document.createElement('input');
        performerInput.id = "performer" + fieldCount + "_" + performersCount;
        performersForm.insertBefore(performerInput, this);
   }
   
   performerInput.id = "performer" + fieldCount + "_" + performersCount++;

   performersForm.innerHTML = "Исполнитель: ";
   performersForm.append(performerInput, addPerformerButton);
   
   fieldSet.id = "field" + fieldCount;
   
   fieldSet.append(
       programNameLabel,
       programNameInput,
       concertDateLabel,
       concertDateInput,
       concertTimeLabel,
       concertTimeInput,
       performersForm
       );

   document.getElementById("add_form").append(fieldSet);

   fieldCount++;
}
