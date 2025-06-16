// ===============================
// PAYMENT SUCCESS - payment-success.js
// ===============================

document.addEventListener("DOMContentLoaded", () => {
  const username = localStorage.getItem("username");

  // Verificare autentificare
  if (!username) {
    alert("Trebuie sa fii autentificat.");
    window.location.href = "index.html";
    return;
  }

  // Incarcarea detaliilor platii
  loadPaymentDetails();
});

// Incarcarea si afisarea detaliilor platii
function loadPaymentDetails() {
  const paymentData = localStorage.getItem('lastPayment');
  
  if (!paymentData) {
    // Daca nu avem date de plata, redirectionez la cos
    alert("Nu au fost gasite detalii despre plata.");
    window.location.href = "cos.html";
    return;
  }

  try {
    const payment = JSON.parse(paymentData);
    displayPaymentDetails(payment);
    
    // Stergem datele dupa afisare pentru securitate
    localStorage.removeItem('lastPayment');
  } catch (error) {
    console.error("Eroare la parsarea datelor de plata:", error);
    alert("Eroare la incarcarea detaliilor platii.");
    window.location.href = "cos.html";
  }
}

// Afisarea detaliilor platii
function displayPaymentDetails(payment) {
  // Actualizarea titlului si mesajului bazat pe status
  updateStatusMessage(payment.status);
  
  // Detalii tranzactie
  document.getElementById('transaction-id').textContent = payment.transactionId || 'N/A';
  document.getElementById('payment-method').textContent = getPaymentMethodText(payment.paymentType);
  document.getElementById('total-amount').textContent = `${payment.totalAmount.toFixed(2)} RON`;
  
  // Status cu styling
  const statusElement = document.getElementById('payment-status');
  statusElement.textContent = getStatusText(payment.status);
  statusElement.className = `value status ${payment.status}`;
  
  // Data
  const date = new Date(payment.createdAt);
  document.getElementById('payment-date').textContent = formatDate(date);
  
  // Informatii livrare
  document.getElementById('delivery-name').textContent = payment.deliveryName;
  document.getElementById('delivery-phone').textContent = payment.deliveryPhone;
  document.getElementById('delivery-address').textContent = payment.deliveryAddress;
  
  // Instructiuni specifice metodei de plata
  displayPaymentInstructions(payment);
}

// Actualizarea mesajului bazat pe status
function updateStatusMessage(status) {
  const titleElement = document.getElementById('success-title');
  const messageElement = document.getElementById('success-message');
  
  switch (status) {
    case 'PAID':
      titleElement.textContent = 'Plata procesata cu succes!';
      titleElement.style.color = '#28a745';
      messageElement.textContent = 'Comanda ta a fost inregistrata si va fi procesata in cel mai scurt timp.';
      break;
      
    case 'PENDING':
      titleElement.textContent = 'Plata in curs de procesare';
      titleElement.style.color = '#ffc107';
      messageElement.textContent = 'Comanda ta a fost inregistrata si asteapta confirmarea platii.';
      break;
      
    case 'FAILED':
      titleElement.textContent = 'Plata esuata';
      titleElement.style.color = '#dc3545';
      messageElement.textContent = 'A aparut o problema la procesarea platii. Te rugam sa incerci din nou.';
      break;
      
    default:
      titleElement.textContent = 'Status plata necunoscut';
      messageElement.textContent = 'Te rugam sa contactezi serviciul clienti pentru detalii.';
  }
}

// Convertirea tipului de plata in text
function getPaymentMethodText(paymentType) {
  switch (paymentType) {
    case 'CARD':
      return 'Plata cu cardul';
    case 'BANK_TRANSFER':
      return 'Transfer bancar';
    case 'CASH_ON_DELIVERY':
      return 'Ramburs (la livrare)';
    default:
      return paymentType;
  }
}

// Convertirea statusului in text
function getStatusText(status) {
  switch (status) {
    case 'PAID':
      return 'Platita';
    case 'PENDING':
      return 'In asteptare';
    case 'FAILED':
      return 'Esuata';
    default:
      return status;
  }
}

// Formatarea datei
function formatDate(date) {
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  };
  return date.toLocaleDateString('ro-RO', options);
}

// Afisarea instructiunilor specifice metodei de plata
function displayPaymentInstructions(payment) {
  const instructionsContainer = document.getElementById('payment-instructions');
  let html = '';
  
  switch (payment.paymentType) {
    case 'CARD':
      if (payment.status === 'PAID') {
        html = `
          <h4>✅ Plata cu cardul finalizata</h4>
          <div class="info">
            <p><strong>Plata ta a fost procesata cu succes!</strong></p>
            <p>📦 Comanda va fi pregatita si expediate in 1-2 zile lucratoare.</p>
            <p>📧 Vei primi un email de confirmare cu detaliile comenzii.</p>
            <p>🚚 Estimam ca produsele vor ajunge la tine in 2-5 zile lucratoare.</p>
          </div>
        `;
      } else if (payment.status === 'FAILED') {
        html = `
          <h4>❌ Plata cu cardul esuata</h4>
          <div class="warning">
            <p><strong>Plata nu a putut fi procesata.</strong></p>
            <p>Motivele posibile:</p>
            <ul>
              <li>Fonduri insuficiente</li>
              <li>Date card incorecte</li>
              <li>Card expirat sau blocat</li>
              <li>Probleme temporare ale bancii</li>
            </ul>
            <p>Te rugam sa verifici datele cardului si sa incerci din nou.</p>
          </div>
        `;
      }
      break;
      
    case 'BANK_TRANSFER':
      html = `
        <h4>🏦 Transfer bancar - Instructiuni</h4>
        <div class="bank-transfer-details">
          <p><strong>Pentru a finaliza comanda, efectueaza transferul bancar cu urmatoarele detalii:</strong></p>
          <br>
          <p><strong>IBAN:</strong> RO12DEMO1234567890123456</p>
          <p><strong>Beneficiar:</strong> Denis Alibec SRL</p>
          <p><strong>Banca:</strong> Demo Bank</p>
          <p><strong>Suma:</strong> ${payment.totalAmount.toFixed(2)} RON</p>
          <p><strong>Referinta:</strong> Comanda #${payment.id || 'TBD'}</p>
        </div>
        <div class="warning">
          <p><strong>⚠️ IMPORTANT:</strong></p>
          <p>• Daca nu observam ca s-a facut viramentul in <strong>3 zile lucratoare</strong>, comanda va fi anulata automat.</p>
          <p>• Te rugam sa mentionezi referinta comenzii la efectuarea transferului.</p>
          <p>• Dupa confirmarea platii, produsele vor fi expediate in 1-2 zile lucratoare.</p>
        </div>
      `;
      break;
      
    case 'CASH_ON_DELIVERY':
      html = `
        <h4>💰 Plata ramburs confirmata</h4>
        <div class="info">
          <p><strong>Comanda ta a fost confirmata pentru plata ramburs!</strong></p>
          <p>📦 Produsele vor fi pregatite si expediate in 1-2 zile lucratoare.</p>
          <p>🚚 Curierul va ajunge la adresa specificata in 2-5 zile lucratoare.</p>
          <p>💵 Vei plati contravaloarea comenzii (<strong>${payment.totalAmount.toFixed(2)} RON</strong>) in numerar, la livrare.</p>
          <p>📋 Te rugam sa ai suma exacta pregatita pentru curier.</p>
        </div>
        <div class="warning">
          <p><strong>📞 Curierul te va contacta inainte de livrare</strong> la numarul de telefon: <strong>${payment.deliveryPhone}</strong></p>
        </div>
      `;
      break;
      
    default:
      html = `
        <h4>Metoda de plata: ${getPaymentMethodText(payment.paymentType)}</h4>
        <p>Pentru detalii suplimentare, te rugam sa contactezi serviciul clienti.</p>
      `;
  }
  
  instructionsContainer.innerHTML = html;
}

// Functie pentru redirectionare la cos (in caz de eroare)
function redirectToCart() {
  setTimeout(() => {
    window.location.href = "cos.html";
  }, 3000);
}