let checkboxes = [];
let selectedContainer = null;

document.addEventListener("DOMContentLoaded", () => {
    checkboxes = document.querySelectorAll("#skillDropdown input");
    selectedContainer = document.getElementById("selectedSkills");

    loadData();
    initDropdown();
    initSidebar();
    initContactField();
    

    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const contactInput = document.getElementById("contact");

    if (emailInput) {
        emailInput.addEventListener("input", () => {
            hideError("emailError", "email");
        });
    }

    if (passwordInput) {
        passwordInput.addEventListener("input", () => {
            hideError("passwordError", "password");
        });
    }

    if (contactInput) {
        contactInput.addEventListener("input", () => {
            hideError("contactError", "contact");
        });
    }
});

/* ================= MODAL ================= */
function openModal() {
    const modal = document.getElementById("modal");
    if (modal) {
        modal.classList.add("active");
	document.body.classList.add("modal-open");
    }
}

function closeModal() {
    const modal = document.getElementById("modal");
    if (modal) {
        modal.classList.remove("active");
	document.body.classList.remove("modal-open");
    }
}

function openAddModal() {
    const modal = document.getElementById("modal");
    if (!modal) return;

    delete modal.dataset.editId;

    const name = document.getElementById("name");
    const email = document.getElementById("email");
    const contact = document.getElementById("contact");
    const password = document.getElementById("password");

    if (name) name.value = "";
    if (email) email.value = "";
    if (contact) contact.value = "+91 ";
    if (password) password.value = "";

    checkboxes.forEach(cb => {
        cb.checked = false;
    });

    updateSelectedSkills();

    hideError("emailError", "email");
    hideError("contactError", "contact");
    hideError("passwordError", "password");

    openModal();
}

/* ================= DROPDOWN ================= */
function initDropdown() {
    const box = document.getElementById("selectBox");
    const dropdown = document.getElementById("skillDropdown");

    if (!box || !dropdown) return;

    box.addEventListener("click", (e) => {
        e.stopPropagation();
        dropdown.classList.toggle("show");
    });

    dropdown.addEventListener("click", (e) => {
        e.stopPropagation();
    });

    checkboxes.forEach(cb => {
        cb.addEventListener("change", updateSelectedSkills);
    });

    document.addEventListener("click", (e) => {
        if (!dropdown.contains(e.target) && !box.contains(e.target)) {
            dropdown.classList.remove("show");
        }
    });
}

function updateSelectedSkills() {
    if (!selectedContainer) return;

    selectedContainer.innerHTML = "";

    const selected = Array.from(checkboxes).filter(cb => cb.checked);

    if (selected.length === 0) {
        selectedContainer.innerHTML = `<span class="placeholder">Select Specialization</span>`;
        return;
    }

    selected.forEach(cb => {
        const chip = document.createElement("div");
        chip.className = "skill-chip";

        chip.innerHTML = `
            ${cb.value}
            <button type="button" onclick="removeSkill('${cb.value}', event)">×</button>
        `;

        selectedContainer.appendChild(chip);
    });
}

function removeSkill(value, e) {
    e.stopPropagation();

    const checkbox = Array.from(checkboxes).find(cb => cb.value === value);
    if (checkbox) {
        checkbox.checked = false;
    }

    updateSelectedSkills();
}
function gotoDashboard(){
window.location.href = "SuperVisorDashboardIndex.html";
}

/* ================= ADD / EDIT TECH ================= */
function generateTechld(){
let counter = localStorage.getItem("techCounter");

if(!counter){
  counter = 24701;
  }else{
    counter = parseInt(counter) + 1;
  }
localStorage.setItem("techCounter", counter);

return "T-" + counter;
}
function addTech() {
    const name = document.getElementById("name")?.value.trim() || "";
    const email = document.getElementById("email")?.value.trim() || "";
    const contactValue = document.getElementById("contact")?.value.trim() || "";
    const password = document.getElementById("password")?.value.trim() || "";
    const modal = document.getElementById("modal");
    const editId = modal ? modal.dataset.editId : "";

    const skills = Array.from(checkboxes)
        .filter(cb => cb.checked)
        .map(cb => cb.value);

    const contactDigits = getContactDigits(contactValue);

    hideError("emailError", "email");
    hideError("contactError", "contact");
    hideError("passwordError", "password");

    // required fields
    if (!name || !email || !contactDigits || skills.length === 0 || (!editId && !password)) {
        alert("Fill all fields");
        return;
    }

    // email validation
    if (!isValidEmail(email)) {
        showError("emailError", "email");
        return;
    }

    // contact validation (10 digits after +91)
    if (!isValidIndianMobile(contactValue)) {
        showError("contactError", "contact");
        return;
    }

    // password validation
    // Add mode: password required and must be valid
    // Edit mode: password optional, but if typed then must be valid
    if ((!editId && !isValidPassword(password)) || (editId && password && !isValidPassword(password))) {
        showError("passwordError", "password");
        return;
    }

    let data = JSON.parse(localStorage.getItem("techs")) || [];

    if (editId) {
        const index = data.findIndex(t => t.id === editId);
        if (index !== -1) {
            data[index] = {
                ...data[index],
                name,
                email,
                contact: `+91 ${contactDigits}`,
                skills
            };
        }
        delete modal.dataset.editId;
    } else {
        data.push({
            id: generateTechld(),
            name,
            email,
            contact: `+91 ${contactDigits}`,
            skills,
            date: new Date().toLocaleDateString()
        });
    }

    localStorage.setItem("techs", JSON.stringify(data));

    const tbody = document.querySelector("#techTable tbody");
    if (tbody) {
        tbody.innerHTML = "";
    }

    loadData();
    closeModal();
}

/* ================= EDIT ================= */
function editTech(id) {
    const data = JSON.parse(localStorage.getItem("techs")) || [];
    const tech = data.find(t => t.id === id);

    if (!tech) return;

    const modal = document.getElementById("modal");
    if (!modal) return;

    const name = document.getElementById("name");
    const email = document.getElementById("email");
    const contact = document.getElementById("contact");
    const password = document.getElementById("password");

    if (name) name.value = tech.name || "";
    if (email) email.value = tech.email || "";

    const existingDigits = String(tech.contact || "").replace(/\D/g, "").slice(-10);
    if (contact) contact.value = "+91 " + existingDigits;

    if (password) password.value = "";

    checkboxes.forEach(cb => {
        cb.checked = tech.skills.includes(cb.value);
    });

    updateSelectedSkills();

    hideError("emailError", "email");
    hideError("contactError", "contact");
    hideError("passwordError", "password");

    modal.dataset.editId = id;
    openModal();
}

/* ================= TABLE ================= */
function appendRow(t) {
    const tbody = document.querySelector("#techTable tbody");
    if (!tbody) return;

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${t.id}</td>
        <td>${t.name}</td>
        <td>${t.email}</td>
        <td>${t.skills.join(", ")}</td>
        <td>${t.contact}</td>
        <td>${t.date}</td>
        <td><button type="button" onclick="editTech('${t.id}')">Edit</button></td>
    `;

    tbody.appendChild(row);
}

/* ================= LOAD ================= */
function loadData() {
    const data = JSON.parse(localStorage.getItem("techs")) || [];
    data.forEach(appendRow);
}

/* ================= UTIL ================= */
function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function showError(id, inputId) {
    const error = document.getElementById(id);
    const input = document.getElementById(inputId);

    if (error) error.style.display = "block";
    if (input) input.classList.add("error");
}

function hideError(id, inputId) {
    const error = document.getElementById(id);
    const input = document.getElementById(inputId);

    if (error) error.style.display = "none";
    if (input) input.classList.remove("error");
}

function getContactDigits(value) {
    let digits = value.replace(/\D/g, "");

    if (digits.startsWith("91")) {
        digits = digits.slice(2);
    }

    return digits.slice(0, 10);
}

function isValidIndianMobile(value) {
    return /^\d{10}$/.test(getContactDigits(value));
}

function isValidPassword(password) {
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/.test(password);
}

function initContactField() {
    const contactInput = document.getElementById("contact");
    if (!contactInput) return;

    if (!contactInput.value || !contactInput.value.startsWith("+91 ")) {
        contactInput.value = "+91 ";
    }

    contactInput.addEventListener("focus", () => {
        if (!contactInput.value.startsWith("+91 ")) {
            contactInput.value = "+91 ";
        }
    });

    contactInput.addEventListener("input", () => {
        let digits = contactInput.value.replace(/\D/g, "");

        if (digits.startsWith("91")) {
            digits = digits.slice(2);
        }

        digits = digits.slice(0, 10);
        contactInput.value = "+91 " + digits;

        hideError("contactError", "contact");
    });
}

/* ================= SIDEBAR ================= */
function initSidebar() {
    const hamburgerBtn = document.getElementById("hamburgerBtn");
    const sidebar = document.querySelector(".sidebar");

    if (!hamburgerBtn || !sidebar) return;

    hamburgerBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        sidebar.classList.toggle("open");
    });

    document.addEventListener("click", (e) => {
        if (!sidebar.contains(e.target) && !hamburgerBtn.contains(e.target)) {
            sidebar.classList.remove("open");
        }
    });

    document.querySelectorAll(".menu li").forEach(item => {
        item.addEventListener("click", () => {
            const text = item.textContent.trim();

            if (text.includes("Dashboard")) window.location.href = "SuperVisorDashboardIndex.html";
            if (text.includes("Tickets")) window.location.href = "SupervisorTicketIndex.html";
            if (text.includes("Technician Allocation")) window.location.href = "TechnicianAllocationIndex.html";
            if (text.includes("History")) window.location.href = "SupervisorHistoryIndex.html";
            if (text.includes("Register Technician")) window.location.href = "registerTechnician.html";
        });
    });

    const logoutBtn = document.querySelector(".logout");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            if (confirm("Logout?")) {
                window.location.href = "supervisorLogin.html";
            }
        });
    }
}
