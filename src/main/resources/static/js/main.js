// ============================
// AFISARE FORMULAR LOGIN / REGISTER
// ============================
function showForm(formType) {
  const login = document.getElementById("login-popup");
  const register = document.getElementById("register-popup");

  if (formType === "login") {
    login.style.display = "block";
    register.style.display = "none";
  } else {
    login.style.display = "none";
    register.style.display = "block";
  }
}

window.addEventListener("click", function (e) {
  const login = document.getElementById("login-popup");
  const register = document.getElementById("register-popup");

  if (e.target === login || e.target === register) {
    login.style.display = "none";
    register.style.display = "none";
  }
});

function closeForms() {
  document.getElementById("login-popup").style.display = "none";
  document.getElementById("register-popup").style.display = "none";
}

// ============================
// DOMContentLoaded: verifica daca userul e logat
// ============================
document.addEventListener("DOMContentLoaded", function () {
  const username = localStorage.getItem("username");
  const navRight = document.querySelector(".nav-right");

  if (username && navRight) {
    navRight.innerHTML = `
      <span style="margin-right: 1rem;">Salut, <strong>${username}</strong></span>
      <a href="cos.html">Cosul meu</a>
      <button onclick="logout()">Logout</button>
    `;
  }

  const registerForm = document.getElementById("register-form");
  const loginForm = document.getElementById("login-form");

  // ============================
  // REGISTER
  // ============================
  if (registerForm) {
    registerForm.addEventListener("submit", async function (e) {
      e.preventDefault();

      const userData = {
        username: document.getElementById("register-username").value,
        email: document.getElementById("register-email").value,
        password: document.getElementById("register-password").value,
        firstName: document.getElementById("register-firstname").value,
        lastName: document.getElementById("register-lastname").value,
        phone: document.getElementById("register-phone").value,
      };

      try {
        const response = await fetch("http://localhost:8080/api/auth/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(userData),
        });

        if (response.ok) {
          alert("Utilizator inregistrat cu succes!");
          registerForm.reset();
          showForm("login");
        } else {
          const message = await response.text();
          alert("Eroare: " + message);
        }
      } catch (err) {
        console.error("Eroare retea:", err);
        alert("Eroare la conectarea cu serverul.");
      }
    });
  }

  // ============================
  // LOGIN
  // ============================
  if (loginForm) {
    loginForm.addEventListener("submit", async function (e) {
      e.preventDefault();

      const loginData = {
        username: document.getElementById("login-username").value,
        password: document.getElementById("login-password").value,
      };

      try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(loginData),
        });

        if (response.ok) {
          localStorage.setItem("username", loginData.username);
          alert("Autentificare reusita!");
          loginForm.reset();
          window.location.href = "utilizator.html";
        } else {
          const message = await response.text();
          alert("Eroare la autentificare: " + message);
        }
      } catch (err) {
        console.error("Eroare retea:", err);
        alert("Eroare la conectarea cu serverul.");
      }
    });
  }
});

// ============================
// LOGOUT FUNCTION
// ============================
function logout() {
  localStorage.removeItem("username");
  window.location.reload();
}

