import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;

@Contract(name = "Agreements", info = @Info(title = "Agreements contract", description = "A java chaincode demo", version = "0.0.1-SNAPSHOT"))
@Default
public final class AgreementRepository implements ContractInterface {

    private final Genson genson = new Genson();

}
