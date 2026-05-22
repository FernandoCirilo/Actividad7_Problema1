package Problema1.data;
import Problema1.excepciones.ExcepcionDeCostoFueraDeLimite;
import Problema1.data.enumerados.Genero;
import Problema1.data.enumerados.Temporada;

import java.util.Objects;
import java.util.Comparator;

public class Prenda implements Comparable<Prenda> {
    private String modelo;
    private String tela;
    private float costoProduccion;
    private Genero genero;
    private Temporada temporada;
    private float costoMaximo;

    private int numeroPrenda;

    public Prenda(int numeroPrenda, String modelo, String tela, float costoProduccion, Genero genero, Temporada temporada, float costoMaximo)
            throws ExcepcionDeCostoFueraDeLimite {

        if(costoProduccion > costoMaximo)
            throw new ExcepcionDeCostoFueraDeLimite("El costo supera el límite de " + costoMaximo);

        this.costoMaximo = costoMaximo;
        this.modelo = modelo;
        this.tela = tela;
        this.costoProduccion = costoProduccion;
        this.genero = genero;
        this.temporada = temporada;
        this.numeroPrenda = numeroPrenda;
    }
    public String getModelo() {return modelo;}
    public String getTela() {return tela;}
    public float getCostoProduccion() {return costoProduccion;}
    public Genero getGenero() {return genero;}
    public Temporada getTemporada() {return temporada;}
    public int getNumeroPrenda(){return numeroPrenda;}

    public void setModelo(String modelo)         { this.modelo = modelo; }
    public void setTela(String tela)             { this.tela = tela; }
    public void setGenero(Genero genero)         { this.genero = genero; }
    public void setTemporada(Temporada temporada){ this.temporada = temporada; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Prenda prenda = (Prenda) o;
        return Float.compare(costoProduccion, prenda.costoProduccion) == 0 &&
                Objects.equals(modelo, prenda.modelo) &&
                Objects.equals(tela, prenda.tela) &&
                Objects.equals(genero, prenda.genero) &&
                Objects.equals(temporada, prenda.temporada);
    }
    @Override
    public int hashCode() {
        int result = Objects.hashCode(modelo);
        result = 31 * result + Objects.hashCode(tela);
        result = 31 * result + Float.hashCode(costoProduccion);
        result = 31 * result + Objects.hashCode(genero);
        result = 31 * result + Objects.hashCode(temporada);
        return result;
    }

    @Override
    public int compareTo(Prenda otra) {
        int r=0;
        if((r = this.modelo.compareTo(otra.modelo))!=0)
            return r;
        if ((r = this.tela.compareTo(otra.tela))!=0)
            return r;
        if((r = Float.compare(this.costoProduccion, otra.costoProduccion))!=0)
            return r;
        if((r = this.genero.compareTo(otra.genero)) != 0)
            return r;
        return this.temporada.compareTo(otra.temporada);
    }


    public static class ComparadorPrendaPorCosto implements Comparator<Prenda> {
        @Override
        public int compare(Prenda a, Prenda b) {
            return Float.compare(a.getCostoProduccion(), b.getCostoProduccion());
        }
    }
    public static class ComparadorPrendaTela implements Comparator<Prenda> {
        @Override
        public int compare(Prenda a, Prenda b){
            return a.getTela().compareTo(b.getTela());
        }
    }

    public Object toCSV() {
        return String.format("%d,%s,%s,%.2f,%s,%s,%.2f",
                numeroPrenda, modelo, tela, costoProduccion,
                genero, temporada, costoMaximo);
    }
    /*public String toCSV{
        return String.format("%d,%s,%s,%.2f,%s,%s,%.2f",
                numeroPrenda, modelo, tela, costoProduccion,
                genero, temporada, costoMaximo);
    }*/

    @Override
    public String toString() {
        return "Prenda{" +
                "modelo='" + modelo +
                ", tela='" + tela +
                ", costoProduccion=" + costoProduccion +
                ", genero='" + genero +
                ", temporada='" + temporada +
                ", costoMaximo=" + costoMaximo +
                '}';
    }
}