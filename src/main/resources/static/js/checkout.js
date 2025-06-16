// ===============================
// CHECKOUT - checkout.js
// ===============================

document.addEventListener("DOMContentLoaded", () => {
  const username = localStorage.getItem("username");

  // Verificare autentificare
  if (!username) {
    alert("Trebuie sa fii autentificat pentru a accesa checkout-ul.");
    window.location.href = "index.html";
    return;
  }

  // Incarcarea sumarului comenzii
  loadOrderSummary();
  
  // Popularea anilor pentru data expirare
  populateExpiryYears();
  
  // Event listeners
  setupEventListeners();
});

// Incarcarea sumarului comenzii din cos
function loadOrderSummary() {
  const username = localStorage.getItem("username");
  
  fetch(`http://localhost:8080/api/cart/${username}`)
    .then(res => {
      if (!res.ok) throw new Error("Eroare la incarcarea cosului.");
      return res.json();
    })
    .then(items => {
      if (items.length === 0) {
        alert("Cosul este gol. Vei fi redirectionat la magazin.");
        window.location.href = "magazin.html";
        return;
      }

      displayOrderSummary(items);
    })
    .catch(err => {
      console.error(err);
      alert("Eroare la incarcarea comenzii.");
    });
}

// Afisarea sumarului comenzii
function displayOrderSummary(items) {
  const cartSummary = document.getElementById('cart-summary');
  const totalAmountElement = document.getElementById('total-amount');
  
  let total = 0;
  let html = '';
  
  items.forEach(item => {
    const itemTotal = item.quantity * item.price;
    total += itemTotal;
    
    html += `
      <div class="cart-item">
        <div class="item-details">
          <div class="item-name">${item.productName}</div>
          <small>Marime: ${item.size} | Cantitate: ${item.quantity}</small>
        </div>
        <div class="item-price">${itemTotal.toFixed(2)} RON</div>
      </div>
    `;
  });
  
  cartSummary.innerHTML = html;
  totalAmountElement.textContent = `${total.toFixed(2)} RON`;
}

// Popularea anilor pentru selectarea datei de expirare
function populateExpiryYears() {
  const yearSelect = document.getElementById('expiry-year');
  const currentYear = new Date().getFullYear();
  
  for (let year = currentYear; year <= currentYear + 10; year++) {
    const option = document.createElement('option');
    option.value = year;
    option.textContent = year;
    yearSelect.appendChild(option);
  }
}

// Configurarea event listeners
function setupEventListeners() {
  // Event listener pentru schimbarea metodei de plata
  const paymentMethods = document.querySelectorAll('input[name="paymentType"]');
  paymentMethods.forEach(method => {
    method.addEventListener('change', handlePaymentMethodChange);
  });
  
  // Event listener pentru formularul de checkout
  const checkoutForm = document.getElementById('checkout-form');
  checkoutForm.addEventListener('submit', handleCheckoutSubmit);
  
  // Formatare numar card
  const cardNumberInput = document.getElementById('card-number');
  cardNumberInput.addEventListener('input', function(e) {
    // Permite doar cifre
    this.value = this.value.replace(/\D/g, '');
  });
  
  // Formatare CVV
  const cvvInput = document.getElementById('cvv');
  cvvInput.addEventListener('input', function(e) {
    // Permite doar cifre, maxim 3
    this.value = this.value.replace(/\D/g, '').substring(0, 3);
  });
  
  // Formatare telefon
  const phoneInput = document.getElementById('delivery-phone');
  phoneInput.addEventListener('input', function(e) {
    // Permite doar cifre
    this.value = this.value.replace(/\D/g, '');
  });
}

// Gestionarea schimbarii metodei de plata
function handlePaymentMethodChange(e) {
  const selectedMethod = e.target.value;
  
  // Ascunderea tuturor sectiunilor specifice
  document.getElementById('card-details').style.display = 'none';
  document.getElementById('bank-transfer-info').style.display = 'none';
  document.getElementById('cash-delivery-info').style.display = 'none';
  
  // Resetarea validarilor pentru card
  resetCardValidation();
  
  // Afisarea sectiunii corespunzatoare
  switch (selectedMethod) {
    case 'CARD':
      document.getElementById('card-details').style.display = 'block';
      setCardValidation(true);
      break;
    case 'BANK_TRANSFER':
      document.getElementById('bank-transfer-info').style.display = 'block';
      break;
    case 'CASH_ON_DELIVERY':
      document.getElementById('cash-delivery-info').style.display = 'block';
      break;
  }
}

// Setarea validarilor pentru datele cardului
function setCardValidation(required) {
  const cardFields = ['card-number', 'cvv', 'expiry-month', 'expiry-year'];
  cardFields.forEach(fieldId => {
    const field = document.getElementById(fieldId);
    if (field) {
      field.required = required;
    }
  });
}

// Resetarea validarilor pentru card
function resetCardValidation() {
  setCardValidation(false);
  // Curatarea valorilor
  document.getElementById('card-number').value = '';
  document.getElementById('cvv').value = '';
  document.getElementById('expiry-month').value = '';
  document.getElementById('expiry-year').value = '';
}

// Gestionarea submit-ului formularului
function handleCheckoutSubmit(e) {
  e.preventDefault();
  
  const formData = new FormData(e.target);
  const username = localStorage.getItem("username");
  
  // Construirea obiectului de request
  const paymentRequest = {
    username: username,
    deliveryName: formData.get('deliveryName'),
    deliveryPhone: formData.get('deliveryPhone'),
    deliveryAddress: formData.get('deliveryAddress'),
    paymentType: formData.get('paymentType')
  };
  
  // Adaugarea datelor specifice metodei de plata
  if (paymentRequest.paymentType === 'CARD') {
    paymentRequest.cardNumber = formData.get('cardNumber');
    paymentRequest.cvv = formData.get('cvv');
    paymentRequest.expiryMonth = formData.get('expiryMonth');
    paymentRequest.expiryYear = formData.get('expiryYear');
  } else if (paymentRequest.paymentType === 'BANK_TRANSFER') {
    paymentRequest.iban = 'RO12DEMO1234567890123456'; // IBAN demo
  }
  
  // Validare suplimentara
  if (!validateCheckoutForm(paymentRequest)) {
    return;
  }
  
  // Dezactivarea butonului de submit
  const submitBtn = e.target.querySelector('button[type="submit"]');
  submitBtn.disabled = true;
  submitBtn.textContent = 'Se proceseaza...';
  
  // Trimiterea cererii de plata
  processPayment(paymentRequest, submitBtn);
}

// Validare formular
function validateCheckoutForm(request) {
  // Validare telefon
  if (!/^\d+$/.test(request.deliveryPhone)) {
    alert('Telefonul trebuie sa contina doar cifre.');
    return false;
  }
  
  // Validari specifice pentru card
  if (request.paymentType === 'CARD') {
    if (!/^\d{16}$/.test(request.cardNumber)) {
      alert('Numarul cardului trebuie sa contina exact 16 cifre.');
      return false;
    }
    
    if (!/^\d{3}$/.test(request.cvv)) {
      alert('CVV-ul trebuie sa contina exact 3 cifre.');
      return false;
    }
    
    if (!request.expiryMonth || !request.expiryYear) {
      alert('Selectati luna si anul de expirare.');
      return false;
    }
    
    // Validare data expirare
    const currentDate = new Date();
    const currentMonth = currentDate.getMonth() + 1; // getMonth() returneaza 0-11, deci adaugam 1
    const currentYear = currentDate.getFullYear();
    const expMonth = parseInt(request.expiryMonth);
    const expYear = parseInt(request.expiryYear);
    
    // Verificare: anul trebuie sa fie mai mare sau egal cu anul curent
    // Daca este anul curent, luna trebuie sa fie mai mare sau egala cu luna curenta
    if (expYear < currentYear || (expYear === currentYear && expMonth < currentMonth)) {
      alert(`Data de expirare a cardului nu poate fi in trecut. Luna curenta: ${currentMonth}/${currentYear}`);
      return false;
    }
  }
  
  return true;
}

// Procesarea platii
function processPayment(paymentRequest, submitBtn) {
  fetch('http://localhost:8080/api/payments/process', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(paymentRequest)
  })
    .then(res => {
      if (!res.ok) {
        return res.text().then(text => {
          throw new Error(text);
        });
      }
      return res.json();
    })
    .then(payment => {
      // Succes - redirectionare la pagina de confirmare
      localStorage.setItem('lastPayment', JSON.stringify(payment));
      window.location.href = 'payment-success.html';
    })
    .catch(err => {
      console.error('Eroare la procesarea platii:', err);
      alert('Eroare la procesarea platii: ' + err.message);
    })
    .finally(() => {
      // Reactivarea butonului
      submitBtn.disabled = false;
      submitBtn.textContent = 'Finalizeaza comanda';
    });
}