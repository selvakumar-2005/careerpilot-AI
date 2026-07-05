import { useState, useCallback } from 'react';

/**
 * Generic form state manager.
 * Handles values, errors, touched state, and change/blur events.
 *
 * @param {Object} initialValues - Initial field values
 * @param {Function} validate    - Function(values) → errors object
 */
function useForm(initialValues, validate) {
  const [values, setValues]   = useState(initialValues);
  const [errors, setErrors]   = useState({});
  const [touched, setTouched] = useState({});

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    setValues((prev) => ({ ...prev, [name]: value }));

    // Re-validate the changed field immediately
    if (validate) {
      const newErrors = validate({ ...values, [name]: value });
      setErrors((prev) => ({ ...prev, [name]: newErrors[name] || '' }));
    }
  }, [values, validate]);

  const handleBlur = useCallback((e) => {
    const { name } = e.target;
    setTouched((prev) => ({ ...prev, [name]: true }));

    if (validate) {
      const newErrors = validate(values);
      setErrors((prev) => ({ ...prev, [name]: newErrors[name] || '' }));
    }
  }, [values, validate]);

  const validateAll = useCallback(() => {
    if (!validate) return true;
    const newErrors = validate(values);
    setErrors(newErrors);
    // Mark all fields as touched so errors become visible
    const allTouched = Object.keys(values).reduce(
      (acc, key) => ({ ...acc, [key]: true }),
      {}
    );
    setTouched(allTouched);
    return Object.values(newErrors).every((e) => !e);
  }, [values, validate]);

  const reset = useCallback(() => {
    setValues(initialValues);
    setErrors({});
    setTouched({});
  }, [initialValues]);

  /** Returns the error for a field only if it has been touched */
  const getFieldError = useCallback(
    (name) => (touched[name] ? errors[name] : ''),
    [errors, touched]
  );

  return {
    values,
    errors,
    touched,
    handleChange,
    handleBlur,
    validateAll,
    reset,
    getFieldError,
    setValues,
  };
}

export default useForm;
