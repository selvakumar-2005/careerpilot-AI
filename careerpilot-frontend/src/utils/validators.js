/**
 * Client-side validation helpers.
 * Mirror the backend constraints so users get instant feedback.
 */

export const validators = {
  fullName: (value) => {
    if (!value || !value.trim()) return 'Full name is required';
    if (value.trim().length < 2) return 'Full name must be at least 2 characters';
    if (value.trim().length > 100) return 'Full name must be under 100 characters';
    return null;
  },

  email: (value) => {
    if (!value || !value.trim()) return 'Email is required';
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) return 'Please enter a valid email address';
    return null;
  },

  password: (value) => {
    if (!value) return 'Password is required';
    if (value.length < 8) return 'Password must be at least 8 characters';
    if (!/[A-Z]/.test(value)) return 'Password must contain at least one uppercase letter';
    if (!/[a-z]/.test(value)) return 'Password must contain at least one lowercase letter';
    if (!/\d/.test(value)) return 'Password must contain at least one number';
    if (!/[@$!%*?&_#^()+=\-]/.test(value)) return 'Password must contain at least one special character';
    return null;
  },

  confirmPassword: (password, confirmPassword) => {
    if (!confirmPassword) return 'Please confirm your password';
    if (password !== confirmPassword) return 'Passwords do not match';
    return null;
  },
};

/** Returns true if the errors object has no non-null values. */
export const isFormValid = (errors) =>
  Object.values(errors).every((err) => err === null || err === '');
