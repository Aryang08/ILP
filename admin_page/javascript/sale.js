function toggleSidebar() {
  document.getElementById("sidebar").classList.toggle("open");
}

function toggleExt() {
  let val = document.getElementById("extended").value;
  let box = document.getElementById("extBox");
  box.classList.toggle("hidden", val !== "Yes");
}

/* VALIDATION + PREVIEW */
function openModal() {

  let name = document.getElementById("name").value.trim();
  let phone = document.getElementById("phone").value.trim();
  let address = document.getElementById("address").value.trim();
  let serial = document.getElementById("serial").value.trim();
  let date = document.getElementById("date").value;
  let invoice = document.getElementById("invoice").value.trim();

  if (!name || !phone || !address || !amount || !date || !invoice) {
    alert("Please fill all required fields (*)");
    return;
  }

  let preview = `
    <b>Name:</b> ${name}<br>
    <b>Phone:</b> ${phone}<br>
    <b>Address:</b> ${address}<br>
    <b>Serial Number:</b> ${serial}<br>
    <b>Date:</b> ${date}<br>
    <b>Invoice:</b> ${invoice}
  `;

  document.getElementById("preview").innerHTML = preview;
  document.getElementById("modal").classList.add("show");
}

/* FINAL SAVE */
function confirmSave() {
  document.getElementById("modal").classList.remove("show");
  alert("Data Added Successfully");
}

/* CLICK OUTSIDE CLOSE */
/* SIDEBAR */
function toggleSidebar() {
  document.querySelector(".sidebar").classList.toggle("open");
}

/* EXTENDED WARRANTY TOGGLE */
function toggleExt() {
  const extBox = document.getElementById("extBox");
  const value = document.getElementById("extended").value;
  value === "Yes"
    ? extBox.classList.remove("hidden")
    : extBox.classList.add("hidden");
}

/* LOGOUT */
function openLogoutPopup() {
  document.getElementById("logoutPopup").classList.add("show");
}

function closeLogoutPopup() {
  document.getElementById("logoutPopup").classList.remove("show");
}

function confirmLogout() {
  alert("Logged out successfully");
  // optional redirect
  // window.location.href = "login.html";
}

/* CLOSE POPUP ON OUTSIDE CLICK */
window.onclick = function (e) {
  const popup = document.getElementById("logoutPopup");
  if (e.target === popup) popup.classList.remove("show");
};

const phoneInput = document.getElementById("phone");

phoneInput.addEventListener("focus", () => {
  if (!phoneInput.value.startsWith("+91 ")) {
    phoneInput.value = "+91 ";
  }
});

phoneInput.addEventListener("input", () => {
  // Always keep +91
  if (!phoneInput.value.startsWith("+91 ")) {
    phoneInput.value = "+91 ";
  }

  // Allow only digits after +91
  let number = phoneInput.value.replace("+91 ", "").replace(/\D/g, "");

  // First digit must be 5–9
  if (number.length === 1 && !/[5-9]/.test(number)) {
    number = "";
  }

  // Limit to 10 digits
  number = number.slice(0, 10);

  phoneInput.value = "+91 " + number;
});
