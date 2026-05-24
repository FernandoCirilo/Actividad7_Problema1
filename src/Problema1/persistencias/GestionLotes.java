package Problema1.persistencias;

import Problema1.data.Lote;
import Problema1.data.Prenda;
import Problema1.excepciones.ExcepcionDeNumeroDePiezasInvalido;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class GestionLotes {

    private Formatter output;
    private Scanner input;
    private String nombreArchivo;

    public GestionLotes() {
        this.nombreArchivo = "lotes.txt";
    }

    public void guardaDatos(ArrayList<Lote> lotes) throws FileNotFoundException {
        output = new Formatter(nombreArchivo);
        for (Lote lote : lotes)
            output.format("%s\n", lote.toCSV());
        output.flush();
        output.close();
    }

    private Lote procesaDatos(String linea, ArrayList<Prenda> prendas)
            throws ExcepcionDeNumeroDePiezasInvalido {
        Scanner sc = new Scanner(linea).useDelimiter(",");
        int numeroLote = sc.nextInt();
        int numeroPieza = sc.nextInt();
        LocalDate fecha = LocalDate.parse(sc.next());
        int numeroPrenda = sc.nextInt();
        sc.close();

        Prenda prenda = null;
        for (Prenda p : prendas)
            if (p.getNumeroPrenda() == numeroPrenda) {
                prenda = p;
                break;
            }

        return new Lote(numeroLote, numeroPieza, fecha, prenda);
    }

    // recibe las prendas ya cargadas para poder vincularlas
    public ArrayList<Lote> leerDatos(ArrayList<Prenda> prendas) throws FileNotFoundException {
        ArrayList<Lote> lotes = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        input = new Scanner(archivo);
        while (input.hasNextLine()) {
            String linea = input.nextLine();
            try {
                lotes.add(procesaDatos(linea, prendas));
            } catch (ExcepcionDeNumeroDePiezasInvalido e) {
                System.err.println("Error al leer lote: " + e.getMessage());
            }
        }
        input.close();
        return lotes;
    }
}