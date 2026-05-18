document.addEventListener("DOMContentLoaded", () => {

    /* ===============================
       SIDEBAR NAVIGATION
    =============================== */
    const sidebarItems = document.querySelectorAll(".sidebar li");

    sidebarItems.forEach(item => {
        item.addEventListener("click", () => {
            const text = item.textContent.toLowerCase();

            if (text.includes("service history")) {
                window.location.href = "service-history.html";
            }

		 if (text.includes("track request")) {
                window.location.href = "service-request.html";
            }


            if (text.includes("appliances")) {
                window.location.href = "test.html";
            }

                   });
    });

   
  
    /* ===============================
       SIDEBAR TOGGLE (HAMBURGER MENU)
    =============================== */
    const menuToggle = document.querySelector(".menu-toggle");
    const sidebar = document.querySelector(".sidebar");

    if (menuToggle && sidebar) {
        menuToggle.addEventListener("click", () => {
            if (window.innerWidth <= 900) {
                // Mobile behavior (slide in / out)
                sidebar.classList.toggle("open");
            } else {
                // Desktop behavior (collapse / expand)
                sidebar.classList.toggle("collapsed");
            }
        });
    }

      });


/* ===============================
   LOGOUT MODAL LOGIC
=============================== */
const logoutModal = document.getElementById("logoutModal");
const logoutYes = document.getElementById("logoutYes");
const logoutNo = document.getElementById("logoutNo");

const sidebarItems = document.querySelectorAll(".sidebar li");

sidebarItems.forEach(item => {
    item.addEventListener("click", () => {
        const text = item.textContent.trim().toLowerCase();

        if (text === "logout") {
            logoutModal.style.display = "flex";
        }
    });
});

// YES → Logout
logoutYes.addEventListener("click", () => {
    localStorage.clear();
    sessionStorage.clear();
    window.location.href = "login.html";
});

// NO → Close modal
logoutNo.addEventListener("click", () => {
    logoutModal.style.display = "none";
});