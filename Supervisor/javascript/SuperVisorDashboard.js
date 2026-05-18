document.addEventListener("DOMContentLoaded", () => {
    refreshDashboardKPIs();
    initializeLogout();
    initializeSidebarNavigation();
    initializeHamburgerMenu();
});

/* ================= KPI REFRESH ================= */
function refreshDashboardKPIs() {
    const cards = document.querySelectorAll(".kpi-card h3");

    const mockStats = [64, 12, 36, 8];

    cards.forEach((card, index) => {
        if (mockStats[index] !== undefined) {
            card.textContent = mockStats[index];
        }
    });
}
function gotoDashboard(){
window.location.href = "SuperVisorDashboardIndex.html";
}

/* ================= LOGOUT ================= */
function initializeLogout() {
    const logout = document.querySelector(".logout");

    if (!logout) return;

    logout.addEventListener("click", () => {
        if (confirm("Are you sure you want to logout?")) {
            window.location.href = "supervisorLogin.html";
        }
    });
}

/* ================= SIDEBAR NAVIGATION ================= */
function initializeSidebarNavigation() {
    const menuItems = document.querySelectorAll(".menu li");
    const sidebar = document.querySelector(".sidebar");

    menuItems.forEach(item => {
        item.addEventListener("click", () => {
            const text = item.textContent.trim();

            if (text.includes("Dashboard")) {
                window.location.href = "SuperVisorDashboardIndex.html";
            }
            else if (text.includes("Tickets")) {
                window.location.href = "SupervisorTicketIndex.html";
            }
            else if (text.includes("Technician Allocation")) {
                window.location.href = "TechnicianAllocationIndex.html";
            }
            else if (text.includes("History")) {
                window.location.href = "SupervisorHistoryIndex.html";
            }
	    else if (text.includes("Register Technician")) {
                window.location.href = "registerTechnician.html";
            }

            if (window.innerWidth <= 992 && sidebar) {
                sidebar.classList.remove("open");
            }
        });
    });
}

/* ================= HAMBURGER ================= */
function initializeHamburgerMenu() {
    const btn = document.getElementById("hamburgerBtn");
    const sidebar = document.querySelector(".sidebar");

    if (!btn || !sidebar) {
        console.error("Hamburger button or sidebar not found");
        return;
    }

    btn.addEventListener("click", function (e) {
        e.stopPropagation();
        sidebar.classList.toggle("open");
    });

    document.addEventListener("click", function (e) {
        if (
            sidebar.classList.contains("open") &&
            !sidebar.contains(e.target) &&
            !btn.contains(e.target)
        ) {
            sidebar.classList.remove("open");
        }
    });
}