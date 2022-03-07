package agreements;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public final class AgreementTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            Agreement agreement = new Agreement("CompanyOne", "CompanyTwo", "open");
            assertThat(agreement).isEqualTo(agreement);
        }

        @Test
        public void isSymmetric() {
            Agreement agreementA = new Agreement("CompanyOne", "CompanyTwo", "open");
            Agreement agreementB = new Agreement("CompanyOne", "CompanyTwo", "open");

            assertThat(agreementA).isEqualTo(agreementB);
            assertThat(agreementB).isEqualTo(agreementA);
        }

        @Test
        public void isTransitive(){
            Agreement agreementA = new Agreement("CompanyOne", "CompanyTwo", "open");
            Agreement agreementB = new Agreement("CompanyOne", "CompanyTwo", "open");
            Agreement agreementC = new Agreement("CompanyOne", "CompanyTwo", "open");

            assertThat(agreementA).isEqualTo(agreementB);
            assertThat(agreementB).isEqualTo(agreementC);
            assertThat(agreementA).isEqualTo(agreementC);
        }

        @Test
        public void handlesInequality() {
            Agreement agreementA = new Agreement("CompanyOne", "CompanyTwo", "open");
            Agreement agreementB = new Agreement("CompanyTwo", "CompanyThree", "open");

            assertThat(agreementA).isNotEqualTo(agreementB);
        }

        @Test
        public void handlesOtherObjects(){
            Agreement agreementA = new Agreement("CompanyOne", "CompanyTwo", "open");
            String agreementB = "not an agreement";

            assertThat(agreementA).isNotEqualTo(agreementB);
        }

        @Test
        public void handlesNull(){
            Agreement agreement = new Agreement("CompanyOne", "CompanyTwo", "open");

            assertThat(agreement).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifierAsset() {
        Agreement agreement = new Agreement("CompanyOne", "CompanyTwo", "open");

        assertThat(agreement.toString()).isEqualTo("Agreement{party1='CompanyOne', party2='CompanyTwo', status='open'}");
    }

}
