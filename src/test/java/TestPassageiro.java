import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestPassageiro {
    @Test
    public void testTruePriority() {
        Passageiro passageiro = new Passageiro("Guthyerri", 160);
        assertTrue(passageiro.ePrioritario(), "O passageiro é prioridade");
    }

    @Test
    public void testFalsePriority(){
        Passageiro passageiro = new Passageiro("Guthyerri", 19);
        assertFalse(passageiro.ePrioritario(), "O passageiro é prioridade");
    }
}