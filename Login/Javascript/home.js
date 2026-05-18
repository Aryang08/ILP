
document.addEventListener("DOMContentLoaded", function () {

    const loginToggle = document.getElementById("loginToggle");
    const loginDropdown = document.getElementById("loginDropdown");

    // Toggle dropdown on Login click
    loginToggle.addEventListener("click", function (event) {
        event.stopPropagation(); // prevent body click
        loginDropdown.classList.toggle("active");
    });

    // Close dropdown when clicking outside
    document.addEventListener("click", function () {
        loginDropdown.classList.remove("active");
    });

});
