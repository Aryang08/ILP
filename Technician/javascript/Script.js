document.addEventListener("DOMContentLoaded", () => {

  /* ================= MOBILE SIDEBAR ================= */
  const hamburger = document.getElementById("hamburgerBtn");
  const sidebar = document.getElementById("sidebar");
  const overlay = document.getElementById("overlay");

  hamburger?.addEventListener("click", () => {
    sidebar.classList.toggle("active");
    overlay.classList.toggle("active");
  });

  overlay?.addEventListener("click", () => {
    sidebar.classList.remove("active");
    overlay.classList.remove("active");
  });
  document.getElementById("requests")?.addEventListener("click", () => {
  window.location.href = "Request_list.html";
	});
  document.getElementById("myjobs").addEventListener("click", ()=>{window.location.href="TechnicianjobDetail.html"});

  /* ================= SNAPSHOT FILTER ================= */
  const snapshotItems = document.querySelectorAll(".stats div");
  const jobRows = document.querySelectorAll("tr[data-status]");

  snapshotItems.forEach(item => {
    item.addEventListener("click", () => {
      const filter = item.dataset.filter;

      snapshotItems.forEach(i => i.classList.remove("active-filter"));
      item.classList.add("active-filter");

      jobRows.forEach(row => {
        row.style.display =
          filter === "all" || row.dataset.status === filter
            ? "table-row"
            : "none";
      });
    });
  });

  /* ================= JOB ACTIONS ================= */
  document.querySelector(".start")?.addEventListener("click", () =>
    alert("✅ Job Started")
  );

  document.querySelector(".pause")?.addEventListener("click", () =>
    alert("⏸ Job Paused")
  );

  document.querySelector(".complete")?.addEventListener("click", () =>
    alert("✔ Job Completed")
  );

  /* ================= NOTIFICATION ================= */
  document.getElementById("notifIcon")?.addEventListener("click", () =>
    alert("🔔 No new notifications")
  );

});
function openLogoutModal() {
    document.getElementById("logoutModal").classList.add("show");
}

function closeLogoutModal() {
    document.getElementById("logoutModal").classList.remove("show");
}

function confirmLogout() {
    window.location.href = "tech.html";
}