package tutorial.ch11.exercise02;

import java.io.Serializable;

class DataWithInterface implements Serializable {
    private final long serialVersionUID = 1L;
    private final String data;

    public DataWithInterface(final String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataWithInterface{"
            + "data='" + data + '\''
            + '}';
    }
}