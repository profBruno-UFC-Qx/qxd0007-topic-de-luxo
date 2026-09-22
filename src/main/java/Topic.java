public class Topic {

    private Passageiro[] prioritarios, normais;

    public Topic(int capacidade, int qtdPrioritatios) {
        prioritarios = new Passageiro[qtdPrioritatios];
        normais = new Passageiro[capacidade - qtdPrioritatios];
    }

    public int getNumeroAssentosPrioritarios() {
        return prioritarios.length;
    }

    public int getNumeroAssentosNormais() {
        return normais.length;
    }

    public Passageiro getPassageiroAssentoNormal(int lugar) {
        return normais[lugar];
    }

    public Passageiro getPassageiroAssentoPrioritario(int lugar) {
        return prioritarios[lugar];
    }

    public int getVagas() {
        return contar(prioritarios) + contar(normais);
    }

    private int contar(Passageiro[] v) {
        int n = 0;
        for (Passageiro p : v)
            if (p == null) n++;
        return n;
    }

    private int buscar(Passageiro[] v, String nome) {
        for (int i = 0; i < v.length; i++)
            if (v[i] != null && v[i].getNome().equals(nome))
                return i;
        return -1;
    }

    private int livre(Passageiro[] v) {
        for (int i = 0; i < v.length; i++)
            if (v[i] == null) return i;
        return -1;
    }

    public boolean subir(Passageiro p) {
        if (buscar(prioritarios, p.getNome()) != -1 ||
            buscar(normais, p.getNome()) != -1 || getVagas() == 0)
            return false;

        Passageiro[] v = p.ePrioritario() ? prioritarios : normais;
        Passageiro[] outro = p.ePrioritario() ? normais : prioritarios;

        int i = livre(v);
        if (i == -1) {
            i = livre(outro);
            outro[i] = p;
        } else {
            v[i] = p;
        }
        return true;
    }

    public boolean descer(String nome) {
        int i = buscar(prioritarios, nome);
        if (i != -1) {
            prioritarios[i] = null;
            return true;
        }
        i = buscar(normais, nome);
        if (i != -1) {
            normais[i] = null;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "[";
        for (Passageiro p : prioritarios)
            s += "@" + (p == null ? "" : p) + " ";
        for (Passageiro p : normais)
            s += "=" + (p == null ? "" : p) + " ";
        return s + "]";
    }
}
