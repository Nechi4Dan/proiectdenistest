// ==============================
// MAGAZIN ONLINE: Afisare TOATE produsele + search prin DB + SUGESTII
// ==============================

// Selectam containerul unde vom afisa toate produsele
const listaProduse = document.getElementById("lista-produse");

// ========== VARIABILA PENTRU CACHE PRODUSE (ADAUGATA PENTRU SUGESTII) ========== 
let allProducts = []; // Pentru sugestii și filtrare frontend

// ========== CODUL ORIGINAL (PASTRAT EXACT) ========== 

// Facem request catre backend pentru a lua produsele
function loadAllProducts() {
  fetch("http://localhost:8080/api/products")
    .then(response => {
      if (!response.ok) throw new Error("Eroare la incarcarea produselor.");
      return response.json();
    })
    .then(produse => {
      // ADAUGAT: Salvam produsele pentru sugestii
      allProducts = produse;
      console.log('Produse incarcate:', produse.length);
      
      // Incarcam categoriile din produsele incarcate (fallback)
      setTimeout(() => {
        if (allProducts.length > 0) {
          loadCategoriesFromFrontend();
        }
      }, 1000);
      
      // Parcurgem fiecare produs si il afisam in pagina
      displayProducts(produse);
      updateResultsInfo(produse.length, 'toate produsele');
    })
    .catch(error => {
      // Afisam eroarea daca nu se pot incarca produsele
      listaProduse.innerHTML = `<p style="color:red;">${error.message}</p>`;
    });
}

function displayProducts(produse) {
  listaProduse.innerHTML = '';
  
  if (produse.length === 0) {
    listaProduse.innerHTML = '<p style="text-align:center; color:#666;">Nu au fost gasite produse.</p>';
    return;
  }
  
  produse.forEach(prod => {
    // Cream un card pentru fiecare produs
    const card = document.createElement("div");
    card.className = "produs";
    card.style.cursor = "pointer";

    // Cand dam click pe card, mergem la pagina cu detalii
    card.onclick = () => {
      window.location.href = `produs.html?id=${prod.id}`;
    };

    // Completam continutul HTML al cardului
    card.innerHTML = `
      <img src="${prod.image || 'images/default.jpg'}" alt="${prod.name}" style="width:100%; height:200px; object-fit:cover;" />
      <h3>${prod.name}</h3>
      <p>Pret: ${prod.price} RON</p>
    `;

    // Adaugam cardul in containerul principal
    listaProduse.appendChild(card);
  });
}

// ========== FUNCTII NOI PENTRU CAUTARE PRIN DB ========== 

// Incarcarea categoriilor pentru dropdown (din DB)
function loadCategories() {
  fetch("http://localhost:8080/api/products/categories")
    .then(response => {
      if (!response.ok) throw new Error("Eroare la incarcarea categoriilor.");
      return response.json();
    })
    .then(categories => {
      console.log('Categorii din backend:', categories);
      populateCategories(categories);
    })
    .catch(error => {
      console.warn('Nu pot incarca categoriile din backend, folosesc frontend:', error);
      // ADAUGAT: Fallback pentru categorii din frontend
      loadCategoriesFromFrontend();
    });
}

// Helper pentru popularea dropdown-ului de categorii
function populateCategories(categories) {
  const categorySelect = document.getElementById('category-filter');
  if (!categorySelect) {
    console.error('Nu gasesc elementul category-filter');
    return;
  }
  
  categorySelect.innerHTML = '<option value="">Toate categoriile</option>';
  
  categories.forEach(category => {
    categorySelect.innerHTML += `<option value="${category}">${category}</option>`;
  });
  
  console.log('Categorii populate:', categories);
}

// Functia principala de cautare (prin DB)
function performSearch() {
  const searchTerm = document.getElementById('search-input').value.trim();
  const category = document.getElementById('category-filter').value;
  const minPrice = document.getElementById('min-price').value;
  const maxPrice = document.getElementById('max-price').value;
  
  // Ascundem sugestiile cand cautam
  hideSuggestions();
  
  // Construirea URL-ului pentru cautare
  let url = 'http://localhost:8080/api/products/search?';
  const params = [];
  
  if (searchTerm) params.push(`name=${encodeURIComponent(searchTerm)}`);
  if (category) params.push(`category=${encodeURIComponent(category)}`);
  if (minPrice) params.push(`minPrice=${minPrice}`);
  if (maxPrice) params.push(`maxPrice=${maxPrice}`);
  
  url += params.join('&');
  
  // Efectuarea cererii catre DB
  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error("Eroare la cautare.");
      return response.json();
    })
    .then(produse => {
      displayProducts(produse);
      
      // Construirea textului pentru informatii
      let searchInfo = [];
      if (searchTerm) searchInfo.push(`"${searchTerm}"`);
      if (category) searchInfo.push(`categoria "${category}"`);
      if (minPrice || maxPrice) {
        const priceRange = `${minPrice || 0} - ${maxPrice || '∞'} RON`;
        searchInfo.push(`pretul ${priceRange}`);
      }
      
      const searchText = searchInfo.length > 0 ? 
        `cautarea: ${searchInfo.join(', ')}` : 'toate produsele';
      
      updateResultsInfo(produse.length, searchText);
    })
    .catch(error => {
      console.warn('Backend nu merge, folosesc filtrarea frontend:', error);
      performFrontendSearch();
    });
}

// Resetarea filtrelor
function clearFilters() {
  document.getElementById('search-input').value = '';
  document.getElementById('category-filter').value = '';
  document.getElementById('min-price').value = '';
  document.getElementById('max-price').value = '';
  
  // Ascundem sugestiile
  hideSuggestions();
  
  // Reincarcarea tuturor produselor
  loadAllProducts();
}

// Actualizarea informatiilor despre rezultate
function updateResultsInfo(count, searchText) {
  const resultsInfo = document.getElementById("results-info");
  if (resultsInfo) {
    resultsInfo.innerHTML = `Gasite ${count} produse pentru ${searchText}`;
  }
}

// ========== FUNCTII ADAUGATE PENTRU SUGESTII ========== 

// Crearea si afisarea sugestiilor
function showSuggestions(suggestions) {
  let suggestionsContainer = document.getElementById('suggestions-container');
  
  // Daca nu exista containerul, il cream
  if (!suggestionsContainer) {
    suggestionsContainer = document.createElement('div');
    suggestionsContainer.id = 'suggestions-container';
    suggestionsContainer.style.cssText = `
      position: absolute;
      top: 100%;
      left: 0;
      right: 0;
      background: white;
      border: 1px solid #ddd;
      border-top: none;
      border-radius: 0 0 6px 6px;
      max-height: 200px;
      overflow-y: auto;
      z-index: 1000;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      display: none;
    `;
    
    // Adaugam containerul dupa input-ul de cautare
    const searchInput = document.getElementById('search-input');
    const filterGroup = searchInput.parentNode;
    filterGroup.style.position = 'relative';
    filterGroup.appendChild(suggestionsContainer);
  }
  
  // Curatam sugestiile anterioare
  suggestionsContainer.innerHTML = '';
  
  if (suggestions.length === 0) {
    suggestionsContainer.style.display = 'none';
    return;
  }
  
  // Afisam sugestiile
  suggestions.forEach(suggestion => {
    const suggestionItem = document.createElement('div');
    suggestionItem.style.cssText = `
      padding: 12px 15px;
      cursor: pointer;
      border-bottom: 1px solid #eee;
      transition: background-color 0.2s ease;
    `;
    suggestionItem.textContent = suggestion;
    
    // Event listener pentru click pe sugestie
    suggestionItem.addEventListener('click', function() {
      document.getElementById('search-input').value = suggestion;
      suggestionsContainer.style.display = 'none';
      performSearch();
    });
    
    // Hover effect
    suggestionItem.addEventListener('mouseenter', function() {
      this.style.backgroundColor = '#f5f5f5';
    });
    
    suggestionItem.addEventListener('mouseleave', function() {
      this.style.backgroundColor = 'white';
    });
    
    suggestionsContainer.appendChild(suggestionItem);
  });
  
  suggestionsContainer.style.display = 'block';
}

// Ascunderea sugestiilor
function hideSuggestions() {
  const suggestionsContainer = document.getElementById('suggestions-container');
  if (suggestionsContainer) {
    setTimeout(() => {
      suggestionsContainer.style.display = 'none';
    }, 150); // Delay pentru a permite click-ul pe sugestii
  }
}

// Generarea sugestiilor pe baza input-ului
function generateSuggestions(searchTerm) {
  if (!searchTerm || searchTerm.length < 2) {
    hideSuggestions();
    return;
  }
  
  const lowerSearchTerm = searchTerm.toLowerCase();
  const suggestions = new Set(); // Folosim Set pentru a evita duplicatele
  
  // Cautam in numele produselor
  allProducts.forEach(product => {
    const productName = product.name.toLowerCase();
    
    // Daca numele contine termenul de cautare
    if (productName.includes(lowerSearchTerm)) {
      // Adaugam numele complet
      suggestions.add(product.name);
      
      // Adaugam si cuvintele individuale din nume
      const words = product.name.split(' ');
      words.forEach(word => {
        if (word.toLowerCase().includes(lowerSearchTerm) && word.length > 2) {
          suggestions.add(word);
        }
      });
    }
    
    // Daca un cuvant din nume incepe cu termenul de cautare
    const nameWords = product.name.split(' ');
    nameWords.forEach(word => {
      if (word.toLowerCase().startsWith(lowerSearchTerm)) {
        suggestions.add(word);
        suggestions.add(product.name); // Si numele complet
      }
    });
  });
  
  // Convertim Set la Array si limitam la primele 6 sugestii
  const suggestionArray = Array.from(suggestions).slice(0, 6);
  
  console.log('Sugestii generate pentru "' + searchTerm + '":', suggestionArray);
  showSuggestions(suggestionArray);
}

// ========== FUNCTII ADAUGATE PENTRU FILTRAREA FRONTEND (FALLBACK) ========== 

// Incarcarea categoriilor din produsele deja incarcate (fallback)
function loadCategoriesFromFrontend() {
  if (allProducts.length === 0) {
    console.warn('Nu am produse incarcate pentru a extrage categoriile');
    return;
  }
  
  const categories = [...new Set(allProducts.map(p => p.category))].filter(cat => cat).sort();
  console.log('Categorii din frontend:', categories);
  populateCategories(categories);
}

// Filtrarea produselor in frontend (cand backend nu merge)
function performFrontendSearch() {
  const searchTerm = document.getElementById('search-input').value.trim().toLowerCase();
  const category = document.getElementById('category-filter').value;
  const minPrice = parseFloat(document.getElementById('min-price').value) || 0;
  const maxPrice = parseFloat(document.getElementById('max-price').value) || Infinity;
  
  // Filtrarea produselor in JavaScript
  let filteredProducts = allProducts.filter(product => {
    // Cautare in nume si descriere
    const matchesSearch = !searchTerm || 
      product.name.toLowerCase().includes(searchTerm) ||
      (product.description && product.description.toLowerCase().includes(searchTerm));
    
    // Filtrare dupa categorie
    const matchesCategory = !category || product.category === category;
    
    // Filtrare dupa pret
    const matchesPrice = product.price >= minPrice && product.price <= maxPrice;
    
    return matchesSearch && matchesCategory && matchesPrice;
  });
  
  // Afisarea rezultatelor
  displayProducts(filteredProducts);
  
  // Construirea textului pentru informatii
  let searchInfo = [];
  if (searchTerm) searchInfo.push(`"${searchTerm}"`);
  if (category) searchInfo.push(`categoria "${category}"`);
  if (minPrice > 0 || maxPrice < Infinity) {
    const priceRange = `${minPrice || 0} - ${maxPrice === Infinity ? '∞' : maxPrice} RON`;
    searchInfo.push(`pretul ${priceRange}`);
  }
  
  const searchText = searchInfo.length > 0 ? 
    `cautarea: ${searchInfo.join(', ')}` : 'toate produsele';
  
  updateResultsInfo(filteredProducts.length, searchText + ' (frontend)');
}

// ========== INITIALIZARE ========== 

// La incarcarea paginii
document.addEventListener('DOMContentLoaded', function() {
  loadCategories();
  loadAllProducts();
  
  // Event listeners pentru cautare cu sugestii
  const searchInput = document.getElementById('search-input');
  if (searchInput) {
    // Cautare la apasarea tastei Enter
    searchInput.addEventListener('keypress', function(e) {
      if (e.key === 'Enter') {
        hideSuggestions();
        performSearch();
      }
    });
    
    // Generare sugestii in timp real
    searchInput.addEventListener('input', function(e) {
      generateSuggestions(e.target.value);
    });
    
    // Ascundere sugestii cand input-ul pierde focus-ul
    searchInput.addEventListener('blur', function() {
      hideSuggestions();
    });
    
    // Arata sugestiile cand input-ul primeste focus (daca are text)
    searchInput.addEventListener('focus', function() {
      if (this.value.length >= 2) {
        generateSuggestions(this.value);
      }
    });
  }
  
  // Cautare automata la schimbarea categoriei
  const categoryFilter = document.getElementById('category-filter');
  if (categoryFilter) {
    categoryFilter.addEventListener('change', function() {
      console.log('Categoria schimbata la:', this.value);
      performSearch();
    });
  }
  
  // Event listeners pentru filtrele de pret
  const minPrice = document.getElementById('min-price');
  const maxPrice = document.getElementById('max-price');
  
  if (minPrice) {
    minPrice.addEventListener('change', function() {
      console.log('Pret minim schimbat la:', this.value);
      performSearch();
    });
  }
  
  if (maxPrice) {
    maxPrice.addEventListener('change', function() {
      console.log('Pret maxim schimbat la:', this.value);
      performSearch();
    });
  }
  
  // Ascundere sugestii cand se da click in afara
  document.addEventListener('click', function(e) {
    if (!e.target.closest('.filter-group') && !e.target.closest('#suggestions-container')) {
      hideSuggestions();
    }
  });
});