package at.fhhagenberg.sqe.model;

public class Resource<TData> {
    private TData data;
    private Exception error;
    private Status status;

    public Resource(TData data, Exception error, Status status) {
        this.data = data;
        this.error = error;
        this.status = status;
    }

    public TData getData() {
        return data;
    }

    public Exception getError() {
        return error;
    }

    public Status getStatus() {
        return status;
    }

    public static <TData> Resource<TData> loading(TData data) {
        return new Resource(data, null, Status.LOADING);
    }

    public static <TData> Resource<TData> success(TData data) {
        return new Resource(data, null, Status.SUCCESS);
    }

    public static <TData> Resource<TData> error(Exception error) {
        return new Resource(null, error, Status.ERROR);
    }
}
