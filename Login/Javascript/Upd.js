/* ================================
   MOCK OLD PASSWORD (Demo)
================================ */
const storedPassword = "Temp@1234";

/* ================================
   PASSWORD VALIDATION
================================ */
function isStrongPassword(password) {
    const pattern =
        /^(?=(?:[^A-Z]*[A-Z][^A-Z]*$))(?=.*[a-z])(?=.*[@$!%*?&]).{8,}$/;
    return pattern.test(password);
}

/* ================================
   FORM VALIDATION
================================ */
function validatePasswordForm(event) {
    event.preventDefault();

    const oldPwd = document.getElementById("oldPassword").value.trim();
    const newPwd = document.getElementById("newPassword").value.trim();
    const confirmPwd = document.getElementById("confirmPassword").value.trim();
    const message = document.getElementById("message");

    message.style.color = "#D32F2F";
    message.textContent = "";

    /* Required checks */
    if (!oldPwd || !newPwd || !confirmPwd) {
        message.textContent = "All fields are required";
        return false;
    }

    /* Old password check */
    if (oldPwd !== storedPassword) {
        message.textContent = "Temporary password is incorrect";
        return false;
    }

    /* Same password check */
    if (oldPwd === newPwd) {
        message.textContent = "New password must be different from old password";
        return false;
    }

    /* Password strength */
    if (!isStrongPassword(newPwd)) {
        message.textContent =
            "New password does not meet security requirements";
        return false;
    }

    /* Confirm password */
    if (newPwd !== confirmPwd) {
        message.textContent = "Confirm password does not match";
        return false;
    }

    /* SUCCESS */
    message.style.color = "green";
    message.textContent = "✅ Password updated successfully";

    /* Redirect */
    setTimeout(() => {
        window.location.href = "../Technician/Technician_Dashboard.html";
    }, 1500);

    return false;
}