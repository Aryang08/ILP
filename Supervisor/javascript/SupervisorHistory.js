/* ================= GLOBAL ================= */
let originalRows = [];

/* ================= INIT ================= */
document.addEventListener("DOMContentLoaded", () => {
    initializeSidebarNavigation();
    initializeLogout();
    initializeHamburgerMenu();
    initHistoryFilters();
    applyFilters(); // initial load
});

/* ================= ELEMENTS ================= */
const historySearch = document.getElementById("historySearch");
const statusFilter = document.getElementById("statusFilter");
const fromDate = document.getElementById("fromDate");
const toDate = document.getElementById("toDate");
const todayBtn = document.getElementById("todayBtn");
const clearDatesBtn = document.getElementById("clearDatesBtn");


function gotoDashboard(){
window.location.href = "SuperVisorDashboardIndex.html";
}

/* ================= SIDEBAR ================= */
function initializeSidebarNavigation() {
    document.querySelectorAll(".menu li").forEach(item => {
        item.addEventListener("click", () => {
            const text = item.textContent.trim();

            if (text.includes("Dashboard")) location.href = "SuperVisorDashboardIndex.html";
            else if (text.includes("Tickets")) location.href = "SupervisorTicketIndex.html";
            else if (text.includes("Technician Allocation")) location.href = "TechnicianAllocationIndex.html";
            else if (text.includes("History")) location.href = "SupervisorHistoryIndex.html";
            else if (text.includes("Register Technician")) location.href = "registerTechnician.html";

            if (window.innerWidth <= 992) {
                document.querySelector(".sidebar")?.classList.remove("open");
            }
        });
    });
}

/* ================= LOGOUT ================= */
function initializeLogout() {
    document.querySelector(".logout")?.addEventListener("click", () => {
        if (confirm("Are you sure you want to logout?")) {
            location.href = "supervisorLogin.html";
        }
    });
}

/* ================= HAMBURGER ================= */
function initializeHamburgerMenu() {
    const btn = document.getElementById("hamburgerBtn");
    const sidebar = document.querySelector(".sidebar");

    if (!btn || !sidebar) return;

    btn.addEventListener("click", e => {
        e.stopPropagation();
        sidebar.classList.toggle("open");
    });

    document.addEventListener("click", e => {
        if (!sidebar.contains(e.target) && !btn.contains(e.target)) {
            sidebar.classList.remove("open");
        }
    });
}

/* ================= INIT FILTERS ================= */
function initHistoryFilters() {
    const tbody = document.querySelector("tbody");
    if (!tbody) return;

    originalRows = Array.from(tbody.querySelectorAll("tr"));

    historySearch?.addEventListener("input", applyFilters);
    statusFilter?.addEventListener("change", applyFilters);
    fromDate?.addEventListener("change", applyFilters);
    toDate?.addEventListener("change", applyFilters);
    todayBtn?.addEventListener("click", filterToday);
    clearDatesBtn?.addEventListener("click", clearDates);
}

/* ================= APPLY FILTERS ================= */
function applyFilters() {
    const search = historySearch?.value.toLowerCase() || "";
    const status = statusFilter?.value.toLowerCase() || "all";
    const from = fromDate?.value;
    const to = toDate?.value;

    const tbody = document.querySelector("tbody");
    tbody.innerHTML = "";

    const filtered = originalRows.filter(row => {
        const text = row.innerText.toLowerCase();
        const statusText = row.cells[5]?.innerText.toLowerCase() || "";

        // safer date parsing
        const rowDate = new Date(row.cells[0]?.innerText);
	/* const rowDate = new Date(row.cells[0]?.dataset.date); */

        const searchMatch = !search || text.includes(search);
        const statusMatch = status === "all" || statusText.includes(status);
        const fromMatch = !from || rowDate >= new Date(from);
        const toMatch = !to || rowDate <= new Date(to);
/* const toDateObj = to ? new Date(to + "T23:59:59"): null;
const toMatch = !to || rowDate <= toDateObj; */

        return searchMatch && statusMatch && fromMatch && toMatch;
    });

    if (filtered.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align:center;padding:20px;">
                    No results found
                </td>
            </tr>`;
    } else {
        filtered.forEach(row => tbody.appendChild(row));
    }

    updateSummaryCards(filtered);
    updatePaginationText(filtered);
}

/* ================= SUMMARY CARDS ================= */
function updateSummaryCards(rows) {
    const cards = document.querySelectorAll(".summary-card h3");

    if (!cards.length) return;

    const completed = rows.filter(r =>
        r.innerText.toLowerCase().includes("success") ||
        r.innerText.toLowerCase().includes("resolved")
    ).length;

    cards[0].textContent = rows.length;
    cards[1].textContent = rows.filter(r => r.innerText.includes("Assigned")).length;
    cards[2].textContent = rows.filter(r => r.innerText.includes("Updated")).length;
    cards[3].textContent = rows.filter(r => r.innerText.toLowerCase().includes("delay")).length;
    cards[4].textContent = completed;
}

/* ================= PAGINATION ================= */
function updatePaginationText(rows) {
    const span = document.querySelector(".pagination span");
    if (!span) return;

    span.textContent = `Showing 1 to ${rows.length} of ${rows.length} entries`;
}

/* ================= DATE SHORTCUTS ================= */
function filterToday() {
    const today = new Date().toISOString().split("T")[0];

    if (fromDate) fromDate.value = today;
    if (toDate) toDate.value = today;

    applyFilters();
}

function clearDates() {
    if (fromDate) fromDate.value = "";
    if (toDate) toDate.value = "";

    applyFilters();
}