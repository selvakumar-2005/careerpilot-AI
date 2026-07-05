package com.careerpilot.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_#^()+=\\-])[A-Za-z\\d@$!%*?&_#^()+=\\-]{8,}$",
        message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
    )
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    public RegisterRequest() {}

    public String getFullName()               { return fullName; }
    public void setFullName(String v)         { this.fullName = v; }
    public String getEmail()                  { return email; }
    public void setEmail(String v)            { this.email = v; }
    public String getPassword()               { return password; }
    public void setPassword(String v)         { this.password = v; }
    public String getConfirmPassword()        { return confirmPassword; }
    public void setConfirmPassword(String v)  { this.confirmPassword = v; }
}
