const technicians = [
    { name: "Rajesh Kumar", skills: ["AC", "Refrigerator"], available: true },
    { name: "Sunita Raj", skills: ["Washing", "Dryer"], available: true },
    { name: "Ankit Verma", skills: ["Microwave"], available: false }
];

let selectedRow = null;
let originalRows = [];
let filteredRows = [];
let currentPage = 1;
const rowsPerPage = 4;

document.addEventListener("DOMContentLoaded", () => {
    createModal();
    cacheRows();
    ensurePaginationUI();
    initButtons();
    initFilters();
    initializeSidebarNavigation();
    initializeLogout();
    initializeHamburgerMenu();
    applyFilters(true);
});

/* ================= CACHE ORIGINAL ROWS ================= */
function cacheRows() {
    const tbody = document.querySelector("tbody");
    if (!tbody) return;

    originalRows = Array.from(tbody.querySelectorAll("tr"));
    filteredRows = [...originalRows];
}
function gotoDashboard(){
window.location.href = "SuperVisorDashboardIndex.html";
}

/* ================= BUTTON INIT ================= */
function initButtons() {
    document.querySelectorAll(".assign-btn").forEach(btn => {
        if (btn.dataset.bound === "true") return;

        btn.dataset.bound = "true";

        btn.addEventListener("click", e => {
            if (btn.disabled) return;

            selectedRow = e.target.closest("tr");
            openModal();
        });
    });
}

/* ================= CREATE MODAL ================= */
function createModal() {
    if (document.getElementById("techModal")) return;

    const html = `
        <div id="techModal" class="modal-overlay">
            <div class="assign-modal">
                <h3>Select Technician</h3>

                <input
                    type="text"
                    id="techSearch"
                    class="tech-search"
                    placeholder="Search name or skill"
                >

                <div id="techList"></div>

                <div style="margin-top:15px;text-align:right;">
                    <button class="modal-close-btn" type="button" onclick="closeModal()">Close</button>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML("beforeend", html);

    const techSearch = document.getElementById("techSearch");
    if (techSearch) {
        techSearch.addEventListener("input", filterTechList);
    }
}

/* ================= OPEN MODAL ================= */
function openModal() {
    const modal = document.getElementById("techModal");
    const techList = document.getElementById("techList");
    const techSearch = document.getElementById("techSearch");

    if (!modal || !techList || !selectedRow) return;

    if (techSearch) techSearch.value = "";

    const appliance = selectedRow.cells[1]?.textContent.toLowerCase() || "";

    const matchingTechs = technicians.filter(tech =>
        tech.skills.some(skill => appliance.includes(skill.toLowerCase()))
    );

    techList.innerHTML = "";

    if (!matchingTechs.length) {
        techList.innerHTML = `<p style="padding:14px;">No technician available for this appliance.</p>`;
    } else {
        matchingTechs.forEach(tech => {
            techList.innerHTML += `
                <div
                    class="tech-item"
                    data-name="${tech.name.toLowerCase()}"
                    data-skill="${tech.skills.join(", ").toLowerCase()}"
                >
                    <div>
                        <strong>${tech.name}</strong><br>
                        <small>${tech.skills.join(", ")}</small>
                    </div>

                    <button
                        type="button"
                        onclick="selectTech('${tech.name}')"
                        ${!tech.available ? "disabled" : ""}
                    >
                        ${tech.available ? "Select" : "Busy"}
                    </button>
                </div>
            `;
        });
    }

    modal.classList.add("active");
}

/* ================= FILTER INSIDE MODAL ================= */
function filterTechList() {
    const input = document.getElementById("techSearch");
    if (!input) return;

    const value = input.value.toLowerCase().trim();

    document.querySelectorAll(".tech-item").forEach(item => {
        const name = item.dataset.name || "";
        const skill = item.dataset.skill || "";

        item.style.display =
            name.includes(value) || skill.includes(value)
                ? "flex"
                : "none";
    });
}

/* ================= SELECT TECH ================= */
function selectTech(name) {
    if (!selectedRow) return;

    const assignedCell = selectedRow.querySelector(".assigned-tech");
    const btn = selectedRow.querySelector(".assign-btn");

    if (assignedCell) {
        assignedCell.textContent = name;
    }

    if (btn) {
        btn.textContent = "Assigned";
        btn.disabled = true;
        btn.style.background = "green";
    }

    closeModal();

    // Refresh table/cards/info after assignment
    applyFilters(false);
}

/* ================= CLOSE MODAL ================= */
function closeModal() {
    const modal = document.getElementById("techModal");
    if (modal) {
        modal.classList.remove("active");
    }
}

/* ================= FILTERS ================= */
function initFilters() {
    const searchInput = document.getElementById("ticketSearch");
    const statusFilter = document.getElementById("statusFilter");

    if (searchInput && searchInput.dataset.bound !== "true") {
        searchInput.dataset.bound = "true";
        searchInput.addEventListener("input", () => applyFilters(true));
    }

    if (statusFilter && statusFilter.dataset.bound !== "true") {
        statusFilter.dataset.bound = "true";
        statusFilter.addEventListener("change", () => applyFilters(true));
    }
}

function applyFilters(resetPage = true) {
    const searchInput = document.getElementById("ticketSearch");
    const statusFilter = document.getElementById("statusFilter");

    const search = searchInput ? searchInput.value.toLowerCase().trim() : "";
    const status = statusFilter ? statusFilter.value : "all";

    filteredRows = originalRows.filter(row => {
        const id = row.cells[0]?.textContent.toLowerCase() || "";
        const appliance = row.cells[1]?.textContent.toLowerCase() || "";
        const assigned = row.cells[4]?.textContent.toLowerCase().trim() || "";

        const searchMatch = id.includes(search) || appliance.includes(search);

        const statusMatch =
            status === "all" ||
            (status === "assigned" && assigned !== "unassigned") ||
            (status === "unassigned" && assigned === "unassigned");

        return searchMatch && statusMatch;
    });

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

    if (!filteredRows.length) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align:center;padding:20px;">
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

    // Rebind modal buttons after DOM render
    initButtons();
}

/* ================= PAGINATION UI (AUTO-INJECT) ================= */
function ensurePaginationUI() {
    const tableBox = document.querySelector(".table-box");
    if (!tableBox) return;

    let pagination = tableBox.querySelector(".pagination");
    if (!pagination) {
        pagination = document.createElement("div");
        pagination.className = "pagination";
        pagination.innerHTML = `
            <span id="allocationTableInfo">Showing 0 to 0 of 0 entries</span>
            <div class="pages" id="allocationPagination"></div>
        `;
        tableBox.appendChild(pagination);
    }
}

/* ================= TABLE INFO ================= */
function updateTableInfo() {
    const info = document.getElementById("allocationTableInfo");
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
    const container = document.getElementById("allocationPagination");
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

    const unassignedCount = filteredRows.filter(row => {
        const assignedText = row.cells[4]?.textContent.toLowerCase().trim() || "";
        return assignedText === "unassigned";
    }).length;

    const assignedCount = filteredRows.filter(row => {
        const assignedText = row.cells[4]?.textContent.toLowerCase().trim() || "";
        return assignedText !== "unassigned";
    }).length;

    const delayedCount = filteredRows.filter(row => isDelayedRow(row)).length;

    cardNumbers[0].textContent = unassignedCount;
    cardNumbers[1].textContent = assignedCount;
    cardNumbers[2].textContent = delayedCount;
}

/* ================= DELAYED LOGIC ================= */
function isDelayedRow(row) {
    const preferredDateText = row.cells[3]?.textContent.trim() || "";
    const assignedText = row.cells[4]?.textContent.toLowerCase().trim() || "";

    if (!preferredDateText || assignedText !== "unassigned") return false;

    const currentYear = new Date().getFullYear();
    const parsedDate = new Date(`${preferredDateText}, ${currentYear}`);

    if (isNaN(parsedDate.getTime())) return false;

    return parsedDate < new Date();
}

/* ================= SIDEBAR ================= */
function initializeSidebarNavigation() {
    document.querySelectorAll(".menu li").forEach(item => {
        item.addEventListener("click", () => {
            const text = item.textContent.trim();

            if (text.includes("Dashboard")) {
                location.href = "SuperVisorDashboardIndex.html";
            } else if (text.includes("Tickets")) {
                location.href = "SupervisorTicketIndex.html";
            } else if (text.includes("Technician Allocation")) {
                location.href = "TechnicianAllocationIndex.html";
            } else if (text.includes("History")) {
                location.href = "SupervisorHistoryIndex.html";
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
    const logoutBtn = document.querySelector(".logout");
    if (!logoutBtn) return;

    logoutBtn.addEventListener("click", () => {
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
        if (
            sidebar.classList.contains("open") &&
            !sidebar.contains(e.target) &&
            !btn.contains(e.target)
        ) {
            sidebar.classList.remove("open");
        }
    });
}
``