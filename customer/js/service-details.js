document.addEventListener("DOMContentLoaded", function() {

    /* ================= SIDEBAR TOGGLE ================= */
    const hamburgerBtn = document.getElementById("hamburgerBtn");
    const sidebar = document.getElementById("sidebar");
    const sidebarBackdrop = document.getElementById("sidebarBackdrop");

    if (hamburgerBtn && sidebar && sidebarBackdrop) {
        hamburgerBtn.addEventListener("click", function() {
            sidebar.classList.toggle("open");
            sidebarBackdrop.classList.toggle("active");
        });

        sidebarBackdrop.addEventListener("click", function() {
            sidebar.classList.remove("open");
            sidebarBackdrop.classList.remove("active");
        });
    }

    /* ================= NOTIFICATION ================= */
    const notificationBtn = document.getElementById("notificationBtn");
    const notificationDropdown = document.getElementById("notificationDropdown");

    if (notificationBtn && notificationDropdown) {
        notificationBtn.addEventListener("click", function(e) {
            e.stopPropagation();
            notificationDropdown.classList.toggle("show");
        });

        document.addEventListener("click", function() {
            notificationDropdown.classList.remove("show");
        });
    }

    /* ================= LOAD SERVICE DATA ================= */
    const service = JSON.parse(localStorage.getItem("selectedService"));

    if (service) {
        document.getElementById("name").textContent = service.name;
        document.getElementById("id").textContent = service.id;
        document.getElementById("issue").textContent = service.issue;
        document.getElementById("tech").textContent = service.technician;
        document.getElementById("date").textContent = service.date;
        document.getElementById("amount").textContent = service.amount;

        const status = document.getElementById("status");
        status.textContent = service.status;
        status.classList.add(
            service.status === "Completed" ? "completed" : "in-progress"
        );
    }

});

/* ================= NOTIFICATION FUNCTIONS ================= */
function markAsRead(element) {
    element.classList.remove("unread");
    element.classList.add("read");
}

function markAllRead() {
    const items = document.querySelectorAll(".notification-item.unread");
    items.forEach(item => {
        item.classList.remove("unread");
        item.classList.add("read");
    });
}

/* ================= LOGOUT MODAL ================= */
function openLogoutModal() {
    document.getElementById("logoutModal").classList.add("show");
}

function closeLogoutModal() {
    document.getElementById("logoutModal").classList.remove("show");
}

function confirmLogout() {
    window.location.href = "login.html";
}