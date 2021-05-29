import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTopic {
    @Test
    public void testInicializacao() {
        Topic topic = new Topic(10,2);
        assertEquals(topic.getAssentosNormais().size(), 8, "Quantidade errada de assentos comuns!");
        assertEquals(topic.getAssentosPrioritarios().size(), 2, "Quantidade errada de assentos prioritários!");
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
        Passageiro eduarda = topic.getAssentosNormais().get(0);
        Passageiro marlus = topic.getAssentosPrioritarios().get(0);
        assertNotNull(marlus, "Falha na inserção!");
        assertNotNull(eduarda, "Falha na inserção!");
    }
    @Test
    public void testPassageiroSemPrioridadeComVagaComum() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Eduarda", 19)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Marlus", 120)), "A topic tem vaga mas o passageiro não foi inserido!");
        Passageiro eduarda = topic.getAssentosNormais().get(0);
        assertEquals("Eduarda", eduarda.getNome(), "Inseriu o passageiro jovem na prioridade enquanto ainda tinha vaga comum!");
    }
    @Test
    public void testPassageiroPrioriatioSemVagaPrioritaria() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Eduarda", 190)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Guthyerri", 120)), "Tinha uma vaga comum pro idoso ocupar!");
        Passageiro guthyerri = topic.getAssentosNormais().get(0);
        assertNotNull(guthyerri, "Retornou true mas não inseriu!");
    }
    @Test
    public void testPassageiroSemPrioriadadeSemVagaComum() {
        Topic topic = new Topic(2,1);
        assertTrue(topic.subir(new Passageiro("Eduarda", 19)), "A topic tem vaga mas o passageiro não foi inserido!");
        assertTrue(topic.subir(new Passageiro("Guthyerri", 19)), "Tinha assento prioritário vago pra ele sentar!");
        Passageiro guthyerri = topic.getAssentosPrioritarios().get(0);
        assertNotNull(guthyerri, "Retornou true mas não inseriu!");
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
        Passageiro guthyerri = topic.getAssentosNormais().get(0);
        assertNull(guthyerri, "Retornou true mas não removeu a pessoa da topic!");
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