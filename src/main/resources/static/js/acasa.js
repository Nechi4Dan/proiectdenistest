// ==============================
// SECTIUNEA: Produse (preview pe homepage)
// ==============================

const listaProduse = document.getElementById("lista-produse");

fetch("http://localhost:8080/api/products")
  .then(response => {
    if (!response.ok) {
      throw new Error("Eroare la incarcarea produselor din backend.");
    }
    return response.json();
  })
  .then(produse => {
    const iduriDorite = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19];
    const produseFiltrate = produse.filter(prod => iduriDorite.includes(prod.id));

    produseFiltrate.forEach(prod => {
      const card = document.createElement("div");
      card.className = "produs";
      card.style.cursor = "pointer";

      card.onclick = () => {
        window.location.href = `produs.html?id=${prod.id}`;
      };

      card.innerHTML = `
        <img src="${prod.image || 'images/default.jpg'}" alt="${prod.name}" style="width:100%; height:200px; object-fit:cover;" />
        <h3>${prod.name}</h3>
        <p>Pret: ${prod.price} RON</p>
      `;

      listaProduse.appendChild(card);
    });
  })
  .catch(error => {
    listaProduse.innerHTML = `<p style="color:red;">${error.message}</p>`;
  });

// ==============================
// SECTIUNEA: Blog (preview pe homepage)
// ==============================

const listaBlog = document.getElementById("lista-blog");

fetch("http://localhost:8080/api/blog-posts")
  .then(response => {
    if (!response.ok) {
      throw new Error("Eroare la incarcarea postarilor de blog.");
    }
    return response.json();
  })
  .then(postari => {
    const iduriDoriteBlog = [4, 5, 6];
    const primeleTrei = postari.filter(post => iduriDoriteBlog.includes(post.id));

    primeleTrei.forEach(post => {
      const card = document.createElement("div");
      card.className = "postare";
      card.style.cursor = "pointer";

      card.onclick = () => {
        window.location.href = `articol.html?id=${post.id}`;
      };

      card.innerHTML = `
        <img src="${post.image || 'images/blog/default.jpg'}" alt="${post.title}" style="width:100%; height:180px; object-fit:cover;" />
        <h3>${post.title}</h3>
        <p>${post.summary}</p>
        <a href="articol.html?id=${post.id}" onclick="event.stopPropagation()">Citeste mai mult</a>
      `;

      listaBlog.appendChild(card);
    });
  })
  .catch(error => {
    listaBlog.innerHTML = `<p style="color:red;">${error.message}</p>`;
  });