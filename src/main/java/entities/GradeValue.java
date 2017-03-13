package entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pnikrat on 13.03.17.
 */
@XmlType(name = "concreteGrade")
@XmlEnum
public enum GradeValue {
    @XmlEnumValue("2.0")
    TWO(2.0),
    @XmlEnumValue("2.5")
    TWOHALF(2.5),
    @XmlEnumValue("3.0")
    THREE(3.0),
    @XmlEnumValue("3.5")
    THREEHALF(3.5),
    @XmlEnumValue("4.0")
    FOUR(4.0),
    @XmlEnumValue("4.5")
    FOURHALF(4.5),
    @XmlEnumValue("5.0")
    FIVE(5.0);

    public final Double value;

    GradeValue(Double value) {
        this.value = value;
    }

    @JsonValue
    public Double getValue() {
        return this.value;
    }

    @JsonCreator
    public static GradeValue setValue(Double value) {
        GradeValue[] gradeValues = GradeValue.values();
        for (GradeValue x : gradeValues) {
            if (x.getValue().equals(value))
                return x;
        }
        return null;
    }
}
