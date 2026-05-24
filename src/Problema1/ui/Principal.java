package Problema1.ui;

import Problema1.coleccion.Fabrica;
import Problema1.data.Lote;
import Problema1.data.Prenda;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    private Validacion    validacion;
    private Visualizacion visualizacion;
    private Fabrica       fabrica;
    private Scanner       input;
    private String        menu;

    public Principal() {
        input         = new Scanner(System.in);
        validacion    = new Validacion(input);
        visualizacion = new Visualizacion();
        fabrica       = new Fabrica("Textiles");
        creaMenu();
    }

    private void creaMenu() {
        menu  = "\n╔══════════════════════════════╗\n";
        menu += "║      FÁBRICA DE ROPA         ║\n";
        menu += "╠══════════════════════════════╣\n";
        menu += "║  PRENDAS                     ║\n";
        menu += "║  1.- Agregar prenda          ║\n";
        menu += "║  2.- Eliminar prenda         ║\n";
        menu += "║  3.- Modificar prenda        ║\n";
        menu += "║  4.- Consultar prenda        ║\n";
        menu += "║  5.- Listar prendas          ║\n";
        menu += "╠══════════════════════════════╣\n";
        menu += "║  LOTES                       ║\n";
        menu += "║  6.- Agregar lote            ║\n";
        menu += "║  7.- Eliminar lote           ║\n";
        menu += "║  8.- Listar lotes            ║\n";
        menu += "╠══════════════════════════════╣\n";
        menu += "║  9.- Salir                   ║\n";
        menu += "╚══════════════════════════════╝\n";
        menu += "Proporciona opción [1..9]: ";
    }

    private byte leerOpcion() {
        return (byte) validacion.leerLong(menu, 1, 9, "Opción inválida!!");
    }

    private void agregarPrenda() {
        Prenda prenda = validacion.leerPrenda();
        if (prenda == null) return;
        try {
            fabrica.agregaPrenda(prenda);
            System.out.println("Prenda agregada correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void eliminarPrenda() {
        int numeroPrenda = validacion.leerNumeroPrenda();
        Prenda prenda = fabrica.getPrenda(numeroPrenda);

        if (prenda == null) {
            System.err.println("La prenda no existe!!");
            return;
        }

        visualizacion.encabezadoPrendas();
        visualizacion.visualizaPrenda(prenda);

        // muestra los lotes que se eliminarán
        ArrayList<Lote> lotesAsociados = fabrica.getLotesDePrenda(numeroPrenda);
        if (!lotesAsociados.isEmpty()) {
            System.out.println("\nATENCIÓN — Se eliminarán también estos lotes:");
            visualizacion.visualizaLotesDePrenda(prenda, lotesAsociados);
        } else {
            System.out.println("Esta prenda no tiene lotes asociados.");
        }

        // confirmación
        boolean confirma = validacion.leerBoolean(
                "\n¿Confirmar eliminación? [Si/No]: ",
                "Si", "No", "Escribe Si o No!!");

        if (confirma) {
            try {
                fabrica.eliminaPrenda(prenda);
                System.out.println("Prenda y sus lotes eliminados correctamente.");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private void modificarPrenda() {
        int numeroPrenda = validacion.leerNumeroPrenda();
        Prenda prenda = fabrica.getPrenda(numeroPrenda);

        if (prenda == null) {
            System.err.println("La prenda no existe!!");
            return;
        }

        visualizacion.encabezadoPrendas();
        visualizacion.visualizaPrenda(prenda);

        String menuModificacion  = "\nMenú de modificación\n";
        menuModificacion += "1.- Modificar modelo\n";
        menuModificacion += "2.- Modificar tela\n";
        menuModificacion += "3.- Modificar género\n";
        menuModificacion += "4.- Modificar temporada\n";
        menuModificacion += "Indica opción [1..4]: ";

        byte opcion = (byte) validacion.leerLong(menuModificacion, 1, 4, "Opción inválida!!");

        switch (opcion) {
            case 1: prenda.setModelo(validacion.leerModelo());       break;
            case 2: prenda.setTela(validacion.leerTela());           break;
            case 3: prenda.setGenero(validacion.leerGenero());       break;
            case 4: prenda.setTemporada(validacion.leerTemporada()); break;
        }
        System.out.println("Prenda modificada correctamente.");
    }

    // muestra la prenda y todos sus lotes
    private void consultarPrenda() {
        int numeroPrenda = validacion.leerNumeroPrenda();
        Prenda prenda    = fabrica.getPrenda(numeroPrenda);

        if (prenda == null) {
            System.err.println("La prenda no existe!!");
            return;
        }

        visualizacion.encabezadoPrendas();
        visualizacion.visualizaPrenda(prenda);

        ArrayList<Lote> lotesAsociados = fabrica.getLotesDePrenda(numeroPrenda);
        if (!lotesAsociados.isEmpty())
            visualizacion.visualizaLotesDePrenda(prenda, lotesAsociados);
        else
            System.out.println("Esta prenda no tiene lotes asociados.");
    }

    private void listarPrendas() {
        if (fabrica.cantidadDePrendas() == 0) {
            System.out.println("No hay prendas registradas.");
            return;
        }
        visualizacion.visualizaTodosPrendas(fabrica.getPrendas());
    }

    private void agregarLote() {
        Lote lote = validacion.leerLote(fabrica);
        if (lote == null) return;
        try {
            fabrica.agregaLote(lote);
            System.out.println("Lote agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void eliminarLote() {
        int numeroLote = validacion.leerNumeroLote();
        Lote lote      = fabrica.getLote(numeroLote);

        if (lote == null) {
            System.err.println("El lote no existe!!");
            return;
        }

        visualizacion.encabezadoLotes();
        visualizacion.visualizaLote(lote);

        boolean confirma = validacion.leerBoolean(
                "\n¿Confirmar eliminación? [Si/No]: ",
                "Si", "No", "Escribe Si o No!!");

        if (confirma) {
            try {
                fabrica.eliminaLote(lote);
                System.out.println("Lote eliminado correctamente.");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private void listarLotes() {
        if (fabrica.cantidadDeLotes() == 0) {
            System.out.println("No hay lotes registrados.");
            return;
        }
        visualizacion.visualizaTodosLotes(fabrica.getLotes());
    }

    public void run() {
        byte opcion;
        do {
            opcion = leerOpcion();
            switch (opcion) {
                case 1: agregarPrenda();   break;
                case 2: eliminarPrenda();  break;
                case 3: modificarPrenda(); break;
                case 4: consultarPrenda(); break;
                case 5: listarPrendas();   break;
                case 6: agregarLote();     break;
                case 7: eliminarLote();    break;
                case 8: listarLotes();     break;
            }
        } while (opcion != 9);

        // guarda al salir
        try {
            fabrica.salvarDatos();
            System.out.println("Datos guardados correctamente.");
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }
}