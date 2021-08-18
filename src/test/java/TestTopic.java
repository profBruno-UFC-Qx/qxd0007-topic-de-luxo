import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTopic {
    @Test
    public void testInicializacao() {
        Topic topic = new Topic(10,2);
        assertEquals(8, topic.getAssentosNormais().size(), "Quantidade errada de assentos comuns!");
        assertEquals(2, topic.getAssentosPrioritarios().size(), "Quantidade errada de assentos prioritários!");
    }

    @Test
    public void testQdtPrioritariosMaiorCapacidade() {
        assertThrows(IllegalArgumentException.class, () -> {new Topic(5,10);}, "Não foi retornado um erro do tipo IllegalArgumentException!");
    }

    @Test
    public void testPassageiroPrioriatioComVagaPrioritaria() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Marlus", 120)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Eduarda", 19)), "A topic tem vaga mas o passageiro não foi inserido!");;
        Passageiro eduarda = topic.getPassageiroAssentoNormal(0);
        Passageiro marlus = topic.getPassageiroAssentoPrioritario(0);
        assertNotNull(marlus, "Falha na inserção!");
        assertEquals("Marlus", marlus.getNome(), "Passageiro errado");
        assertNotNull(eduarda, "Falha na inserção!");
        assertEquals("Eduarda", eduarda.getNome(), "Passageiro errado");
    }
    @Test
    public void testPassageiroSemPrioridadeComVagaComum() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Eduarda", 19)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Marlus", 120)), "A topic tem vaga mas o passageiro não foi inserido!");
        Passageiro eduarda = topic.getPassageiroAssentoNormal(0);
        assertNotNull(eduarda, "Falha na inserção!");
        assertEquals("Eduarda", eduarda.getNome(), "Passageiro sem prioridade nao encontrado");
    }
    @Test
    public void testPassageiroPrioriatioSemVagaPrioritaria() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Eduarda", 190)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Guthyerri", 120)), "Tinha uma vaga comum pro idoso ocupar!");
        Passageiro guthyerri = topic.getPassageiroAssentoNormal(0);
        assertNotNull(guthyerri, "Passageiro nao encontrado");
        assertEquals("Guthyerri", guthyerri.getNome(), "Passageiro com prioridade nao encontrado");
    }
    @Test
    public void testPassageiroSemPrioriadadeSemVagaComum() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Eduarda", 19)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Guthyerri", 19)), "Tinha assento prioritário vago pra ele sentar!");
        Passageiro guthyerri = topic.getPassageiroAssentoPrioritario(0);
        assertNotNull(guthyerri, "Passageiro nao encontrado");
        assertEquals("Guthyerri", guthyerri.getNome(), "Passageiro com prioridade nao encontrado");
    }
    @Test
    public void testDescidaTopicVazia() {
        Topic topic = new Topic(2,1);
        assertFalse(topic.descer("Joaquim"), "Removeu alguém sendo que a topic está vazia!");
    }
    @Test
    public void testDescidaDePassageiro() {
        Topic topic = new Topic(2,1);
        topic.subir(new Passageiro("Guthyerri", 19));
        assertTrue(topic.descer("Guthyerri"), "Falha ao remover!");
        Passageiro guthyerri = topic.getPassageiroAssentoNormal(0);
        assertNull(guthyerri, "O passageiro nao foi removido");
        assertEquals(2, topic.getVagas(), "Removeu uma posição do array, deveria somente setar como null!!!");
    }
    @Test
    public void testDescerPassageiroNaoSubiu() {
        Topic topic = new Topic(2,1);
        topic.subir(new Passageiro("Guthyerri", 19));
        topic.descer("Guthyerri");
        assertFalse(topic.descer("Zé Adolfo"), "Removeu alguém que não estava na topic!");
    }

    @Test
    public void testCapacidade() {
        Topic topic = new Topic(2,1);
        topic.subir(new Passageiro("Marlus", 12));
        topic.subir(new Passageiro("Guthyerri", 130));
        assertFalse(topic.subir(new Passageiro("Hermilson", 16)), "Tamanho da Topic foi estourado!");
    }

    @Test
    public void testMostrarVagas() {
        Topic topic = new Topic(5,3);
        assertEquals(5,topic.getVagas(), "Quantidade de vagas disponíveis está errada!");
        topic.subir(new Passageiro("Bode", 5));
        assertEquals(4,topic.getVagas(), "Quantidade de vagas disponíveis está errada!");
    }
    @Test
    public void testMostrarTopicVazia() {
        Topic topic = new Topic(10,5);
        assertEquals("[@ @ @ @ @ = = = = = ]", topic.toString(), "Sua lista está errada!");
    }
    @Test
    public void testMostrarTopicCheia() {
        Topic topic = new Topic(10,5);
        topic.subir(new Passageiro("Marlus", 120));
        topic.subir(new Passageiro("Eduarda", 19));
        topic.subir(new Passageiro("Guthyerri", 19));
        assertEquals("[@Marlus @ @ @ @ =Eduarda =Guthyerri = = = ]", topic.toString(), "Sua lista está com impressão errada!");
    }

}