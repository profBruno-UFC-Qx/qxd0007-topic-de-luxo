import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Topic {

    public Topic(int capacidade, int qtdPrioritatios) {
    }

    public List<Passageiro> getAssentosPrioritarios() {
        return null;
    }
    public List<Passageiro> getAssentosNormais() {
        return null;
    }

    public int getVagas() {
        return -1;
    }

    public boolean subir(Passageiro passageiro) {
        return false;
    }
    public boolean descer(String nome) {
        return true;
    }
}