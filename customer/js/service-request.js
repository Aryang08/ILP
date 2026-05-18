document.addEventListener("DOMContentLoaded", function() {

    /* ================= DATA ================= */
    const services = [
        {
            id: "#123456",
            serviceName: "Washing Machine",
            requestedOn: "03 Apr 2024",
            status: "In Progress",
            statusClass: "in-progress",
            time: "5 mins ago"
        },
        {
            id: "#123455",
            serviceName: "Refrigerator",
            requestedOn: "02 Apr 2024",
            status: "Technician Assigned",
            statusClass: "assigned",
            time: "1 hour ago"
        },
        {
            id: "#123454",
            serviceName: "Air Conditioner",
            requestedOn: "30 Mar 2024",
            status: "Completed",
            statusClass: "completed",
            time: "2 days ago"
        }
    ];

    const inputValue = document.getElementById("inputbar");
    const requestlist = document.querySelector(".service-list");

    function renderServices(serviceArray) {
        if (!requestlist) return;
        requestlist.innerHTML = serviceArray.map(service => {
            const params = new URLSearchParams({
                id: service.id,
                name: service.serviceName,
                requestedOn: service.requestedOn,
                status: service.status
            }).toString();

            return `
                <a href="service-request-detail.html?${params}" class="service-item">
                    <div class="service-info">
                        <strong>${service.id}</strong> ${service.serviceName}<br>
                        <small>Requested on: ${service.requestedOn}</small>
                    </div>
                    <div class="service-meta">
                        <span class="status ${service.statusClass}">
                            ${service.status}
                        </span>
                        <span class="time">${service.time}</span>
                    </div>
                </a>
            `;
        }).join("");
    }

    function showNoData() {
        if (!requestlist) return;
        requestlist.innerHTML = `<div class="no-data">No data found</div>`;
    }

    if (inputValue && requestlist) {
        renderServices(services);

        inputValue.addEventListener("input", function () {
            const searchText = inputValue.value.toLowerCase();
            const filtered = services.filter(service =>
                service.serviceName.toLowerCase().includes(searchText)
            );
            filtered.length ? renderServices(filtered) : showNoData();
        });
    }

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