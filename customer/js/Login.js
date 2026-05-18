/* =====================================
   Allow only numbers in phone input
===================================== */

function restrictNumber(input) {
  // Remove non-numeric characters
  let value = input.value.replace(/[^0-9]/g, "");

  // If first digit exists and is NOT 6,7,8,9 → remove it
  if (value.length > 0 && !/^[6-9]/.test(value)) {
    value = value.substring(1);
  }

  // Limit to 10 digits max
  input.value = value.substring(0, 10);
}
function gotoHome(){
window.location.href = "../Home.html";
}
/* =====================================
   Form validation & navigation
===================================== */
function validateForm(event) {
  event.preventDefault(); // Stop page reload

  const mobileInput = document.getElementById("mobile");
  const mobile = mobileInput.value.trim();
  const otpInputs = document.querySelectorAll(".otp-container input");
  const errorText = document.querySelector(".error-text");
  const clickedButton = event.submitter; // Detect which button was clicked

  // Reset message
  errorText.textContent = "";
  errorText.style.color = "#D32F2F";

  /* =====================================
     PHONE NUMBER VALIDATION FUNCTION
  ===================================== */
  function isValidIndianMobile(number) {
    const pattern = /^[6-9][0-9]{9}$/; // starts with 6-9 & total 10 digits
    return pattern.test(number);
  }

  /* =====================================
     VERIFY BUTTON LOGIC
  ===================================== */
  if (clickedButton && clickedButton.id === "verifyBtn") {

    if (mobile === "") {
      errorText.textContent = "Phone number is required";
      return false;
    }

    if (!isValidIndianMobile(mobile)) {
      errorText.textContent =
        "Enter a valid 10-digit mobile number";
      return false;
    }

    // Simulate OTP sent
    errorText.style.color = "green";
    errorText.textContent = "✅ OTP has been sent to your mobile number";
    return false;
  }

  /* =====================================
     CONTINUE BUTTON LOGIC
  ===================================== */
  if (clickedButton && clickedButton.id === "continueBtn") {

    if (mobile === "") {
      errorText.textContent = "Phone number is required";
      return false;
    }

    if (!isValidIndianMobile(mobile)) {
      errorText.textContent =
        "Enter a valid 10-digit mobile number starting with 6, 7, 8, or 9";
      return false;
    }

    // Check OTP fields
    for (let input of otpInputs) {
      if (input.value.trim() === "") {
        errorText.textContent = "Please enter complete OTP";
        return false;
      }
    }

    // Success → redirect
    errorText.style.color = "green";
    errorText.textContent = "✅ Login successful. Redirecting...";

    setTimeout(() => {
      window.location.href = "customer_dashboard.html";
    }, 800);

    return false;
  }
}


document.querySelectorAll(".otp-container input").forEach((input, index, inputs) => {
  input.addEventListener("input", () => {
    if (input.value && index < inputs.length - 1) {
      inputs[index + 1].focus();
    }
  });

  input.addEventListener("keydown", (e) => {
    if (e.key === "Backspace" && !input.value && index > 0) {
      inputs[index - 1].focus();
    }
  });
});