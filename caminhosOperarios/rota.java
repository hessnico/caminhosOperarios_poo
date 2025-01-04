package caminhosOperarios;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a route with a set of locations, a description, and functionality to manage the route.
 *
 * Input Parameters:
 *    id: int
 *        Unique identifier for the route.
 *    nome: String
 *        Name of the route.
 *    rotaCompleta: String
 *        Full URL of the route's path.
 *
 * Methods:
 *    verDescricao: void
 *        Prints the description of the route to the console.
 *
 *    consultaLocais: void
 *        Prints the names and descriptions of the locations associated with the route.
 *
 *    getLocal: List<local>
 *        Returns the list of locations (local) associated with the route.
 *
 *    adicionarLocal: void
 *        Adds a new location to the route.
 *        If an error occurs, it prints an error message.
 *
 *    getNome: String
 *        Returns the name of the route.
 *
 *    getId: int
 *        Returns the ID of the route.
 *
 *    getRotaCompleta: String
 *        Returns the full route URL.
 *
 *    registerVisita: void
 *        Increments the count of visits to the route.
 *
 * Static Attributes:
 *    visitas: int
 *        Tracks the number of times the route has been visited across all instances.

 */

public class rota {
    private int id;
    private String nome;
    private String descricao;
    private ArrayList<caminhosOperarios.local> local;
    private String rotaCompleta;
    private static int visitas = 0;

    public void verDescricao() {
        System.out.printf("\nDescriçao da rota: %s\n", descricao);
    }

    public void consultaLocais() {
        System.out.printf("\nLocais da rota %s", nome);
        for (caminhosOperarios.local local : this.local) {
            System.out.println("    Nome: " + local.getNome());
            System.out.println("    Descrição: " + local.getDescricao());
        }
    }

    public ArrayList<caminhosOperarios.local> getLocal() {
        return local;
    }

    public void adicionarLocal(caminhosOperarios.local local) {
        try {
            this.local.add(local);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar local no trajeto da rota");
            System.out.println(e);
        }
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getRotaCompleta() {
        return rotaCompleta;
    }

    public void registerVisita() {
        visitas++;
    }

    public rota(int id, String nome, String rotaCompleta) {
        this.id = id;
        this.nome = nome;
        this.rotaCompleta = rotaCompleta;
        this.local = new ArrayList<>();
    }
}
