// ==============================
// Pagina ARTICOL - Detalii postare blog
// ==============================

// Containerul principal unde va fi afisata postarea
const container = document.getElementById("detalii-blog");

// Containerul pentru butoanele de navigare intre articole
const navContainer = document.querySelector(".navigare-blog");

// Preluam ID-ul din URL (ex: articol.html?id=3)
const params = new URLSearchParams(window.location.search);
const id = parseInt(params.get("id"));

// Daca nu exista ID, afisam eroare
if (!id) {
  container.innerHTML = "<p style='color:red;'>Postarea nu a fost specificata.</p>";
} else {
  // Cerere catre API pentru a lua toate postarile
  fetch("http://localhost:8080/api/blog-posts")
    .then(res => {
      if (!res.ok) throw new Error("Eroare la incarcarea postarilor.");
      return res.json();
    })
    .then(postari => {
      // Cautam postarea curenta dupa ID
      const indexCurent = postari.findIndex(p => p.id === id);
      if (indexCurent === -1) throw new Error("Postarea nu a fost gasita.");

      const post = postari[indexCurent];

      // Afisam continutul articolului
      container.innerHTML = `
        <h2 style="text-align:center; margin-bottom: 30px;">${post.title}</h2>
        <p style="font-size: 18px; line-height: 1.7; text-align:left;">
          ${post.content || post.summary || "Continutul nu este disponibil."}
        </p>
      `;

      // Butoane navigare: inapoi, anterior, urmator
      let butoane = `<a href="blog.html" style="margin: 0 auto;">Inapoi la articole</a>`;

      if (indexCurent > 0) {
        butoane = `
          <a href="articol.html?id=${postari[indexCurent - 1].id}">← Articol anterior</a>
          ${butoane}
        `;
      }

      if (indexCurent < postari.length - 1) {
        butoane += `
          <a href="articol.html?id=${postari[indexCurent + 1].id}">Articol urmator →</a>
        `;
      }

      navContainer.innerHTML = butoane;
    })
    .catch(err => {
      container.innerHTML = `<p style="color:red;">${err.message}</p>`;
    });
}