package interpreter.value;

public class IntegerValue extends Value<Integer> {

    public static final IntegerValue Zero = new IntegerValue(0);

    private Integer value;

    public IntegerValue(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

}
