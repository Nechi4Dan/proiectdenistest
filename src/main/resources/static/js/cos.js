// ===============================
// COS DE CUMPARATURI - cos.js (UPDATED)
// ===============================

document.addEventListener("DOMContentLoaded", () => {
  const username = localStorage.getItem("username");

  if (!username) {
    alert("Trebuie sa fii autentificat pentru a vedea cosul.");
    window.location.href = "index.html";
    return;
  }

  const cosContainer = document.getElementById("lista-cos");
  const golesteBtn = document.getElementById("goleste-cos");

  // Afiseaza toate produsele din cos
  fetch(`http://localhost:8080/api/cart/${username}`)
    .then(res => {
      if (!res.ok) throw new Error("Eroare la incarcarea cosului.");
      return res.json();
    })
    .then(items => {
      if (items.length === 0) {
        cosContainer.innerHTML = `
          <div style="text-align: center; padding: 2rem;">
            <p>Cosul tau este gol.</p>
            <a href="magazin.html" style="color: #007bff; text-decoration: none; font-weight: bold;">Continua cumparaturile</a>
          </div>
        `;
        golesteBtn.style.display = 'none';
        return;
      }

      let total = 0;
      let html = `
        <ul class='lista-produse'>
      `;
      
      items.forEach(item => {
        total += item.quantity * item.price;
        html += `
          <li>
            <img src="${item.image || 'images/default.jpg'}" alt="${item.productName}" />
            <div>
              <h4>${item.productName}</h4>
              <p>Marime: ${item.size}</p>
              <p>Cantitate: ${item.quantity}</p>
              <p>Pret unitar: ${item.price} RON</p>
              <p><strong>Subtotal: ${(item.quantity * item.price).toFixed(2)} RON</strong></p>
              <button onclick="stergeDinCos(${item.id})">Sterge</button>
            </div>
          </li>
        `;
      });
      
      html += `
        </ul>
        <h3>Total: ${total.toFixed(2)} RON</h3>
        <div style="text-align: center; margin-top: 2rem;">
          <a href="checkout.html" style="background-color: #28a745; color: white; padding: 1rem 2rem; text-decoration: none; border-radius: 8px; font-weight: bold; display: inline-block; margin-right: 1rem;">Finalizeaza comanda</a>
          <a href="magazin.html" style="background-color: #007bff; color: white; padding: 1rem 2rem; text-decoration: none; border-radius: 8px; font-weight: bold; display: inline-block;">Continua cumparaturile</a>
        </div>
      `;
      
      cosContainer.innerHTML = html;
    })
    .catch(err => {
      console.error(err);
      cosContainer.innerHTML = "<p style='color:red;'>Eroare la afisarea cosului.</p>";
    });

  // Goleste tot cosul
  golesteBtn.addEventListener("click", () => {
    if (confirm("Esti sigur ca vrei sa golesti cosul?")) {
      fetch(`http://localhost:8080/api/cart/${username}/clear`, {
        method: "DELETE"
      })
        .then(res => {
          if (!res.ok) throw new Error("Eroare la golirea cosului.");
          alert("Cosul a fost golit.");
          window.location.reload();
        })
        .catch(err => {
          alert("Eroare: " + err.message);
        });
    }
  });
});

// Sterge un singur produs din cos
function stergeDinCos(itemId) {
  if (confirm("Stergi acest produs din cos?")) {
    fetch(`http://localhost:8080/api/cart/remove/${itemId}`, {
      method: "DELETE"
    })
      .then(res => {
        if (!res.ok) throw new Error("Eroare la stergerea produsului.");
        alert("Produs sters.");
        window.location.reload();
      })
      .catch(err => {
        alert("Eroare: " + err.message);
      });
  }
}