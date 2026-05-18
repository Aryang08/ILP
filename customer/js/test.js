document.addEventListener("DOMContentLoaded", () => {
    /* ===============================
       ELEMENT REFERENCES
    =============================== */

    const applianceDropdown = document.getElementById("applianceDropdown");
    const removeBtn = document.getElementById("removeApplianceBtn");

    const applianceIcon = document.getElementById("applianceIcon");
    const applianceName = document.getElementById("applianceName");
    const applianceTitle = document.getElementById("applianceTitle");

    const warranty = document.getElementById("warranty");
    const installDate = document.getElementById("installDate");
    const purchaseDate = document.getElementById("purchaseDate");
    const modelNumber = document.getElementById("modelNumber");
    const serialNumber = document.getElementById("serialNumber");

    const requestBtn = document.getElementById("requestServiceBtn");

    const optionButtons = document.querySelectorAll(".option-btn");
    const nextBtn = document.querySelector(".next-btn");
    const prevBtn = document.querySelector(".prev-btn");
    const validationMsg = document.querySelector(".validation-message");
    const questionText = document.getElementById("questionText");

    const summaryPanel = document.getElementById("summaryPanel");
    const issueCapturePanel = document.getElementById("issueCapturePanel");
    const questionBox = document.getElementById("questionBox");
    const complaintBox = document.getElementById("complaintBox");
    const scheduleBox = document.getElementById("scheduleBox");
    const confirmationBox = document.getElementById("confirmationBox");

    const customComplaintTextarea = document.getElementById("customComplaint");
    const charCount = document.getElementById("charCount");
    const backToQuestionsFromComplaintBtn = document.getElementById("backToQuestionsFromComplaint");
    const proceedToScheduleBtn = document.getElementById("proceedToSchedule");

    const calendarDates = document.getElementById("calendarDates");
    const calendarTitle = document.getElementById("calendarTitle");

    const prevMonthBtn = document.getElementById("prevMonth");
    const nextMonthBtn = document.getElementById("nextMonth");

    const selectedText = document.getElementById("selectedText");

    const backToQuestionsBtn = document.getElementById("backToQuestions");
    const confirmScheduleBtn = document.getElementById("confirmSchedule");
    const scheduleValidation = document.getElementById("scheduleValidation");

    const confirmAppliance = document.getElementById("confirmAppliance");
    const confirmDate = document.getElementById("confirmDate");
    const confirmComplaintRow = document.getElementById("confirmComplaintRow");
    const confirmComplaint = document.getElementById("confirmComplaint");

    const newServiceBtn = document.getElementById("newServiceBtn");

    // Sidebar / navigation buttons
    const dashboardBtn = document.getElementById("dashboardBtn");
    const trackBtn = document.getElementById("trackBtn");
    const historyBtn = document.getElementById("historyBtn");
    const menuToggle = document.getElementById("menuToggle");
    const sidebar = document.getElementById("sidebar");
    const backdrop = document.getElementById("sidebarBackdrop");

    // Logout
    const logoutBtn = document.querySelector(".logout");

    /* ===============================
       NAVIGATION EVENTS
    =============================== */

    if (dashboardBtn) {
        dashboardBtn.addEventListener("click", () => {
            window.location.href = "customer_dashboard.html";
        });
    }

    if (trackBtn) {
        trackBtn.addEventListener("click", () => {
            window.location.href = "service-request.html";
        });
    }

    if (historyBtn) {
        historyBtn.addEventListener("click", () => {
            window.location.href = "service-history.html";
        });
    }

    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            window.location.href = "./login.html";
        });
    }

    if (menuToggle && sidebar && backdrop) {
        menuToggle.addEventListener("click", () => {
            sidebar.classList.toggle("active");
            backdrop.classList.toggle("active");
        });

        backdrop.addEventListener("click", () => {
            sidebar.classList.remove("active");
            backdrop.classList.remove("active");
        });
    }

    /* ===============================
       DATA
    =============================== */

    const applianceData = {
        Television: {
            warranty: "Active",
            installDate: "12-Jan-2024",
            purchaseDate: "10-Jan-2024",
            model: "TV-SONY-X123",
            serial: "TV987654321"
        },
        Refrigerator: {
            warranty: "Expired",
            installDate: "05-Mar-2022",
            purchaseDate: "01-Mar-2022",
            model: "LG-REF-456",
            serial: "RF123456789"
        },
        "Air Conditioner": {
            warranty: "Active",
            installDate: "20-Apr-2023",
            purchaseDate: "18-Apr-2023",
            model: "AC-Daikin-789",
            serial: "AC555888999"
        },
        "Washing Machine": {
            warranty: "Active",
            installDate: "15-Feb-2023",
            purchaseDate: "12-Feb-2023",
            model: "WM-Samsung-321",
            serial: "WM333777222"
        },
        "Microwave Oven": {
            warranty: "Expired",
            installDate: "10-Aug-2021",
            purchaseDate: "08-Aug-2021",
            model: "MW-IFB-999",
            serial: "MW111222333"
        }
    };

    const applianceQuestions = {
        Television: [
            "Is the TV turning on?",
            "Is there any sound?",
            "Is picture visible?",
            "Ports working?"
        ],
        Refrigerator: [
            "Is it turning on?",
            "Is cooling working?",
            "Any noise?",
            "Freezer ok?"
        ],
        "Air Conditioner": [
            "Is AC turning on?",
            "Cooling properly?",
            "Water leakage?",
            "Remote working?"
        ],
        "Washing Machine": [
            "Is it turning on?",
            "Water filling?",
            "Drum spinning?",
            "Draining?"
        ],
        "Microwave Oven": [
            "Is microwave turning on?",
            "Turntable rotating?",
            "Heating properly?",
            "Buttons working?"
        ]
    };

    /* ===============================
       STATE
    =============================== */

    let currentAppliance = "Television";
    let questions = applianceQuestions[currentAppliance];
    let currentQuestionIndex = 0;
    let selectedOption = null;
    let userAnswers = [];

    let currentDate = new Date();
    let currentMonth = currentDate.getMonth();
    let currentYear = currentDate.getFullYear();
    let selectedDate = null;

    const monthNames = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    /* ===============================
       PANEL FUNCTIONS
    =============================== */

    function showSummary() {
        summaryPanel.style.display = "block";
        issueCapturePanel.style.display = "none";
    }

    function showIssueCapture() {
        summaryPanel.style.display = "none";
        issueCapturePanel.style.display = "flex";
        questionBox.style.display = "block";
        complaintBox.style.display = "none";
        scheduleBox.style.display = "none";
        confirmationBox.style.display = "none";
    }

    function showComplaint() {
        questionBox.style.display = "none";
        complaintBox.style.display = "block";
        scheduleBox.style.display = "none";
        confirmationBox.style.display = "none";
    }

    function showSchedule() {
        questionBox.style.display = "none";
        complaintBox.style.display = "none";
        scheduleBox.style.display = "block";
        confirmationBox.style.display = "none";
        resetSchedule();
        generateCalendar();
    }

    function showConfirmation() {
        questionBox.style.display = "none";
        complaintBox.style.display = "none";
        scheduleBox.style.display = "none";
        confirmationBox.style.display = "block";
    }

    /* ===============================
       UPDATE DETAILS
    =============================== */

    function updateApplianceDetails(name) {
        const data = applianceData[name];
        if (!data) return;

        applianceName.textContent = name;
        applianceTitle.textContent = name;

        warranty.innerHTML =
            data.warranty === "Active"
                ? `<span class="status-active">Active</span>`
                : `<span class="status-expired">Expired</span>`;

        installDate.textContent = data.installDate;
        purchaseDate.textContent = data.purchaseDate;
        modelNumber.textContent = data.model;
        serialNumber.textContent = data.serial;
    }

    /* ===============================
       DROPDOWN CHANGE
    =============================== */

    if (applianceDropdown) {
        applianceDropdown.addEventListener("change", () => {
            const name = applianceDropdown.value;
            currentAppliance = name;

            updateApplianceDetails(name);

            questions = applianceQuestions[name] || [];
            currentQuestionIndex = 0;
            userAnswers = [];
            selectedOption = null;

            loadQuestion();
            showSummary();
        });
    }

    /* ===============================
       REMOVE APPLIANCE
    =============================== */

    if (removeBtn) {
        removeBtn.addEventListener("click", () => {
            const selected = applianceDropdown.value;

            if (confirm(`Remove ${selected}?`)) {
                const optionToRemove = applianceDropdown.querySelector(`option[value="${selected}"]`);
                if (optionToRemove) {
                    optionToRemove.remove();
                }

                if (applianceDropdown.options.length > 0) {
                    applianceDropdown.selectedIndex = 0;
                    applianceDropdown.dispatchEvent(new Event("change"));
                } else {
                    alert("No appliances left!");
                }
            }
        });
    }

    /* ===============================
       QUESTIONS
    =============================== */

    function loadQuestion() {
        if (!questions.length) {
            questionText.textContent = "No questions available.";
            nextBtn.style.display = "none";
            prevBtn.style.display = "none";
            return;
        }

        questionText.textContent = questions[currentQuestionIndex];

        optionButtons.forEach(btn => btn.classList.remove("selected"));
        selectedOption = null;
        validationMsg.style.display = "none";

        nextBtn.textContent = currentQuestionIndex === questions.length - 1 ? "Finish" : "Next";
        prevBtn.style.visibility = currentQuestionIndex === 0 ? "hidden" : "visible";

        if (userAnswers[currentQuestionIndex]) {
            selectedOption = userAnswers[currentQuestionIndex];
            optionButtons.forEach(btn => {
                if (btn.textContent.trim() === selectedOption) {
                    btn.classList.add("selected");
                }
            });
        }
    }

    optionButtons.forEach(btn => {
        btn.addEventListener("click", () => {
            optionButtons.forEach(b => b.classList.remove("selected"));
            btn.classList.add("selected");
            selectedOption = btn.textContent.trim();
            validationMsg.style.display = "none";
        });
    });

    if (nextBtn) {
        nextBtn.addEventListener("click", () => {
            if (!selectedOption) {
                validationMsg.style.display = "block";
                return;
            }

            userAnswers[currentQuestionIndex] = selectedOption;

            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                loadQuestion();
            } else {
                showComplaint();
            }
        });
    }

    if (prevBtn) {
        prevBtn.addEventListener("click", () => {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });
    }

    /* ===============================
       CUSTOM COMPLAINT
    =============================== */

    if (customComplaintTextarea && charCount) {
        customComplaintTextarea.addEventListener("input", () => {
            const len = customComplaintTextarea.value.length;
            charCount.textContent = len;
            const counter = charCount.parentElement;
            if (counter) {
                counter.classList.toggle("near-limit", len >= 450);
            }
        });
    }

    if (backToQuestionsFromComplaintBtn) {
        backToQuestionsFromComplaintBtn.addEventListener("click", () => {
            complaintBox.style.display = "none";
            questionBox.style.display = "block";
            currentQuestionIndex = questions.length - 1;
            loadQuestion();
        });
    }

    if (proceedToScheduleBtn) {
        proceedToScheduleBtn.addEventListener("click", () => {
            showSchedule();
        });
    }

    /* ===============================
       REQUEST BUTTON
    =============================== */

    if (requestBtn) {
    requestBtn.addEventListener("click", showIssueCapture);
    }

    if (newServiceBtn) {
    newServiceBtn.addEventListener("click", () => {
        window.location.href = "customer_dashboard.html";
    });
    }


    /* ===============================
       CALENDAR
    =============================== */

    function generateCalendar() {
        calendarTitle.textContent = `${monthNames[currentMonth]} ${currentYear}`;
        calendarDates.innerHTML = "";

        const today = new Date();
        const todayOnly = new Date(today.getFullYear(), today.getMonth(), today.getDate());

        const firstDay = new Date(currentYear, currentMonth, 1).getDay();
        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

        for (let i = 0; i < firstDay; i++) {
            const emptySpan = document.createElement("span");
            emptySpan.classList.add("empty");
            calendarDates.appendChild(emptySpan);
        }

        for (let d = 1; d <= daysInMonth; d++) {
            const span = document.createElement("span");
            span.textContent = d;

            const cellDate = new Date(currentYear, currentMonth, d);

            if (cellDate < todayOnly) {
                span.classList.add("past");
            } else {
                span.addEventListener("click", () => {
                    selectedDate = `${monthNames[currentMonth]} ${d}, ${currentYear}`;
                    calendarDates.querySelectorAll("span").forEach(s => s.classList.remove("selected"));
                    span.classList.add("selected");
                    scheduleValidation.style.display = "none";
                    updateSelectedText();
                });
            }

            if (
                d === todayOnly.getDate() &&
                currentMonth === todayOnly.getMonth() &&
                currentYear === todayOnly.getFullYear()
            ) {
                span.classList.add("today");
            }

            if (selectedDate === `${monthNames[currentMonth]} ${d}, ${currentYear}`) {
                span.classList.add("selected");
            }

            calendarDates.appendChild(span);
        }

        const currentRealMonth = todayOnly.getMonth();
        const currentRealYear = todayOnly.getFullYear();

        if (
            currentYear < currentRealYear ||
            (currentYear === currentRealYear && currentMonth <= currentRealMonth)
        ) {
            prevMonthBtn.disabled = true;
            prevMonthBtn.style.opacity = "0.5";
            prevMonthBtn.style.cursor = "not-allowed";
        } else {
            prevMonthBtn.disabled = false;
            prevMonthBtn.style.opacity = "1";
            prevMonthBtn.style.cursor = "pointer";
        }
    }

    function updateSelectedText() {
        selectedText.textContent = selectedDate
            ? selectedDate
            : "Please select a date";
    }

    function resetSchedule() {
        selectedDate = null;
        scheduleValidation.style.display = "none";
        updateSelectedText();
    }

    if (prevMonthBtn) {
        prevMonthBtn.addEventListener("click", () => {
            const today = new Date();
            const realMonth = today.getMonth();
            const realYear = today.getFullYear();

            if (
                currentYear < realYear ||
                (currentYear === realYear && currentMonth <= realMonth)
            ) {
                return;
            }

            currentMonth--;

            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }

            generateCalendar();
        });
    }

    if (nextMonthBtn) {
        nextMonthBtn.addEventListener("click", () => {
            currentMonth++;

            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }

            generateCalendar();
        });
    }

    if (backToQuestionsBtn) {
        backToQuestionsBtn.addEventListener("click", () => {
            scheduleBox.style.display = "none";
            complaintBox.style.display = "block";
        });
    }

    if (confirmScheduleBtn) {
        confirmScheduleBtn.addEventListener("click", () => {
            if (!selectedDate) {
                scheduleValidation.style.display = "block";
                return;
            }

            confirmAppliance.textContent = currentAppliance;
            confirmDate.textContent = selectedDate;

            const complaintText = customComplaintTextarea.value.trim();
            if (complaintText) {
                confirmComplaint.textContent = complaintText;
                confirmComplaintRow.style.display = "block";
            } else {
                confirmComplaintRow.style.display = "none";
            }

            showConfirmation();
        });
    }

    /* ===============================
       SIDEBAR ACTIVE STATE
    =============================== */

    const menuItems = document.querySelectorAll(".menu ul li");

    menuItems.forEach(item => {
        item.addEventListener("click", () => {
            menuItems.forEach(li => li.classList.remove("active"));
            item.classList.add("active");
        });
    });

    /* ===============================
       INIT
    =============================== */

    updateApplianceDetails(currentAppliance);
    loadQuestion();
    showSummary();
});
