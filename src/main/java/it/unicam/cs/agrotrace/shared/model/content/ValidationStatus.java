package it.unicam.cs.agrotrace.shared.model.content;

public enum ValidationStatus {
    PENDING,
    NEEDS_CORRECTION,
    REJECTED,
    APPROVED;

    /**
     * Converts a string to a ValidationStatus enum.
     *
     * @param status the string representation of the validation status
     * @return the corresponding ValidationStatus enum, or null if the input is null
     */
    public static ValidationStatus fromString(String status) {
        return status != null
                ? ValidationStatus.valueOf(status.toUpperCase())
                : null;
    }
}
