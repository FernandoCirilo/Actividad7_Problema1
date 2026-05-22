package Problema1.coleccion;

import Problema1.data.Lote;
import Problema1.data.Prenda;
import Problema1.persistencias.GestionLotes;
import Problema1.persistencias.GestionPrendas;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Fabrica {

    private String nombre;
    private ArrayList<Prenda> prendas;
    private ArrayList<Lote>   lotes;
    private GestionPrendas    gp;
    private GestionLotes      gl;

    public Fabrica(String nombre) {
        this.nombre  = nombre;
        this.gp      = new GestionPrendas();
        this.gl      = new GestionLotes();
        recuperarDatos();
    }

    // ─── PERSISTENCIA ────────────────────────────────────────────

    // igual que ListaAlumnos — primero prendas, luego lotes
    private void recuperarDatos() {
        try {
            prendas = gp.leerDatos();
            lotes   = gl.leerDatos(prendas);  // necesita prendas ya cargadas
        } catch (FileNotFoundException e) {
            prendas = new ArrayList<>();
            lotes   = new ArrayList<>();
        }
    }

    // guarda ambos archivos
    public void salvarDatos() throws FileNotFoundException {
        gp.guardaDatos(prendas);
        gl.guardaDatos(lotes);
    }

    // ─── VERIFICACIONES ──────────────────────────────────────────

    // equivalente a existe(Alumno) de ListaAlumnos
    public boolean existePrenda(Prenda prenda) {
        for (Prenda p : prendas)
            if (p.getNumeroPrenda() == prenda.getNumeroPrenda())
                return true;
        return false;
    }

    public boolean existePrenda(int numeroPrenda) {
        for (Prenda p : prendas)
            if (p.getNumeroPrenda() == numeroPrenda)
                return true;
        return false;
    }

    public boolean existeLote(Lote lote) {
        for (Lote l : lotes)
            if (l.getNumeroLote() == lote.getNumeroLote())
                return true;
        return false;
    }

    // ─── GETTERS ─────────────────────────────────────────────────

    // equivalente a getAlumno(long) de ListaAlumnos
    public Prenda getPrenda(int numeroPrenda) {
        for (Prenda p : prendas)
            if (p.getNumeroPrenda() == numeroPrenda)
                return p;
        return null;
    }

    public Lote getLote(int numeroLote) {
        for (Lote l : lotes)
            if (l.getNumeroLote() == numeroLote)
                return l;
        return null;
    }

    public ArrayList<Prenda> getPrendas() { return prendas; }
    public ArrayList<Lote>   getLotes()   { return lotes;   }

    // lotes que pertenecen a una prenda específica
    public ArrayList<Lote> getLotesDePrenda(int numeroPrenda) {
        ArrayList<Lote> resultado = new ArrayList<>();
        for (Lote l : lotes)
            if (l.getPrendaLote().getNumeroPrenda() == numeroPrenda)
                resultado.add(l);
        return resultado;
    }

    // ─── AGREGAR ─────────────────────────────────────────────────

    // equivalente a addAlumno() de ListaAlumnos
    public void agregaPrenda(Prenda prenda) {
        if (prenda == null)
            throw new IllegalArgumentException("La prenda es inválida!!");
        if (existePrenda(prenda))
            throw new IllegalArgumentException("La prenda ya existe!!");
        prendas.add(prenda);
    }

    // el lote solo se agrega si su prenda ya existe — regla de negocio clave
    public void agregaLote(Lote lote) {
        if (lote == null)
            throw new IllegalArgumentException("El lote es inválido!!");
        if (existeLote(lote))
            throw new IllegalArgumentException("El lote ya existe!!");
        if (!existePrenda(lote.getPrendaLote()))
            throw new IllegalArgumentException("La prenda del lote no existe!!");
        lotes.add(lote);
    }

    // ─── ELIMINAR ────────────────────────────────────────────────

    // equivalente a deleteAlumno() de ListaAlumnos
    // pero con cascada — elimina primero todos los lotes de la prenda
    public void eliminaPrenda(Prenda prenda) {
        if (!existePrenda(prenda))
            throw new IllegalArgumentException("La prenda no existe!!");
        // cascada — igual que la regla del enunciado
        lotes.removeIf(l -> l.getPrendaLote().getNumeroPrenda()
                == prenda.getNumeroPrenda());
        prendas.remove(prenda);
    }

    public void eliminaPrenda(int numeroPrenda) {
        Prenda prenda = getPrenda(numeroPrenda);
        if (prenda == null)
            throw new IllegalArgumentException("La prenda no existe!!");
        eliminaPrenda(prenda);
    }

    public void eliminaLote(Lote lote) {
        if (!existeLote(lote))
            throw new IllegalArgumentException("El lote no existe!!");
        lotes.remove(lote);
    }

    public void eliminaLote(int numeroLote) {
        Lote lote = getLote(numeroLote);
        if (lote == null)
            throw new IllegalArgumentException("El lote no existe!!");
        lotes.remove(lote);
    }

    // ─── CONTEOS ─────────────────────────────────────────────────

    public int cantidadDePrendas() { return prendas.size(); }
    public int cantidadDeLotes()   { return lotes.size();   }

    // ─── VISUALIZACIÓN ───────────────────────────────────────────

    public void mostrarPrendas() {
        System.out.println("--- Prendas en la fábrica ---");
        for (int i = 0; i < prendas.size(); i++)
            System.out.println(prendas.get(i));
    }

    public void mostrarLotes() {
        System.out.println("--- Lotes en la fábrica ---");
        for (int i = 0; i < lotes.size(); i++)
            System.out.println(lotes.get(i));
    }
}