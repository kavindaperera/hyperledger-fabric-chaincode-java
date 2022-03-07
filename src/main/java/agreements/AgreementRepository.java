package agreements;

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

    /**
     * This method will be call only the first time that the chaincode is deployed,
     * and will create an agreement to test that the query is working properly.
     * In a real use case, this action could be avoided.
     *
     * @param ctx transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        Agreement agreement = new Agreement("MyCompany", "OtherCompany", "open");

        String agreementState = genson.serialize(agreement);
        stub.putStringState("ARG001", agreementState);
    }

    /**
     * Returns the agreement stored in the ledger
     * @param ctx transaction context
     * @param key
     * @return agreement stored in the ledger
     */
    @Transaction()
    public Agreement getAgreement(final Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String agreementState = stub.getStringState(key);

        if (agreementState.isEmpty()) {
            String errorMessage = String.format("Agreement %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement not found");
        }

        Agreement agreement = genson.deserialize(agreementState, Agreement.class);

        return agreement;
    }

    /**
     * This method will receive the parameters to create a new agreement, and persists it on the ledger,
     * in case that do not exist.
     * @param ctx transaction context
     * @param key
     * @param party1
     * @param party2
     * @param status agreement status
     * @return new agreement
     */
    @Transaction()
    public Agreement createAgreement(final Context ctx, final String key, final String party1, final String party2, final String status) {
        ChaincodeStub stub = ctx.getStub();

        String agreementState = stub.getStringState(key);

        if (!agreementState.isEmpty()){
            String errorMessage = String.format("Agreement %s already exists", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement already exists");
        }

        Agreement agreement = new Agreement(party1, party2, status);
        agreementState = genson.serialize(agreement);
        stub.putStringState(key, agreementState);

        return agreement;
    }

    /**
     * This method will receive the new agreement status, and change it on the agreement.
     * @param ctx transaction context
     * @param key
     * @param newStatus agreement status
     * @return new agreement
     */
    @Transaction()
    public Agreement changeAgreementStatus(final Context ctx, final String key, final String newStatus) {
        ChaincodeStub stub = ctx.getStub();

        String agreementState = stub.getStringState(key);

        if (agreementState.isEmpty()) {
            String errorMessage = String.format("Agreement %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement not found");
        }

        Agreement agreement = genson.deserialize(agreementState, Agreement.class);

        Agreement newAgreement = new Agreement(agreement.getParty1(), agreement.getParty2(), agreement.getStatus());
        String newAgreementState = genson.serialize(newAgreement);
        stub.putStringState(key, newAgreementState);

        return newAgreement;
    }

}
