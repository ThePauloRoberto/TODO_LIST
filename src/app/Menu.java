package app;

import com.google.gson.Gson;
import domain.Tarefa;
import domain.utils.Status;
import services.AlarmTarefa;
import services.GsonService;
import services.utils.MockGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Menu {
    final Scanner sc;
    List<Tarefa> tarefasComAlarmes;
    List<Tarefa> tarefas;

    public Menu() {
         sc = new Scanner(System.in);
         tarefasComAlarmes = new ArrayList<>();
         tarefas =  new ArrayList<>();
    }

    public void Start(){

        tarefas = MockGenerator.Start();
        GsonService.CreateSaves(tarefas);

        Function<Tarefa, Integer> extraiPrioridade = Tarefa::getPrioridade; // pega a prioridade por tarefa -> Pode virar um função da classe tarefa
        Comparator<Tarefa> comparePrioridade = Comparator.comparing(extraiPrioridade); // Compara as prioridades para ordenação -> Outra função da classe tarefa

        int op ;

        System.out.println("----------- SEJA BEM-VINDO AO TO-DO LIST COROTINHO -------------");
        do {

            tarefas.sort(comparePrioridade); // usa a função de comparação
            System.out.println("Escolha uma das opções \n 1- Criar tarefa \n 2- Ver lista de tarefa \n 3- Apagar tarefa \n 4- Sair");
            System.out.println("Digite a opção: ");

            op = sc.nextInt();

            if (op == 1) // criar tarefa
            {
                CriarTarefa();
            }

            else if (op == 2) // Exibir tarefas
            {
              ListTarefas();
            }

            else if (op == 3) // Todas as tarefas listadas
            {
                ListAllTarefas();
            }
            else{
                System.out.println("Até mais");
            }
        }
        while ((op > 0 && op < 4));
        AlarmTarefa.AcionadorDeTarefas(tarefasComAlarmes);

    }

    public Tarefa CriarTarefa() {
        Tarefa tarefa = new Tarefa();

        tarefa.setId(tarefas.size() + 1);

        System.out.println("Nome da tarefa: ");
        tarefa.setNome(sc.next());

        System.out.println("Descrição da tarefa: ");
        tarefa.setDescricao(sc.next());

        String lembrarTarefa;
        System.out.println("Data de prioridade da tarefa: \n O formato da data 'dd-mm-yyyy'");
        tarefa.setData_de_prioridade(sc.next());

        System.out.println("Deseja se lembrado desta tarefas na data? \n [S/N]");
        lembrarTarefa = sc.next().toUpperCase();
        switch (lembrarTarefa) {
            case "S" -> tarefa.setAlarmeAtivo(true);
            case "N" -> tarefa.setAlarmeAtivo(false);
            case "SIM" -> tarefa.setAlarmeAtivo(true);
            case "NAO" -> tarefa.setAlarmeAtivo(false);
            default -> tarefa.setAlarmeAtivo(false);
        }


        System.out.println("Categoria da tarefa: ");
        tarefa.setCategoria(sc.next());

        System.out.println("Status da tarefa: \n 1-TO-DO \n 2-DOING \n 3-DONE");
        String opStatus = sc.next();
        Status status;
        switch (opStatus) {
            case "1" -> status = Status.TO_DO;
            case "2" -> status = Status.DOING;
            case "3" -> status = Status.DONE;
            default -> status = Status.TO_DO;
        }
        tarefa.setStatus(status);

        System.out.println("Prioridade da tarefa 1 - 5");
        int opPrior = sc.nextInt();
        if (opPrior < 0 || opPrior > 5) {

            if (opPrior > 5) {
                opPrior = 5;
            } else {
                opPrior = 0;
            }
        }
        tarefa.setPrioridade(opPrior);

        try {
            if (tarefa.getAlarmeAtivo()) {
                tarefasComAlarmes.add(tarefa);
            }
            tarefas.add(tarefa);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao criar tarefa");
        }


        AlarmTarefa.AcionadorDeTarefas(tarefasComAlarmes);
        GsonService.CreateSaves(tarefa);

        return tarefa;
    }

    public void ListTarefas(){
        System.out.println(" 1- Todas as tarefas \n 2- listar por categoria \n 3- listar por status");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1 -> {
                GsonService.ReadSaves();
            }
            case 2 -> {
                System.out.println("Todas as categorias:");

               List<String> categorias =  tarefas.stream().map(Tarefa::getCategoria).distinct().collect(Collectors.toList());
               categorias.forEach(System.out::println);

                System.out.println("Digite qual cateogria deseja filtar:");
                String opCategoria = sc.next();
                tarefas.stream().filter(value -> value.getCategoria().equals(opCategoria)).forEach(Tarefa::read);
            }
            case 3 -> {
                System.out.println("Status:");

                List<Status> categorias = tarefas.stream().map(Tarefa::getStatus).distinct().collect(Collectors.toList());
                categorias.forEach(System.out::println);

                System.out.println("Digite qual Status deseja filtar:");
                String opStatus = sc.next();
                tarefas.stream().filter(value -> value.getStatus().equals(Status.valueOf(opStatus))).forEach(Tarefa::read);
            }
            default -> {
                for (Tarefa tarefa : tarefas) {
                    tarefa.read();
                }
            }
        }
    }

    public void ListAllTarefas(){
        System.out.println("Todas as Tarefas: ");
        for (Tarefa tarefa : tarefas){tarefa.read();}
        System.out.println("Digite o id da tarefa que deseja excluir: ");
        int opDelete = sc.nextInt();

        tarefas.removeIf( tarefa -> tarefa.getId() == opDelete );
    }
}
