/* ================================
   PHONE INPUT RESTRICTION
   First digit: 6–9 only
================================ */


/* ================================
   PHONE VALIDATION FINAL CHECK
================================ */


/* ================================
   PASSWORD VALIDATION
================================ */

function isStrongPassword(password) {
  const pattern = /^[A-Z](?=.*[a-z])(?=.*[@$!%*?&]).{7,}$/;
  return pattern.test(password);
}

/* ================================
   LIVE PASSWORD MESSAGE
================================ */

function checkPasswordStrength() {
 
  if (password === "") {
    msg.textContent = "";
    return;
  }

  if (isStrongPassword(password)) {
    msg.textContent = "✅ Strong password";
    msg.style.color = "green";
  } else {
    msg.textContent =
      "❌ Password must start with Capital, have lowercase, special character & 8+ characters";
    msg.style.color = "red";
  }
}
function gotoHome(){
window.location.href = "../Home.html";
}

/* ================================
   LOGIN SUBMIT
================================ */

function technicianLogin(event) {
  event.preventDefault();

  const phone = document.getElementById("techPhone").value.trim();
  const password = document.getElementById("techPassword").value.trim();
  const error = document.getElementById("techError");

  error.textContent = "";
  error.style.color = "red";

  if (phone === "") {
    error.textContent = "Phone number is required";
    return false;
  }

  
  if (password === "") {
    error.textContent = "Password is required";
    return false;
  }

  if (!isStrongPassword(password)) {
    error.textContent = "Password strength rules not matched";
    return false;
  }

  error.style.color = "green";
  error.textContent = "✅ Login successful";

  setTimeout(() => {
    window.location.href = "./SuperVisorDashboardIndex.html";
  }, 1000); // 1 second delay

  return false;
}