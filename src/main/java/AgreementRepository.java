import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

@Contract(name = "Agreements", info = @Info(title = "Agreements contract", description = "A java chaincode demo", version = "0.0.1-SNAPSHOT"))
@Default
public final class AgreementRepository implements ContractInterface {

    private final Genson genson = new Genson();

    /**This method will be call only the first time that the chaincode is deployed,
     * and will create an agreement to test that the query is working properly.
     * In a real use case, this action could be avoided.
     * @param ctx
     */
    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        Agreement agreement = new Agreement("MyCompany", "OtherCompany", "open");

        String agreementState = genson.serialize(agreement);
        stub.putStringState("ARG001", agreementState);
    }

    @Transaction()
    public Agreement getAgreement(final Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String agreementState = stub.getStringState(key);

        if (agreementState.isEmpty()){
            String errorMessage = String.format("Agreement %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement not found");
        }

        Agreement agreement = genson.deserialize(agreementState, Agreement.class);

        return agreement;
    }



}
