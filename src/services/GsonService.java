package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Tarefa;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GsonService {
    public static Gson gson = new GsonBuilder().
            registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public GsonService(){

    }

    public static List<Tarefa> ReadSaves(){
        FileReader file = null;
        BufferedReader br = null;
        try {
            file = new FileReader("C:\\Users\\paulo\\OneDrive\\Documentos\\projetos\\TODO_LIST\\Saves\\TarefasSalvas.json");
            br = new BufferedReader(file);

            //Converte String JSON para objeto Java
            List<Tarefa> list = gson.fromJson(br, new TypeToken<List<Tarefa>>(){}.getType());
            if (list != null) {
                for (Tarefa tarefa : list) {
                    System.out.println(tarefa);
                }

                return list;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null){
                    br.close();
                }
                if(br != null){
                    br.close();
                }
            }catch (IOException e ){
                e.printStackTrace();
            }

        }
        return List.of();
    }

    public static void CreateSaves(List<Tarefa> tarefas){
        try (Writer writer = new FileWriter("C:\\Users\\paulo\\OneDrive\\Documentos\\projetos\\TODO_LIST\\Saves\\TarefasSalvas.json")) {


            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            gson.toJson(tarefas, writer);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    } // no futuro
    public static void CreateSaves(Tarefa tarefa) {
        List<Tarefa> list = ReadSaves();

        try (Writer writer = new FileWriter("C:\\Users\\paulo\\OneDrive\\Documentos\\projetos\\TODO_LIST\\Saves\\TarefasSalvas.json")) {

            list.add(tarefa);

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            gson.toJson(list, writer);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void DeleteSave(int id){
        List<Tarefa> list = ReadSaves();

        try (Writer writer = new FileWriter("C:\\Users\\paulo\\OneDrive\\Documentos\\projetos\\TODO_LIST\\Saves\\TarefasSalvas.json")) {

            list.removeIf(tarefa -> tarefa.getId() == id);

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            gson.toJson(list, writer);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
