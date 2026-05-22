package Problema1.ui;

import Problema1.data.Lote;
import Problema1.data.Prenda;

import java.util.ArrayList;

public class Visualizacion {

    // ─── PRENDA ──────────────────────────────────────────────────

    // equivalente a visualizaAlumno() del base
    public void visualizaPrenda(Prenda prenda) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("%04d  ", prenda.getNumeroPrenda()));
        buffer.append(String.format("%-15s ", prenda.getModelo()));
        buffer.append(String.format("%-12s ", prenda.getTela()));
        buffer.append(String.format("%8.2f  ", prenda.getCostoProduccion()));
        buffer.append(String.format("%-10s ", prenda.getGenero()));
        buffer.append(String.format("%-10s ", prenda.getTemporada()));
        System.out.println(buffer.toString());
    }

    // equivalente a visualizaEncabezado() del base
    public void encabezadoPrendas() {
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.printf("%-4s  %-15s %-12s %-8s  %-10s %-10s%n",
                "No", "Modelo", "Tela", "Costo", "Género", "Temporada");
        System.out.println("─────────────────────────────────────────────────────────────");
    }

    // equivalente a visualizaTodos() del base
    public void visualizaTodosPrendas(ArrayList<Prenda> prendas) {
        encabezadoPrendas();
        for (Prenda prenda : prendas)
            visualizaPrenda(prenda);
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Total de prendas: " + prendas.size());
    }

    // ─── LOTE ────────────────────────────────────────────────────

    public void visualizaLote(Lote lote) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("%04d  ", lote.getNumeroLote()));
        buffer.append(String.format("%04d  ", lote.getNumeroPieza()));
        buffer.append(String.format("%-12s ", lote.getFechaFabricacion()));
        buffer.append(String.format("%-15s ", lote.getPrendaLote().getModelo()));
        buffer.append(String.format("%10.2f  ", lote.getCalcularCostoProduccionLote()));
        buffer.append(String.format("%10.2f  ", lote.getCalcularPrecioVentaPieza()));
        buffer.append(String.format("%10.2f  ", lote.getCalcularGananciaVentaLote()));
        System.out.println(buffer.toString());
    }

    public void encabezadoLotes() {
        System.out.println("──────────────────────────────────────────────────────────────────────────");
        System.out.printf("%-4s  %-4s  %-12s %-15s %-10s %-10s %-10s%n",
                "No", "Pzas", "Fecha", "Modelo Prenda",
                "Costo Lote", "P.Venta", "Ganancia");
        System.out.println("──────────────────────────────────────────────────────────────────────────");
    }

    public void visualizaTodosLotes(ArrayList<Lote> lotes) {
        encabezadoLotes();
        for (Lote lote : lotes)
            visualizaLote(lote);
        System.out.println("──────────────────────────────────────────────────────────────────────────");
        System.out.println("Total de lotes: " + lotes.size());
    }

    // ─── LOTES DE UNA PRENDA ESPECÍFICA ──────────────────────────

    // nuevo respecto al base — muestra solo los lotes de una prenda
    public void visualizaLotesDePrenda(Prenda prenda, ArrayList<Lote> lotes) {
        System.out.println("\nLotes de la prenda: "
                + prenda.getNumeroPrenda()
                + " — " + prenda.getModelo());
        encabezadoLotes();
        int total = 0;
        for (Lote lote : lotes) {
            visualizaLote(lote);
            total++;
        }
        System.out.println("──────────────────────────────────────────────────────────────────────────");
        System.out.println("Total de lotes: " + total);
    }
}