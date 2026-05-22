package Problema1.data;

import Problema1.excepciones.ExcepcionDeNumeroDePiezasInvalido;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Comparator;

public class Lote implements Comparable<Lote>{
    private int numeroLote;
    private int numeroPieza;
    private LocalDate fechaFabricacion;
    private Prenda prendaLote;

    private static final int PIEZAS_MINIMAS = 50;
    private static final int PIEZAS_MAXIMAS = 350;

    public Lote(int numeroLote, int numeroPieza, LocalDate fechaFabricacion, Prenda prendaLote)
            throws ExcepcionDeNumeroDePiezasInvalido {
        if(numeroPieza < PIEZAS_MINIMAS || numeroPieza > PIEZAS_MAXIMAS)
            throw new ExcepcionDeNumeroDePiezasInvalido("El número de piezas debe estar entre 50 y 350");
        this.numeroLote = numeroLote;
        this.numeroPieza = numeroPieza;
        this.fechaFabricacion = fechaFabricacion;
        this.prendaLote = prendaLote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return numeroLote == lote.numeroLote &&
                numeroPieza == lote.numeroPieza &&
                Objects.equals(fechaFabricacion, lote.fechaFabricacion) &&
                Objects.equals(prendaLote, lote.prendaLote);
    }
    @Override
    public int hashCode() {
        int result = Integer.hashCode(numeroLote);
        result = 31 * result + Integer.hashCode(numeroPieza);
        result = 31 * result + Objects.hashCode(fechaFabricacion);
        result = 31 * result + Objects.hashCode(prendaLote);
        return result;
    }

    @Override
    public int compareTo(Lote otro){
        int r = 0;
        if((r = Integer.compare(this.numeroLote,otro.numeroLote))!=0)
            return r;
        if((r = Integer.compare(this.numeroPieza,otro.numeroPieza))!=0)
            return r;
        if((r = this.fechaFabricacion.compareTo(otro.fechaFabricacion))!=0)
            return r;
        return this.prendaLote.compareTo(otro.prendaLote);
    }

    public static class ComparadorPorPiezas implements Comparator<Lote> {
        @Override
        public int compare(Lote a, Lote b) {
            return Integer.compare(a.getNumeroPieza(), b.getNumeroPieza());
        }
    }
    public static class ComparadorPorFecha implements Comparator<Lote> {
        @Override
        public int compare(Lote a, Lote b) {
            return a.getFechaFabricacion().compareTo(b.getFechaFabricacion());
        }
    }

    public Prenda getPrendaLote() {return prendaLote;}
    public int getNumeroLote() {return numeroLote;}
    public int getNumeroPieza() {return numeroPieza;}
    public LocalDate getFechaFabricacion() {return fechaFabricacion;}


    public float getCalcularCostoProduccionLote(){
        float productoLote=0;
        productoLote = numeroPieza * prendaLote.getCostoProduccion();
        return productoLote;
    }

    public float getCalcularPrecioVentaPieza(){
        float costoProduccion = 0;
        costoProduccion = prendaLote.getCostoProduccion() * 1.15f;
        return costoProduccion;
    }

    public float getCalcularGananciaVentaLote(){
        float gananciaVenta = 0;
        float precioPrendaLote = prendaLote.getCostoProduccion() * 0.05f;
        gananciaVenta = precioPrendaLote * numeroPieza;
        return gananciaVenta;
    }

    public String toCSV() {
        return String.format("%d,%d,%s,%d",
                numeroLote, numeroPieza, fechaFabricacion,
                prendaLote.getNumeroPrenda());
    }

    @Override
    public String toString() {
        return "Lote{" +
                "numeroLote=" + numeroLote +
                ", numeroPieza=" + numeroPieza +
                ", fechaFabricacion=" + fechaFabricacion +
                ",\nprendaLote=" + prendaLote +
                '}';
    }
}