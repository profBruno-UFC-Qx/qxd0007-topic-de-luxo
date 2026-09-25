import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Topic")
public class TestTopic {

    private static void subirComSucesso(Topic topic, String nome, int idade) {
        assertTrue(topic.subir(new Passageiro(nome, idade)), "A topic tem vaga mas " + nome + " não foi inserido!");
    }

    // ---------- Inicialização ----------

    @Test
    @DisplayName("Inicialização separa assentos prioritários e normais")
    public void inicializacao() {
        Topic topic = new Topic(10, 2);
        assertEquals(8, topic.getNumeroAssentosNormais(), "Quantidade errada de assentos normais!");
        assertEquals(2, topic.getNumeroAssentosPrioritarios(), "Quantidade errada de assentos prioritários!");
        assertEquals(10, topic.getVagas(), "A quantidade de vagas disponíveis está incorreta.");
    }

    @Test
    @DisplayName("Topic sem assentos prioritários aloca idoso em assento normal")
    public void topicSemAssentosPrioritarios() {
        Topic topic = new Topic(2, 0);
        assertEquals(0, topic.getNumeroAssentosPrioritarios(), "Quantidade errada de assentos prioritários!");
        subirComSucesso(topic, "Marlus", 70);
        assertEquals("Marlus", topic.getPassageiroAssentoNormal(0).getNome(), "O idoso deveria ir para o assento normal!");
        assertEquals("[=Marlus:70 = ]", topic.toString(), "Sua topic está com impressão errada!");
    }

    @Test
    @DisplayName("Topic só com assentos prioritários aloca jovem em assento prioritário")
    public void topicSoComAssentosPrioritarios() {
        Topic topic = new Topic(2, 2);
        assertEquals(0, topic.getNumeroAssentosNormais(), "Quantidade errada de assentos normais!");
        subirComSucesso(topic, "Eduarda", 19);
        assertEquals("Eduarda", topic.getPassageiroAssentoPrioritario(0).getNome(), "A jovem deveria ir para o assento prioritário!");
        assertEquals("[@Eduarda:19 @ ]", topic.toString(), "Sua topic está com impressão errada!");
    }

    // ---------- Subir ----------

    @Test
    @DisplayName("Subir: prioritário vai para assento prioritário livre")
    public void subirPrioritarioComVagaPrioritaria() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Marlus", 120);
        subirComSucesso(topic, "Eduarda", 19);
        Passageiro marlus = topic.getPassageiroAssentoPrioritario(0);
        Passageiro eduarda = topic.getPassageiroAssentoNormal(0);
        assertNotNull(marlus, "Falha na inserção!");
        assertEquals("Marlus", marlus.getNome(), "Passageiro prioritário não encontrado no assento prioritário");
        assertNotNull(eduarda, "Falha na inserção!");
        assertEquals("Eduarda", eduarda.getNome(), "Passageiro sem prioridade não encontrado no assento normal");
    }

    @Test
    @DisplayName("Subir: não prioritário vai para assento normal livre")
    public void subirSemPrioridadeComVagaNormal() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Eduarda", 19);
        subirComSucesso(topic, "Marlus", 120);
        Passageiro eduarda = topic.getPassageiroAssentoNormal(0);
        Passageiro marlus = topic.getPassageiroAssentoPrioritario(0);
        assertNotNull(eduarda, "Falha na inserção!");
        assertEquals("Eduarda", eduarda.getNome(), "Passageiro sem prioridade não encontrado no assento normal");
        assertNotNull(marlus, "Falha na inserção!");
        assertEquals("Marlus", marlus.getNome(), "Passageiro prioritário não encontrado no assento prioritário");
    }

    @Test
    @DisplayName("Subir: prioritário vai para assento normal quando prioritários estão ocupados")
    public void subirPrioritarioSemVagaPrioritaria() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Eduarda", 190);
        subirComSucesso(topic, "Guthyerri", 120);
        Passageiro guthyerri = topic.getPassageiroAssentoNormal(0);
        assertNotNull(guthyerri, "Passageiro não encontrado");
        assertEquals("Guthyerri", guthyerri.getNome(), "O idoso deveria ocupar o assento normal livre");
    }

    @Test
    @DisplayName("Subir: não prioritário vai para assento prioritário quando normais estão ocupados")
    public void subirSemPrioridadeSemVagaNormal() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Eduarda", 19);
        subirComSucesso(topic, "Guthyerri", 19);
        Passageiro guthyerri = topic.getPassageiroAssentoPrioritario(0);
        assertNotNull(guthyerri, "Passageiro não encontrado");
        assertEquals("Guthyerri", guthyerri.getNome(), "O jovem deveria ocupar o assento prioritário livre");
    }

    @Test
    @DisplayName("Subir: ocupa o primeiro assento livre, reaproveitando assentos esvaziados")
    public void subirOcupaPrimeiroAssentoLivre() {
        Topic topic = new Topic(6, 3);
        subirComSucesso(topic, "Ana", 20);
        subirComSucesso(topic, "Bia", 21);
        subirComSucesso(topic, "Caio", 22);
        subirComSucesso(topic, "Xico", 70);
        subirComSucesso(topic, "Yara", 71);
        subirComSucesso(topic, "Zeca", 72);
        assertTrue(topic.descer("Bia"), "Falha ao remover!");
        assertTrue(topic.descer("Yara"), "Falha ao remover!");

        subirComSucesso(topic, "Davi", 23);
        subirComSucesso(topic, "Wagner", 73);
        assertEquals("Davi", topic.getPassageiroAssentoNormal(1).getNome(), "O passageiro deveria ocupar o assento normal que ficou livre!");
        assertEquals("Wagner", topic.getPassageiroAssentoPrioritario(1).getNome(), "O passageiro deveria ocupar o assento prioritário que ficou livre!");
    }

    @Test
    @DisplayName("Subir: topic lotada recusa passageiro sem alterar os assentos")
    public void subirNaTopicLotada() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Marlus", 12);
        subirComSucesso(topic, "Guthyerri", 130);
        String antes = topic.toString();
        assertFalse(topic.subir(new Passageiro("Hermilson", 16)), "A topic lotou!");
        assertEquals(0, topic.getVagas(), "A topic lotada não deveria ter vagas!");
        assertEquals(antes, topic.toString(), "A topic não deveria mudar ao recusar um passageiro!");
    }

    @Test
    @DisplayName("Subir: recusa passageiro repetido em assento normal")
    public void subirPassageiroRepetido() {
        Topic topic = new Topic(3, 1);
        subirComSucesso(topic, "Marlus", 12);
        assertFalse(topic.subir(new Passageiro("Marlus", 12)), "O passageiro já estava na topic!");
        assertEquals(2, topic.getVagas(), "Passageiro repetido não deveria ocupar vaga!");
    }

    @Test
    @DisplayName("Subir: recusa passageiro repetido em assento prioritário")
    public void subirPassageiroRepetidoEmAssentoPrioritario() {
        Topic topic = new Topic(3, 1);
        subirComSucesso(topic, "Marlus", 80);
        assertFalse(topic.subir(new Passageiro("Marlus", 80)), "O passageiro já estava na topic!");
        assertEquals(2, topic.getVagas(), "Passageiro repetido não deveria ocupar vaga!");
    }

    // ---------- Descer ----------

    @Test
    @DisplayName("Descer: topic vazia não remove ninguém")
    public void descidaComTopicVazia() {
        Topic topic = new Topic(2, 1);
        assertFalse(topic.descer("Joaquim"), "Removeu alguém sendo que a topic está vazia!");
    }

    @Test
    @DisplayName("Descer: remove passageiro de assento normal")
    public void descidaDePassageiro() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Guthyerri", 19);
        assertTrue(topic.descer("Guthyerri"), "Falha ao remover!");
        assertNull(topic.getPassageiroAssentoNormal(0), "O passageiro não foi removido");
        assertEquals(2, topic.getVagas(), "Removeu uma posição do vetor, deveria somente atribuir null!");
    }

    @Test
    @DisplayName("Descer: remove passageiro de assento prioritário")
    public void descidaDePassageiroPrioritario() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Marlus", 90);
        assertTrue(topic.descer("Marlus"), "Falha ao remover passageiro do assento prioritário!");
        assertNull(topic.getPassageiroAssentoPrioritario(0), "O passageiro não foi removido");
        assertEquals(2, topic.getVagas(), "A quantidade de vagas está errada após a descida!");
    }

    @Test
    @DisplayName("Descer: os demais passageiros continuam nos mesmos assentos")
    public void descidaNaoDeslocaOsDemais() {
        Topic topic = new Topic(4, 1);
        subirComSucesso(topic, "Ana", 20);
        subirComSucesso(topic, "Bia", 21);
        subirComSucesso(topic, "Caio", 22);
        assertTrue(topic.descer("Ana"), "Falha ao remover!");
        assertNull(topic.getPassageiroAssentoNormal(0), "O assento de quem desceu deveria ficar vazio (null)!");
        assertEquals("Bia", topic.getPassageiroAssentoNormal(1).getNome(), "Os demais passageiros não devem mudar de assento!");
        assertEquals("Caio", topic.getPassageiroAssentoNormal(2).getNome(), "Os demais passageiros não devem mudar de assento!");
    }

    @Test
    @DisplayName("Descer: passageiro que não está na topic não é removido")
    public void descidaDePassageiroQueNaoSubiu() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, "Guthyerri", 19);
        assertFalse(topic.descer("Zé Adolfo"), "Removeu alguém que não estava na topic!");
        assertTrue(topic.descer("Guthyerri"), "Falha ao remover!");
        assertFalse(topic.descer("Guthyerri"), "Removeu um passageiro que já tinha descido!");
    }

    @Test
    @DisplayName("Nomes são comparados pelo conteúdo (equals), não pela referência (==)")
    public void nomesComparadosComEquals() {
        Topic topic = new Topic(2, 1);
        subirComSucesso(topic, new String("Marlus"), 30);
        assertFalse(topic.subir(new Passageiro(new String("Marlus"), 30)), "O passageiro já estava na topic! Use equals para comparar Strings.");
        assertTrue(topic.descer(new String("Marlus")), "Falha ao remover! Use equals para comparar Strings.");
    }

    // ---------- Mostrar ----------

    @Test
    @DisplayName("Vagas diminuem quando um passageiro sobe")
    public void mostrarVagas() {
        Topic topic = new Topic(5, 3);
        assertEquals(5, topic.getVagas(), "Quantidade de vagas disponíveis está errada!");
        subirComSucesso(topic, "Bode", 5);
        assertEquals(4, topic.getVagas(), "Quantidade de vagas disponíveis está errada!");
    }

    @Test
    @DisplayName("toString da topic vazia")
    public void mostrarTopicVazia() {
        Topic topic = new Topic(10, 5);
        assertEquals("[@ @ @ @ @ = = = = = ]", topic.toString(), "Sua topic está com impressão errada!");
    }

    @Test
    @DisplayName("toString da topic cheia")
    public void mostrarTopicCheia() {
        Topic topic = new Topic(3, 1);
        subirComSucesso(topic, "Marlus", 120);
        subirComSucesso(topic, "Eduarda", 19);
        subirComSucesso(topic, "Guthyerri", 19);
        assertEquals("[@Marlus:120 =Eduarda:19 =Guthyerri:19 ]", topic.toString(), "Sua topic está com impressão errada!");
    }

    @Test
    @DisplayName("toString com assentos vazios e ocupados misturados")
    public void mostrarTopicParcial() {
        Topic topic = new Topic(5, 2);
        subirComSucesso(topic, "Ana", 17);
        subirComSucesso(topic, "Bia", 18);
        subirComSucesso(topic, "Caio", 30);
        subirComSucesso(topic, "Davi", 20);
        assertTrue(topic.descer("Bia"), "Falha ao remover!");
        assertEquals("[@Davi:20 @ =Ana:17 = =Caio:30 ]", topic.toString(), "Sua topic está com impressão errada!");
    }
}
