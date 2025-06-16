document.addEventListener("DOMContentLoaded", () => {
  const username = localStorage.getItem("username");

  if (!username) {
    alert("Nu esti autentificat.");
    window.location.href = "index.html";
    return;
  }

  fetch(`http://localhost:8080/api/users/${username}`)
    .then(response => {
      if (!response.ok) {
        throw new Error("Eroare la preluarea datelor utilizatorului");
      }
      return response.json();
    })
    .then(data => {
      document.getElementById("username").textContent = data.username;
      document.getElementById("fullname").textContent = `${data.firstName} ${data.lastName}`;
      document.getElementById("email").textContent = data.email;
      document.getElementById("phone").textContent = data.phone;
      document.getElementById("role").textContent = data.role;
    })
    .catch(error => {
      console.error("Eroare:", error);
      alert("Nu s-au putut incarca datele utilizatorului.");
    });
});

// ============================
// LOGOUT
// ============================
function logout() {
  localStorage.removeItem("username");
  window.location.href = "index.html";
}
