// Outro Serviço
const tipoSelect = document.getElementById("tipoServico");
const tipoOutroGroup = document.getElementById("tipoOutroGroup");

tipoSelect.addEventListener("change", () => {
    if (tipoSelect.value === "outro") {
        tipoOutroGroup.style.display = "block";
    } else {
        tipoOutroGroup.style.display = "none";
    }
});

// Buscar animais
const animalInput = document.getElementById("animalSearch");
const animalList = document.getElementById("animalList");

animalInput.addEventListener("input", () => {
    let animals = JSON.parse(localStorage.getItem("animais")) || [];
    let search = animalInput.value.toLowerCase();

    animalList.innerHTML = "";

    if (search.length < 1) return;

    let results = animals.filter(a => a.nome.toLowerCase().includes(search));

    results.forEach(a => {
        let li = document.createElement("li");
        li.textContent = a.nome;
        li.onclick = () => {
            animalInput.value = a.nome;
            animalList.innerHTML = "";
        };
        animalList.appendChild(li);
    });
});
