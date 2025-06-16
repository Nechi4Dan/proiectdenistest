// ==============================
// FORMULAR DE CONTACT - VALIDARE SI CONFIRMARE
// ==============================

document.getElementById("contact-form").addEventListener("submit", function (e) {
  e.preventDefault(); // Previne trimiterea formularului

  // Preluare valori din formular
  const nume = this.nume.value.trim();
  const prenume = this.prenume.value.trim();
  const email = this.email.value.trim();
  const mesaj = this.mesaj.value.trim();
  const confirmare = document.getElementById("confirmare");

  // Ascundem mesajul anterior (daca exista)
  confirmare.style.display = "none";

  // Validare campuri obligatorii
  if (!nume || !prenume || !email || !mesaj) {
    alert("Completeaza toate campurile obligatorii: nume, prenume, email si mesaj.");
    return;
  }

  // Simulam trimiterea mesajului
  confirmare.textContent = "Mesajul a fost trimis cu succes!";
  confirmare.style.display = "block";
  confirmare.style.color = "green";

  // Resetam formularul dupa trimitere
  this.reset();
});
