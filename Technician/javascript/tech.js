/* ================================
   PHONE INPUT RESTRICTION
   First digit: 6–9 only
================================ */

function restrictPhoneInput(event) {
  const key = event.key;
  const value = event.target.value;

  // Allow only numbers
  if (!/[0-9]/.test(key)) return false;

  // First digit must be 6–9
  if (value.length === 0 && !/[6-9]/.test(key)) {
    return false;
  }

  return true;
}
function gotoHome(){
window.location.href = "../Home.html";
}

/* ================================
   PHONE VALIDATION FINAL CHECK
================================ */

function isValidPhone(phone) {
  return /^[6-9][0-9]{9}$/.test(phone);
}

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
  const password = document.getElementById("techPassword").value;
  const msg = document.getElementById("passwordMsg");

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

  if (!isValidPhone(phone)) {
    error.textContent =
      "Phone number must be 10 digits and start with 6–9";
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
    window.location.href = "./Technician_Dashboard.html";
  }, 1000); // 1 second delay

  return false;
}