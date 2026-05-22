package Problema1.persistencias;

import Problema1.data.Prenda;
import Problema1.data.enumerados.Genero;
import Problema1.data.enumerados.Temporada;
import Problema1.excepciones.ExcepcionDeCostoFueraDeLimite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class GestionPrendas {

    private Formatter output;
    private Scanner input;
    private String nombreArchivo;

    public GestionPrendas() {
        this.nombreArchivo = "prendas.txt";
    }

    public void guardaDatos(ArrayList<Prenda> prendas) throws FileNotFoundException {
        output = new Formatter(nombreArchivo);
        for (Prenda prenda : prendas)
            output.format("%s\n", prenda.toCSV());
        output.flush();
        output.close();
    }

    private Prenda procesaDatos(String linea) throws ExcepcionDeCostoFueraDeLimite {
        Scanner sc = new Scanner(linea).useDelimiter(",");
        int numeroPrenda  = sc.nextInt();
        String modelo     = sc.next();
        String tela       = sc.next();
        float costo       = sc.nextFloat();
        Genero genero     = Genero.valueOf(sc.next());
        Temporada temporada    = Temporada.valueOf(sc.next());
        float costoMaximo = sc.nextFloat();
        sc.close();
        return new Prenda(numeroPrenda, modelo, tela, costo, genero, temporada, costoMaximo);
    }

    public ArrayList<Prenda> leerDatos() throws FileNotFoundException {
        ArrayList<Prenda> prendas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        input = new Scanner(archivo);
        while (input.hasNextLine()) {
            String linea = input.nextLine();
            try {
                prendas.add(procesaDatos(linea));
            } catch (ExcepcionDeCostoFueraDeLimite e) {
                System.err.println("Error al leer prenda: " + e.getMessage());
            }
        }
        input.close();
        return prendas;
    }
}