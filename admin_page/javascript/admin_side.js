const requests = [
    {id:"REQ001", customer:"Rahul Sharma", issue:"AC Not Cooling", status:"Open", tech:"-", date:"20 May"},
    {id:"REQ002", customer:"Amit Verma", issue:"Fridge Issue", status:"Assigned", tech:"Rohit", date:"20 May"},
    {id:"REQ003", customer:"Neha Singh", issue:"Washing Machine", status:"Delayed", tech:"-", date:"19 May"},
    {id:"REQ004", customer:"Sanjay Mehta", issue:"AC Leak", status:"Open", tech:"-", date:"18 May"}
];

function toggleSidebar() {
    document.querySelector(".sidebar").classList.toggle("open");
}

/* Auto-close sidebar on mobile after click */
document.querySelectorAll(".menu-item").forEach(item => {
    item.addEventListener("click", () => {
        if (window.innerWidth <= 900) {
            document.querySelector(".sidebar").classList.remove("open");
        }
    });
});

function renderTable(data) {
    const tbody = document.getElementById("tableBody");
    tbody.innerHTML = "";
    data.forEach(r => {
        tbody.innerHTML += `
        <tr>
            <td>${r.id}</td>
            <td>${r.customer}</td>
            <td>${r.issue}</td>
            <td>${r.status}</td>
            <td>${r.tech}</td>
            <td>${r.date}</td>
        </tr>`;
    });
}

function updateCounts(data) {
    document.getElementById("openCount").innerText =
        data.filter(r => r.status === "Open").length;
    document.getElementById("assignedCount").innerText =
        data.filter(r => r.status === "Assigned").length;
    document.getElementById("delayedCount").innerText =
        data.filter(r => r.status === "Delayed").length;
}

renderTable(requests);
updateCounts(requests);

const searchBox = document.getElementById("searchBox");
const statusFilter = document.getElementById("statusFilter");

function filterData() {
    const searchValue = searchBox.value.toLowerCase();
    const statusValue = statusFilter.value;

    const filtered = requests.filter(r => {
        const matchesSearch =
            r.id.toLowerCase().includes(searchValue) ||
            r.customer.toLowerCase().includes(searchValue) ||
            r.issue.toLowerCase().includes(searchValue);

        const matchesStatus =
            statusValue === "All Status" || r.status === statusValue;

        return matchesSearch && matchesStatus;
    });

    renderTable(filtered);
    updateCounts(filtered);
}

/* Event Listeners */
searchBox.addEventListener("keyup", filterData);
statusFilter.addEventListener("change", filterData);

function goNextPage() {
    window.location.href = "feedback.html";

}


function openLogoutPopup() {
    document.getElementById("logoutPopup").classList.add("show");
}

function closeLogoutPopup() {
    document.getElementById("logoutPopup").classList.remove("show");
}

function confirmLogout() {
    alert("Logged out successfully");
    window.location.href = "adminLogin.html";
}

/* CLICK OUTSIDE CLOSE */
window.onclick = function(e) {
    const popup = document.getElementById("logoutPopup");
    if (e.target === popup) {
        popup.classList.remove("show");
    }
};
