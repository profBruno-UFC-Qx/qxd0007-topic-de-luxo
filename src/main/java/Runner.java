public class Runner {

    public static void main(final String[] args) {

        Topic topic = new Topic(5, 2);
        System.out.println(topic); // [@ @ = = = ]

        Passageiro passageiro = new Passageiro("davi", 17);
        topic.subir(passageiro);
        System.out.println(topic); // [@ @ =davi:17 = = ]

        passageiro = new Passageiro("joao", 103);
        topic.subir(passageiro);
        System.out.println(topic); // [@joao:103 @ =davi:17 = = ]

        passageiro = new Passageiro("ana", 35);
        topic.subir(passageiro);
        System.out.println(topic); // [@joao:103 @ =davi:17 =ana:35 = ]

        passageiro = new Passageiro("rex", 20);
        topic.subir(passageiro);
        passageiro = new Passageiro("bia", 16); // assentos normais cheios: vai para o prioritário
        topic.subir(passageiro);
        System.out.println(topic); // [@joao:103 @bia:16 =davi:17 =ana:35 =rex:20 ]

        topic.descer("davi");
        System.out.println(topic); // [@joao:103 @bia:16 = =ana:35 =rex:20 ]

        passageiro = new Passageiro("aragao", 96); // prioritários cheios: vai para o normal
        topic.subir(passageiro);
        System.out.println(topic); // [@joao:103 @bia:16 =aragao:96 =ana:35 =rex:20 ]

        passageiro = new Passageiro("lucas", 23);
        if (!topic.subir(passageiro)) {
            System.out.println("Topic lotada"); // Topic lotada
        }

        if (!topic.descer("marcelo")) {
            System.out.println("Passageiro não está na topic"); // Passageiro não está na topic
        }

        topic.descer("ana");
        passageiro = new Passageiro("bia", 16);
        if (!topic.subir(passageiro)) {
            System.out.println("Passageiro já está na topic"); // Passageiro já está na topic
        }
        System.out.println(topic); // [@joao:103 @bia:16 =aragao:96 = =rex:20 ]
        System.out.println(topic.getVagas()); // 1
    }
}
