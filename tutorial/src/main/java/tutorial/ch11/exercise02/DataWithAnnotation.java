package tutorial.ch11.exercise02;

@CustomSerializable(serialVersionUID = 2L)
class DataWithAnnotation {

    private int number;
    private String data;
    private Integer test = 1;

    public DataWithAnnotation() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(final Integer test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "DataWithAnnotation{" +
            "number=" + number +
            ", data='" + data + '\'' +
            ", test=" + test +
            '}';
    }
}
