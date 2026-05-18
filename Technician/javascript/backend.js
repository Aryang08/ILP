/* =========================================================
   GLOBAL SCRIPT – SAFE FOR ALL PAGES
========================================================= */
document.addEventListener("DOMContentLoaded", () => {

  /* =========================================================
     SIDEBAR / HAMBURGER MENU
  ========================================================= */
  const hamburger = document.getElementById("hamburgerBtn");
  const sidebar = document.getElementById("sidebar");
  const overlay = document.getElementById("overlay");
  const spareDropdownBtn = document.getElementById("spareDropdownBtn");
  const spareDropdown = document.getElementById("spareDropdown");
  if (hamburger && sidebar && overlay) {
    hamburger.addEventListener("click", () => {
      sidebar.classList.toggle("active");
      overlay.classList.toggle("active");
    });

    overlay.addEventListener("click", () => {
      sidebar.classList.remove("active");
      overlay.classList.remove("active");
    });

spareDropdownBtn?.addEventListener("click", () => {
  spareDropdown.classList.toggle("show");
});

// Close dropdown when clicking outside
document.addEventListener("click", (e) => {
  if (!spareDropdownBtn.contains(e.target) &&
      !spareDropdown.contains(e.target)) {
    spareDropdown.classList.remove("show");
  }
});
  }


  /* =========================================================
     REQUEST LIST PAGE LOGIC
  ========================================================= */
  const requestList = document.querySelector(".request-list");

  document.getElementById("requestListBtn")?.addEventListener("click", () => {
  window.location.href = "Request_list.html";
	});
  document.getElementById("Dashboardlink")?.addEventListener("click",()=>{window.location.href="Technician_Dashboard.html";});

  if (requestList) {
    const statusFilter = document.getElementById("statusFilter");
    const priorityFilter = document.getElementById("priorityFilter");
    const applianceFilter = document.getElementById("applianceFilter");

    const requests = [
      {
        customer: "Rahul Mehta",
        applianceType: "AC",
        appliance: " Split AC (1.5 Ton)",
        issue: "Low Cooling",
        address: "Satellite, Ahmedabad",
        schedule: "Today 3:00 – 5:00 PM",
        priority: "High",
        status: "New"
      },
      {
        customer: "Amit Shah",
        applianceType: "Washing Machine",
        appliance: " Washing Machine",
        issue: "Water Leakage",
        address: "Bodakdev, Ahmedabad",
        schedule: "Tomorrow 10:00 – 12:00 PM",
        priority: "Normal",
        status: "Assigned"
      },
      {
        customer: "Neha Patel",
        applianceType: "Refrigerator",
        appliance: " Refrigerator",
        issue: "Not Cooling",
        address: "Maninagar, Ahmedabad",
        schedule: "Today 6:00 – 8:00 PM",
        priority: "High",
        status: "In Progress"
      },
      {
        customer: "Pooja Rai",
        applianceType: "AC",
        appliance: "Godrej Window AC",
        issue: "No Power",
        address: "Gota, Ahmedabad",
        schedule: "Tomorrow 1:00 – 3:00 PM",
        priority: "Normal",
        status: "Job Completed"
      }
    ];

    function renderRequests(data) {
      requestList.innerHTML = "";

      if (!data.length) {
        requestList.innerHTML =
          `<p class="no-data">No requests found.</p>`;
        return;
      }

      data.forEach(req => {
        const card = document.createElement("div");
        card.className = `request-card ${req.priority === "High" ? "high" : ""}`;

        card.innerHTML = `
          <div class="request-header">
            <span class="customer-name">${req.customer}</span>
            <span class="status-badge ${req.priority === "High" ? "urgent" : ""}">
              ${req.priority}
            </span>
          </div>

          <div class="request-body">
            <p><strong>Appliance:</strong> ${req.appliance}</p>
            <p><strong>Issue:</strong> ${req.issue}</p>
            <p><strong>Location:</strong> ${req.address}</p>
            <p><strong>Schedule:</strong> ${req.schedule}</p>
            <p><strong>Status:</strong> ${req.status}</p>
          </div>

          <div class="request-actions">
            <button class="view-details">View Details</button>
            <button class="call-btn">Notify</button>
            <button class="start-btn">Open Job</button>
          </div>
        `;

        card.querySelector(".view-details").addEventListener("click", () => {
          localStorage.setItem("activeJob", JSON.stringify(req));
          window.location.href = "TechnicianjobDetail.html";
        });

        card.querySelector(".call-btn").addEventListener("click", () => {
          alert(`Notifying ${req.customer}`);
        });

        card.querySelector(".start-btn").addEventListener("click", () => {
          localStorage.setItem("activeJob", JSON.stringify(req));
          window.location.href = "TechnicianjobDetail.html";
        });

        requestList.appendChild(card);
      });
    }

    function applyFilters() {
      let filtered = [...requests];

      if (statusFilter?.value !== "Status: All") {
        filtered = filtered.filter(r => r.status === statusFilter.value);
      }

      if (priorityFilter?.value !== "Priority: All") {
        filtered = filtered.filter(r => r.priority === priorityFilter.value);
      }

      if (applianceFilter?.value !== "Appliance: All") {
        filtered = filtered.filter(r => r.applianceType === applianceFilter.value);
      }

      renderRequests(filtered);
    }

    statusFilter?.addEventListener("change", applyFilters);
    priorityFilter?.addEventListener("change", applyFilters);
    applianceFilter?.addEventListener("change", applyFilters);

    renderRequests(requests);
  }

 /* =========================================================
   TECHNICIAN JOB DETAILS PAGE
========================================================= */
const jobAddress = document.getElementById("jobAddress");
const jobAppliance = document.getElementById("jobAppliance");
const statusBox = document.getElementById("applianceDetails");

const quickActionsBox = document.getElementById("quickActionsBox");
const workNotesBox = document.getElementById("workNotesBox");
const sparePartsBox = document.getElementById("sparePartsBox");

const startBtn = document.getElementById("startJob");
const completeBtn = document.getElementById("markCompleted");
/*const rescheduleBtn= document.getElementById("reschedule");*/
const notes = document.querySelector("textarea");
const submitBtn = document.querySelector(".submit");
const activeJob = localStorage.getItem("activeJob");
const InitiateBtn = document.getElementById("initiate");


/*================== JOb completed==============*/


/* ================= LOAD JOB DATA ================= */
if (activeJob && jobAddress && jobAppliance && statusBox) {
  const job = JSON.parse(activeJob);

  jobAddress.innerHTML = `
    ${job.customer}<br>
    ${job.address}<br>
    Schedule: ${job.schedule}
  `;

  jobAppliance.innerHTML = `
    ${job.appliance}<br>
    Issue: ${job.issue}
  `;

  statusBox.innerHTML = `● ${job.status}`;
workNotesBox && (workNotesBox.style.display = "none");
if(job.status==="Job Completed"){
  workNotesBox && (workNotesBox.style.display = "none");
  sparePartsBox && (sparePartsBox.style.display = "none");
  submitBtn.style.display="none";

}
/* in progress*/

  if (job.status =="In Progress"){

	startBtn.style.display = "none";
        InitiateBtn.style.display = "none";
	
	
     completeBtn?.addEventListener("click", () => {
  completeBtn.innerHTML =
    "● Job Completed • Waiting for customer feedback";
	
	statusBox.innerHTML = "● Job Completed • Waiting for customer feedback";
  const job = JSON.parse(localStorage.getItem("activeJob"));
  job.status = "Completed";
  localStorage.setItem("activeJob", JSON.stringify(job));

  quickActionsBox && (quickActionsBox.style.display = "none");
submitBtn?.addEventListener("click", () => {
  const notes = document.querySelector("textarea");
  workNotesBox && (workNotesBox.style.display = "none");
  sparePartsBox && (sparePartsBox.style.display = "none");
  alert("Job Successfully Completed");
  const checkedParts = [];
  document
    .querySelectorAll('input[type="checkbox"]:checked')
    .forEach(cb =>
      checkedParts.push(cb.parentElement.innerText.trim())
    );
	submitBtn.style.display="none";
});

	
});
	document
        .querySelectorAll("#sparePartsBox input[type='checkbox']")
        .forEach(cb => cb.disabled = true);
	
	spareDropdownBtn.disabled = true;
	spareDropdownBtn.style.opacity = "0.6";

	
}
  if (job.status =="Assigned"){

	startBtn.style.display = "block";
        InitiateBtn.style.display = "none";
	completeBtn.style.display="none";
	startBtn?.addEventListener("click",()=>{
	statusBox.innerHTML="● Job Started • Technician is on-site";
	startBtn.style.display="none";
	completeBtn.style.display="block";

});
	
     completeBtn?.addEventListener("click", () => {
  completeBtn.innerHTML =
    "● Job Completed • Waiting for customer feedback";
	
	statusBox.innerHTML = "● Job Completed • Waiting for customer feedback";
  const job = JSON.parse(localStorage.getItem("activeJob"));
  job.status = "Completed";
  localStorage.setItem("activeJob", JSON.stringify(job));

  quickActionsBox && (quickActionsBox.style.display = "none");
submitBtn?.addEventListener("click", () => {
  const notes = document.querySelector("textarea");
  workNotesBox && (workNotesBox.style.display = "none");
  sparePartsBox && (sparePartsBox.style.display = "none");
  alert("Job Successfully Completed");
  const checkedParts = [];
  document
    .querySelectorAll('input[type="checkbox"]:checked')
    .forEach(cb =>
      checkedParts.push(cb.parentElement.innerText.trim())
    );
	submitBtn.style.display="none";
});

	
});
	document
        .querySelectorAll("#sparePartsBox input[type='checkbox']")
        .forEach(cb => cb.disabled = true);
	
	spareDropdownBtn.disabled = true;
	spareDropdownBtn.style.opacity = "0.6";

	
}
else if (job.status === "New") {
  
  startBtn.style.display = "none";
  completeBtn.style.display = "none";

  InitiateBtn?.addEventListener("click", () => {
    setTimeout(() => {
      // ✅ Update UI
      startBtn.style.display = "block";
      InitiateBtn.style.display = "none";
      statusBox.innerHTML = "● On the Way";

      // ✅ DISABLE SPARE PARTS CHECKLIST
      document
        .querySelectorAll("#sparePartsBox input[type='checkbox']")
        .forEach(cb => cb.disabled = true);
	
	spareDropdownBtn.disabled = true;
	spareDropdownBtn.style.opacity = "0.6";


    }, 200);

  });
 submitBtn?.addEventListener("click", () => {
  const notes = document.querySelector("textarea");
  workNotesBox && (workNotesBox.style.display = "none");
  sparePartsBox && (sparePartsBox.style.display = "none");
  alert("Job Successfully Completed");
  const checkedParts = [];
  document
    .querySelectorAll('input[type="checkbox"]:checked')
    .forEach(cb =>
      checkedParts.push(cb.parentElement.innerText.trim())
    );
	submitBtn.style.display="none";


});

}



  /* ✅ Hide right-column sections if completed */
  else if (job.status === "Completed") {
    quickActionsBox && (quickActionsBox.style.display = "none");
    workNotesBox && (workNotesBox.style.display = "none");
    sparePartsBox && (sparePartsBox.forEach(input => input.disabled = true));
  }
}


/* ================= START JOB ================= */
startBtn?.addEventListener("click", () => {
  statusBox.innerHTML = "● Job Started • Technician is on-site";

  const job = JSON.parse(localStorage.getItem("activeJob"));
  job.status = "In Progress";
  localStorage.setItem("activeJob", JSON.stringify(job));
  workNotesBox && (workNotesBox.style.display = "block");
  startBtn.style.display="none";
  completeBtn.style.display="block";
  rescheduleBtn.style.display="block";
  
});

/* ================= COMPLETE JOB ================= */
completeBtn?.addEventListener("click", () => {
  statusBox.innerHTML =
    "● Job Completed • Waiting for customer feedback";

  const job = JSON.parse(localStorage.getItem("activeJob"));
  job.status = "Completed";
  localStorage.setItem("activeJob", JSON.stringify(job));
  
  /* ✅ Hide all action sections immediately */
  quickActionsBox && (quickActionsBox.style.display = "none");
	
});

/* ================= SUBMIT UPDATE ================= */
if(job.status==="Job Completed"){
  workNotesBox && (workNotesBox.style.display = "none");
  sparePartsBox && (sparePartsBox.style.display = "none");

}
submitBtn?.addEventListener("click", () => {
  const notes = document.querySelector("textarea");
  workNotesBox && (workNotesBox.style.display = "none");
  sparePartsBox && (sparePartsBox.style.display = "none");
  alert("Job Successfully Completed");
  const checkedParts = [];
  document
    .querySelectorAll('input[type="checkbox"]:checked')
    .forEach(cb =>
      checkedParts.push(cb.parentElement.innerText.trim())
    );


});
});

/* ===========================Modal===================*/

function openLogoutModal() {
    document.getElementById("logoutModal").classList.add("show");
}

function closeLogoutModal() {
    document.getElementById("logoutModal").classList.remove("show");
}

function confirmLogout() {
    window.location.href = "tech.html";
}