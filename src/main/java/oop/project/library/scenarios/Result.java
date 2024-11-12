package oop.project.library.scenarios;

import java.util.Objects;

public abstract class Result<T> {

    public static class Success<T> extends Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        public T value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success<?> success = (Success<?>) o;
            return Objects.equals(value, success.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Success{" +
                    "value=" + value +
                    '}';
        }
    }

    public static class Failure<T> extends Result<T> {
        private final Exception error;

        public Failure(Exception error) {
            this.error = error;
        }

        public Exception error() {
            return error;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Failure<?> failure = (Failure<?>) o;
            return Objects.equals(error, failure.error);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error);
        }

        @Override
        public String toString() {
            return "Failure{" +
                    "error=" + error +
                    '}';
        }
    }

    public static <T> Result<T> ok(T value) {
        return new Success<>(value);
    }

    public static <T> Result<T> error(Exception error) {
        return new Failure<>(error);
    }

    public boolean isOk() {
        return this instanceof Success;
    }
}
