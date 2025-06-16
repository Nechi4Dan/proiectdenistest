// ==============================
// BLOG - Afișare toate postarile
// ==============================

// Selectam containerul unde vor fi afisate postarile
const toatePostarile = document.getElementById("toate-blogurile");

// Cerere catre backend pentru a obtine toate postarile de blog
fetch("http://localhost:8080/api/blog-posts")
  .then(response => {
    if (!response.ok) {
      throw new Error("Eroare la incarcarea postarilor de blog.");
    }
    return response.json();
  })
  .then(postari => {
    postari.forEach(post => {
      // Cream un card HTML pentru fiecare postare
      const card = document.createElement("div");
      card.className = "postare";
      card.style.cursor = "pointer";

      // Cardul este clicabil si duce la pagina detalii blog
      card.onclick = () => {
        window.location.href = `articol.html?id=${post.id}`;
      };

      // Continutul cardului
      card.innerHTML = `
        <img src="${post.image || 'images/blog/default.jpg'}" alt="${post.title}" style="width:100%; height:200px; object-fit:cover;" />
        <h3>${post.title}</h3>
        <p>${post.summary}</p>
        <a href="articol.html?id=${post.id}" onclick="event.stopPropagation()">Citeste mai mult</a>
      `;

      toatePostarile.appendChild(card);
    });
  })
  .catch(error => {
    // Afisam un mesaj de eroare in caz de esec
    toatePostarile.innerHTML = `<p style="color:red;">${error.message}</p>`;
  });