package it.unicam.cs.agrotrace.validator;

import lombok.Getter;
import org.springframework.lang.NonNull;


import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


public final class ValidationResult implements Iterable<Map.Entry<String, String>> {
    @Getter
    private final Object validatedObject;
    private final Map<String, String> errors;


    public ValidationResult(Object validatedObject, Map<String, String> errors) {
        this.validatedObject = validatedObject;
        this.errors = errors == null ? Collections.emptyMap() : errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Optional<String> getError(String field) {
        return Optional.ofNullable(errors.get(field));
    }

    public int getErrorCount() {
        return errors.size();
    }

    @Override
    public @NonNull Iterator<Map.Entry<String, String>> iterator() {
        return errors.entrySet().iterator();
    }

    public Stream<Map.Entry<String, String>> stream() {
        return errors.entrySet().stream();
    }

}
