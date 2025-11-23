document.addEventListener("DOMContentLoaded", function () {
    const especieSelect = document.getElementById("especieSelect");
    const especieOutroGroup = document.getElementById("especieOutroGroup");
    const especieOutroInput = document.getElementById("especieOutroInput");

    especieSelect.addEventListener("change", function () {
        if (this.value === "outro") {
            especieOutroGroup.style.display = "block";
            especieOutroInput.required = true;
        } else {
            especieOutroGroup.style.display = "none";
            especieOutroInput.required = false;
            especieOutroInput.value = "";
        }
    });
});

// Está sendo utilizado no cadastroanimal.html
