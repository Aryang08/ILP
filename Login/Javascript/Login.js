/* ===============================
   GLOBAL OTP (Testing)
================================ */

const generatedOTP = "1234";

/* ===============================
   Allow only numbers
================================ */

function restrictNumber(input) {
  let value = input.value.replace(/[^0-9]/g, "");

  if (value.length > 0 && !/^[6-9]/.test(value)) {
    value = value.substring(1);
  }

  input.value = value.substring(0, 10);
}

/* ===============================
   Validate Indian Mobile
================================ */

function isValidIndianMobile(number) {
  return /^[6-9][0-9]{9}$/.test(number);
}

/* ===============================
   MAIN FORM VALIDATION
================================ */

function validateForm(event) {
  event.preventDefault();

  const mobile = document.getElementById("mobile").value.trim();
  const otpInputs = document.querySelectorAll(".otp-container input");
  const errorText = document.querySelector(".error-text");
  const clickedButton = event.submitter;

  errorText.textContent = "";
  errorText.style.color = "#D32F2F";

  /* ===============================
     VERIFY OTP BUTTON
  ================================ */

  if (clickedButton.id === "verifyBtn") {

    if (!mobile) {
      errorText.textContent = "Phone number is required";
      return false;
    }

    if (!isValidIndianMobile(mobile)) {
      errorText.textContent = "Enter a valid 10-digit mobile number";
      return false;
    }

    // ✅ Simulate OTP send
    errorText.style.color = "green";
    errorText.textContent = "✅ OTP sent successfully";
    console.log("OTP:", generatedOTP);
    return false;
  }

  /* ===============================
     CONTINUE (LOGIN)
  ================================ */

  if (clickedButton.id === "continueBtn") {

    if (!mobile) {
      errorText.textContent = "Phone number is required";
      return false;
    }

    if (!isValidIndianMobile(mobile)) {
      errorText.textContent = "Invalid mobile number";
      return false;
    }

    let enteredOTP = "";
    otpInputs.forEach(input => enteredOTP += input.value);

    if (enteredOTP.length < 4) {
      errorText.textContent = "Please enter complete OTP";
      return false;
    }

    if (enteredOTP !== generatedOTP) {
      errorText.textContent = "❌ Incorrect OTP";
      return false;
    }

    /* ✅ OTP CORRECT → LOGIN */
    errorText.style.color = "green";
    errorText.textContent = "✅ Login successful. Redirecting…";

    setTimeout(() => {
      window.location.href = "../customer/customer_dashboard.html";
    }, 1000);

    return false;
  }
}

/* ===============================
   OTP Auto Move Logic
================================ */

document.querySelectorAll(".otp-container input").forEach((input, index, inputs) => {

  input.addEventListener("input", () => {
    input.value = input.value.replace(/[^0-9]/g, "");

    if (input.value && index < inputs.length - 1) {
      inputs[index + 1].focus();
    }
  });

  input.addEventListener("keydown", e => {
    if (e.key === "Backspace" && !input.value && index > 0) {
      inputs[index - 1].focus();
    }
  });

});