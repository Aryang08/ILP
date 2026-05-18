/* DATA */
const services = [
    {id: "SRV001", name: "Television", icon: "📺", status: "Completed", date: "10 Apr 2026", issue: "Screen flickering", technician: "Rohit Mehta", amount: 650},
    {id: "SRV002", name: "Refrigerator", icon: "🧊", status: "Completed", date: "05 Apr 2026", issue: "Cooling issue", technician: "Amit Patel", amount: 450},
    {id: "SRV003", name: "Microwave", icon: "🍲", status: "Completed", date: "28 Mar 2026", issue: "Sensor replaced", technician: "Ankit Shah", amount: 520},
    {id: "SRV004", name: "Air Conditioner", icon: "❄️", status: "Completed", date: "20 Mar 2026", issue: "Gas refill", technician: "Suresh Kumar", amount: 800},
    {id: "SRV005", name: "Washing Machine", icon: "🧺", status: "Completed", date: "15 Mar 2026", issue: "Motor repair", technician: "Ravi Sharma", amount: 550},
    {id: "SRV006", name: "Television", icon: "📺", status: "Completed", date: "05 Mar 2026", issue: "Speaker issue", technician: "John Doe", amount: 300}
];

// Filter only completed services
const completedServices = services.filter(s => s.status === "Completed");

const list = document.getElementById("serviceList");
const search = document.getElementById("searchInput");

function render(data) {
    list.innerHTML = "";
    data.forEach(s => {
        const div = document.createElement("div");
        div.className = "service-item";
        div.innerHTML = `
            <div class="service-left">
                <span class="service-icon">${s.icon}</span>
                <div class="service-details">
                    <span class="service-name">${s.name}</span>
                    <span class="service-id">${s.id}</span>
                </div>
            </div>
            <div class="service-right">
                <span class="status ${s.status === "Completed" ? "completed" : "in-progress"}">${s.status}</span>
                <span class="date">${s.date}</span>
            </div>
        `;
        div.onclick = () => {
            localStorage.setItem("selectedService", JSON.stringify(s));
            location.href = "service-detail.html";
        };
        list.appendChild(div);
    });
}

render(completedServices);

search.oninput = () => {
    const v = search.value.toLowerCase();
    render(completedServices.filter(s => s.name.toLowerCase().includes(v) || s.id.toLowerCase().includes(v)));
};

/* SIDEBAR TOGGLE */
const hamburgerBtn = document.getElementById("hamburgerBtn");
const sidebar = document.getElementById("sidebar");
const sidebarBackdrop = document.getElementById("sidebarBackdrop");

if (hamburgerBtn && sidebar && sidebarBackdrop) {
    hamburgerBtn.addEventListener("click", () => {
        sidebar.classList.toggle("open");
        sidebarBackdrop.classList.toggle("active");
    });

    sidebarBackdrop.addEventListener("click", () => {
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