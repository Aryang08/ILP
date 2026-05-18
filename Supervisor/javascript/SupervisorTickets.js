/* ================= GLOBAL ================= */
let originalRows = [];
let filteredRows = [];
let currentPage = 1;
const rowsPerPage = 4;

/* ================= INIT ================= */
document.addEventListener("DOMContentLoaded", () => {
    initializeSidebarNavigation();
    initializeLogout();
    initializeHamburgerMenu();
    cacheRows();
    ensurePaginationUI();
    initTicketFilters();
    applyFilters(true);
});

/* ================= CACHE ORIGINAL ROWS ================= */
function cacheRows() {
    const tbody = document.querySelector("tbody");
    if (!tbody) return;

    originalRows = Array.from(tbody.querySelectorAll("tr"));
    filteredRows = [...originalRows];
}

/* ================= SIDEBAR NAVIGATION ================= */
function initializeSidebarNavigation() {
    const menuItems = document.querySelectorAll(".menu li");

    menuItems.forEach(item => {
        item.addEventListener("click", () => {
            const text = item.textContent.trim();

            menuItems.forEach(i => i.classList.remove("active"));
            item.classList.add("active");

            if (text.includes("Dashboard")) {
                window.location.href = "SuperVisorDashboardIndex.html";
            } else if (text.includes("Tickets")) {
                window.location.href = "SupervisorTicketIndex.html";
            } else if (text.includes("Technician Allocation")) {
                window.location.href = "TechnicianAllocationIndex.html";
            } else if (text.includes("History")) {
                window.location.href = "SupervisorHistoryIndex.html";
            } else if (text.includes("Register Technician")) {
                window.location.href = "registerTechnician.html";
            }

            if (window.innerWidth <= 992) {
                document.querySelector(".sidebar")?.classList.remove("open");
            }
        });
    });
}

/* ================= LOGOUT ================= */
function initializeLogout() {
    const logout = document.querySelector(".logout");

    if (logout) {
        logout.addEventListener("click", () => {
            if (confirm("Are you sure you want to logout?")) {
                window.location.href = "supervisorLogin.html";
            }
        });
    }
}

function gotoDashboard(){
window.location.href = "SuperVisorDashboardIndex.html";
}
/* ================= HAMBURGER MENU ================= */
function toggleSidebar() {
    const sidebar = document.querySelector(".sidebar");
    if (sidebar) {
        sidebar.classList.toggle("open");
    }
}

function initializeHamburgerMenu() {
    const btn = document.querySelector(".hamburger");
    const sidebar = document.querySelector(".sidebar");

    if (!btn || !sidebar) return;

    // close when clicking outside
    document.addEventListener("click", e => {
        if (
            sidebar.classList.contains("open") &&
            !sidebar.contains(e.target) &&
            !btn.contains(e.target)
        ) {
            sidebar.classList.remove("open");
        }
    });
}

/* ================= FILTER INIT ================= */
function initTicketFilters() {
    const searchInput = document.getElementById("ticketSearch");
    const statusFilter = document.getElementById("statusFilter");

    if (searchInput) {
        searchInput.addEventListener("input", debounce(() => applyFilters(true), 300));
    }

    if (statusFilter) {
        statusFilter.addEventListener("change", () => applyFilters(true));
    }
}

/* ================= DEBOUNCE ================= */
function debounce(func, delay) {
    let timer;
    return function () {
        clearTimeout(timer);
        timer = setTimeout(() => func(), delay);
    };
}

/* ================= APPLY FILTERS ================= */
function applyFilters(resetPage = true) {
    const searchInput = document.getElementById("ticketSearch");
    const statusFilter = document.getElementById("statusFilter");

    const searchValue = searchInput ? searchInput.value.toLowerCase().trim() : "";
    const statusValue = statusFilter ? statusFilter.value.toLowerCase().trim() : "all";

    filteredRows = [...originalRows];

    /* SEARCH */
    if (searchValue !== "") {
        filteredRows = filteredRows.filter(row => {
            const ticketId = row.cells[0]?.textContent.toLowerCase() || "";
            const customer = row.cells[1]?.textContent.toLowerCase() || "";
            const appliance = row.cells[2]?.textContent.toLowerCase() || "";
            const issue = row.cells[3]?.textContent.toLowerCase() || "";

            return (
                ticketId.includes(searchValue) ||
                customer.includes(searchValue) ||
                appliance.includes(searchValue) ||
                issue.includes(searchValue)
            );
        });
    }

    /* STATUS FILTER */
    if (statusValue !== "all") {
        filteredRows = filteredRows.filter(row => {
            const status = row.cells[5]?.textContent.toLowerCase().trim() || "";

            if (statusValue === "open") {
                return status === "unassigned" || status === "open";
            }

            if (statusValue === "assigned") {
                return status === "assigned" || status === "in progress";
            }

            if (statusValue === "delayed") {
                return status === "delayed";
            }

            if (statusValue === "completed") {
                return status === "completed";
            }

            return true;
        });
    }

    if (resetPage) {
        currentPage = 1;
    }

    const totalPages = Math.max(1, Math.ceil(filteredRows.length / rowsPerPage));
    if (currentPage > totalPages) {
        currentPage = totalPages;
    }

    renderTable();
    renderPagination();
    updateTableInfo();
    updateSummaryCards();
}

/* ================= RENDER TABLE ================= */
function renderTable() {
    const tbody = document.querySelector("tbody");
    if (!tbody) return;

    tbody.innerHTML = "";

    if (filteredRows.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; padding:20px;">
                    No results found
                </td>
            </tr>
        `;
        return;
    }

    const startIndex = (currentPage - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    const rowsToShow = filteredRows.slice(startIndex, endIndex);

    rowsToShow.forEach(row => tbody.appendChild(row));
}

/* ================= AUTO PAGINATION UI ================= */
function ensurePaginationUI() {
    const tableBox = document.querySelector(".table-box");
    if (!tableBox) return;

    let pagination = tableBox.querySelector(".pagination");

    if (!pagination) {
        pagination = document.createElement("div");
        pagination.className = "pagination";
        pagination.innerHTML = `
            <span id="ticketTableInfo">Showing 0 to 0 of 0 entries</span>
            <div class="pages" id="ticketPagination"></div>
        `;
        tableBox.appendChild(pagination);
    }
}

/* ================= TABLE INFO ================= */
function updateTableInfo() {
    const info = document.getElementById("ticketTableInfo");
    if (!info) return;

    if (filteredRows.length === 0) {
        info.textContent = "Showing 0 to 0 of 0 entries";
        return;
    }

    const start = (currentPage - 1) * rowsPerPage + 1;
    const end = Math.min(currentPage * rowsPerPage, filteredRows.length);

    info.textContent = `Showing ${start} to ${end} of ${filteredRows.length} entries`;
}

/* ================= PAGINATION ================= */
function renderPagination() {
    const container = document.getElementById("ticketPagination");
    if (!container) return;

    container.innerHTML = "";

    const totalPages = Math.ceil(filteredRows.length / rowsPerPage);

    if (totalPages <= 1) return;

    const prevBtn = document.createElement("button");
    prevBtn.type = "button";
    prevBtn.textContent = "Prev";
    prevBtn.disabled = currentPage === 1;
    prevBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            renderTable();
            renderPagination();
            updateTableInfo();
        }
    });
    container.appendChild(prevBtn);

    for (let i = 1; i <= totalPages; i++) {
        const pageBtn = document.createElement("button");
        pageBtn.type = "button";
        pageBtn.textContent = i;

        if (i === currentPage) {
            pageBtn.classList.add("active-page");
        }

        pageBtn.addEventListener("click", () => {
            currentPage = i;
            renderTable();
            renderPagination();
            updateTableInfo();
        });

        container.appendChild(pageBtn);
    }

    const nextBtn = document.createElement("button");
    nextBtn.type = "button";
    nextBtn.textContent = "Next";
    nextBtn.disabled = currentPage === totalPages;
    nextBtn.addEventListener("click", () => {
        if (currentPage < totalPages) {
            currentPage++;
            renderTable();
            renderPagination();
            updateTableInfo();
        }
    });
    container.appendChild(nextBtn);
}

/* ================= SUMMARY CARDS ================= */
function updateSummaryCards() {
    const cardNumbers = document.querySelectorAll(".summary-card h3");
    if (cardNumbers.length < 3) return;

    // Card 1: New Tickets = all currently filtered rows
    const newTickets = filteredRows.length;

    // Card 2: Pending Assignment = unassigned/open OR no technician assigned
    const pendingAssignment = filteredRows.filter(row => {
        const status = row.cells[5]?.textContent.toLowerCase().trim() || "";
        const technician = row.cells[6]?.textContent.toLowerCase().trim() || "";

        return (
            status === "unassigned" ||
            status === "open" ||
            technician === "-" ||
            technician === ""
        );
    }).length;

    // Card 3: In Progress = assigned/in progress
    const inProgress = filteredRows.filter(row => {
        const status = row.cells[5]?.textContent.toLowerCase().trim() || "";
        return status === "in progress" || status === "assigned";
    }).length;

    cardNumbers[0].textContent = newTickets;
    cardNumbers[1].textContent = pendingAssignment;
    cardNumbers[2].textContent = inProgress;
}
``