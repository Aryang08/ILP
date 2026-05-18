document.addEventListener("DOMContentLoaded", () => {

  /* ================= URL PARAMS ================= */
  const params = new URLSearchParams(window.location.search);
  const serviceId = params.get("id") || "#123456";
  const serviceName = params.get("name") || "Washing Machine";
  const requestedOn = params.get("requestedOn");
  const status = params.get("status");

  // Update Header
  const requestCard = document.querySelector(".request-card");
  if (requestCard) {
    requestCard.querySelector("strong").textContent = serviceId;
    requestCard.querySelector("p").innerHTML = `<strong>${serviceId}</strong> ${serviceName}`;
  }

  /* ================= BILLING POPUP ================= */
  const billingOverlay = document.getElementById("billingOverlay");

  if (billingOverlay) {
    document.getElementById("billServiceId").textContent = serviceId;
    document.getElementById("billServiceName").textContent = serviceName;

    // Only show billing when service is completed
    if (status && status.toLowerCase() === "completed") {
      setTimeout(() => {
        billingOverlay.classList.remove("hidden");
      }, 800);
    }
  }

  /* ================= SIDEBAR TOGGLE ================= */
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

/* ================= REVIEW FUNCTIONS ================= */
let selectedStars = 0;

function selectStar(rating) {
  selectedStars = rating;
  const stars = document.getElementById("starRating").children;
  for (let i = 0; i < stars.length; i++) {
    if (i < rating) {
      stars[i].style.color = "#ffc107";
    } else {
      stars[i].style.color = "#ccc";
    }
  }
}

function submitReview() {
  const reviewText = document.getElementById("reviewText").value.trim();
  const errorMsg = document.getElementById("reviewError");

  if (selectedStars === 0 || reviewText === "") {
    errorMsg.style.display = "block";
    return;
  }

  errorMsg.style.display = "none";
  
  document.getElementById("reviewForm").style.display = "none";
  document.getElementById("submittedReview").style.display = "block";

  let starsDisplay = "";
  for (let i = 0; i < 5; i++) {
    starsDisplay += i < selectedStars ? "★" : "☆";
  }
  
  document.getElementById("submittedStars").innerHTML = starsDisplay;
  document.getElementById("submittedStars").style.color = "#fbc02d";
  document.getElementById("submittedText").textContent = reviewText;
}