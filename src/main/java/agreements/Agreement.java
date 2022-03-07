package agreements;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType
public class Agreement {

    @Property()
    private final String party1;

    @Property()
    private final String party2;

    @Property
    private final String status;

    public Agreement(@JsonProperty("party1") final String party1,
                     @JsonProperty("party2") final String party2,
                     @JsonProperty("status") final String status) {
        this.party1 = party1;
        this.party2 = party2;
        this.status = status;

    }

    public String getParty1() {
        return party1;
    }

    public String getParty2() {
        return party2;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement other = (Agreement) o;
        return Objects.deepEquals(
                new String[] {getParty1(), getParty2(), getStatus()},
                new String[] {other.getParty1(), other.getParty2(), other.getStatus()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParty1(), getParty2(), getParty2());
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "party1='" + party1 + '\'' +
                ", party2='" + party2 + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
