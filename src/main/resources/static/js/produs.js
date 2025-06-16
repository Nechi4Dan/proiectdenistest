// ===========================
// DETALII PRODUS
// ===========================

const produsContainer = document.getElementById("detalii-produs");
const params = new URLSearchParams(window.location.search);
const id = params.get("id");

if (!id) {
  produsContainer.innerHTML = "<p style='color:red;'>Produsul nu a fost specificat.</p>";
} else {
  fetch(`http://localhost:8080/api/products/${id}`)
    .then(res => {
      if (!res.ok) throw new Error("Produsul nu a fost gasit.");
      return res.json();
    })
    .then(prod => {
      produsContainer.innerHTML = `
        <div class="container-detalii">
          <div class="stanga">
            <h2>${prod.name}</h2>
            <img src="${prod.image || 'images/default.jpg'}" alt="${prod.name}" />
          </div>
          <div class="dreapta">
            <p><strong>Pret:</strong> ${prod.price} RON</p>
            <p><strong>Stoc:</strong> ${prod.stock} buc.</p>
            <p><strong>Categorie:</strong> ${prod.category}</p>
            <p><strong>Descriere:</strong><br>${prod.description}</p>

            <hr />

            <label for="product-size"><strong>Marime:</strong></label>
            <select id="product-size">
              <option value="S">S</option>
              <option value="M">M</option>
              <option value="L">L</option>
            </select>

            <label for="product-quantity"><strong>Cantitate:</strong></label>
            <input type="number" id="product-quantity" min="1" value="1" />

            <button onclick="adaugaInCos(${prod.id})">Adauga in cos</button>

            <br><br>
            <a href="magazin.html" class="btn-inapoi">Inapoi la produse</a>
          </div>
        </div>
      `;
    })
    .catch(err => {
      produsContainer.innerHTML = `<p style="color:red;">${err.message}</p>`;
    });
}

// ===========================
// ADAUGA IN COS
// ===========================
function adaugaInCos(productId) {
  const username = localStorage.getItem("username");
  if (!username) {
    alert("Trebuie sa fii autentificat pentru a adauga produse in cos.");
    return;
  }

  const size = document.getElementById("product-size").value;
  const quantity = parseInt(document.getElementById("product-quantity").value);

  const requestData = {
    productId: productId,
    size: size,
    quantity: quantity
  };

  fetch(`http://localhost:8080/api/cart/${username}/add`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(requestData)
  })
    .then(res => {
      if (!res.ok) throw new Error("Eroare la adaugarea in cos.");
      return res.json();
    })
    .then(data => {
      alert("Produs adaugat in cos cu succes!");
    })
    .catch(err => {
      console.error("Eroare:", err);
      alert("Eroare: " + err.message);
    });
}
