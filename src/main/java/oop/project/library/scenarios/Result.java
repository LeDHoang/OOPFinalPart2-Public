package oop.project.library.scenarios;

public abstract class Result<T> {

    public static class Success<T> extends Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        public T value() {
            return value;
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
