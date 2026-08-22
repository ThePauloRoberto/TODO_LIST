package services.utils;

import domain.Tarefa;
import domain.utils.Status;

import java.util.ArrayList;
import java.util.List;

public class MockGenerator {

    public static List<Tarefa> Start(){


        List<Tarefa> tarefas = new ArrayList<>();

        // Criação de uma lista de tarefas (tasks)
        Tarefa tarefa1 = new Tarefa(1, "Fazer CRD", "Fazer o back-end do to-do list e adicionar as funcionalidades CRD", "12-12-2026", "Back-end", Status.DOING, 1);
        Tarefa tarefa2 = new Tarefa(2, "Clase da Tarefa", "Criar uma classe com os atributos da tarefa", "22-08-2026", "Back-end", Status.DONE, 2);
        Tarefa tarefa3 = new Tarefa(3, "Fazer tela dos cards das tarefas", "Usar pre modelos prontos de cards e customizar", "12-12-2026", "Front-end", Status.TO_DO, 3);
        Tarefa tarefa4 = new Tarefa(4, "Banco de dados", "Criar o banco de dados da terefa e seus relacionamentos", "12-12-2026", "BD", Status.TO_DO, 4);
        Tarefa tarefa5 = new Tarefa(5, "Fazer menu crud", "Criar uma aba para o CRUD ", "08-09-2026", "Front-end", Status.TO_DO, 5);

        tarefas.add(tarefa1);
        tarefas.add(tarefa2);
        tarefas.add(tarefa3);
        tarefas.add(tarefa4);
        tarefas.add(tarefa5);

        return tarefas;
    }
}
