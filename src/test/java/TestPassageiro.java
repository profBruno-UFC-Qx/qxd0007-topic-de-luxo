import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Passageiro")
public class TestPassageiro {

    @Test
    @DisplayName("Construtor inicializa nome e idade")
    public void criacaoDePassageiro() {
        Passageiro p = new Passageiro("joao", 25);
        assertEquals("joao", p.getNome(), "O nome do passageiro deve ser inicializado corretamente.");
        assertEquals(25, p.getIdade(), "A idade do passageiro deve ser inicializada corretamente.");
    }

    @Test
    @DisplayName("toString retorna nome:idade")
    public void testToString() {
        Passageiro p = new Passageiro("ana", 67);
        assertEquals("ana:67", p.toString(), "A representação em string do passageiro está incorreta.");
    }

    @Test
    @DisplayName("Idoso é prioritário e jovem não é")
    public void prioridadePorIdade() {
        assertTrue(new Passageiro("Guthyerri", 160).ePrioritario(), "Um passageiro de 160 anos é prioritário!");
        assertFalse(new Passageiro("Guthyerri", 19).ePrioritario(), "Um passageiro de 19 anos não é prioritário!");
    }

    @Test
    @DisplayName("Prioridade começa exatamente aos 65 anos")
    public void limiteDeIdadePrioridade() {
        assertTrue(new Passageiro("Marlus", 65).ePrioritario(), "Um passageiro de 65 anos é prioritário!");
        assertFalse(new Passageiro("Marlus", 64).ePrioritario(), "Um passageiro de 64 anos não é prioritário!");
    }
}
