package Problema1.ui;

import Problema1.coleccion.Fabrica;
import Problema1.data.Lote;
import Problema1.data.Prenda;
import Problema1.data.enumerados.Genero;
import Problema1.data.enumerados.Temporada;
import Problema1.excepciones.ExcepcionDeCostoFueraDeLimite;
import Problema1.excepciones.ExcepcionDeNumeroDePiezasInvalido;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validacion {

    private Scanner input;

    public Validacion(Scanner input) {
        this.input = input;
    }

    public long leerLong(String texto, long min, long max, String error) {
        long valor;
        if (min >= max)
            throw new IllegalArgumentException("El mínimo no puede ser mayor o igual al máximo!!");
        do {
            System.out.print(texto);
            try {
                valor = Long.parseLong(input.nextLine());
            } catch (NumberFormatException e) {
                valor = max + 1;
            }
            if (valor < min || valor > max)
                System.err.println(error);
        } while (valor < min || valor > max);
        return valor;
    }

    public float leerFlotante(String texto, float min, float max, String error) {
        float valor;
        if (min >= max)
            throw new IllegalArgumentException("El mínimo no puede ser mayor o igual al máximo!!");
        do {
            System.out.print(texto);
            try {
                valor = Float.parseFloat(input.nextLine());
            } catch (NumberFormatException e) {
                valor = max + 1;
            }
            if (valor < min || valor > max)
                System.err.println(error);
        } while (valor < min || valor > max);
        return valor;
    }

    public String leerString(String texto, String[] validos, String error) {
        String valor;
        do {
            System.out.print(texto);
            valor = input.nextLine().trim();
            if (validos != null) {
                boolean encontrado = false;
                for (String v : validos)
                    if (v.equalsIgnoreCase(valor)) {
                        valor = v;
                        encontrado = true;
                        break;
                    }
                if (!encontrado) {
                    System.err.println(error);
                    valor = null;
                }
            }
        } while (valor == null || valor.isEmpty());
        return valor;
    }

    public boolean leerBoolean(String texto, String verdadero,
                               String falso, String error) {
        String valor = leerString(texto,
                new String[]{verdadero, falso}, error);
        return valor.equalsIgnoreCase(verdadero);
    }

    public LocalDate leerFecha() {
        LocalDate fecha = null;
        do {
            System.out.print("Proporciona fecha de fabricación [AAAA-MM-DD]: ");
            try {
                fecha = LocalDate.parse(input.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.err.println("Fecha inválida, usa el formato AAAA-MM-DD!!");
            }
        } while (fecha == null);
        return fecha;
    }

    public Genero leerGenero() {
        String[] opciones = {"Masculino", "Femenino", "Mixto"};
        String valor = leerString(
                "Proporciona género [Masculino/Femenino/Mixto]: ",
                opciones, "Género inválido!!");
        return Genero.valueOf(valor);
    }

    public Temporada leerTemporada() {
        String[] opciones = {"Primavera", "Verano", "Otoño", "Invierno"};
        String valor = leerString(
                "Proporciona temporada [Primavera/Verano/Otoño/Invierno]: ",
                opciones, "Temporada inválida!!");
        return Temporada.valueOf(valor);
    }

    public int leerNumeroPrenda() {
        return (int) leerLong(
                "Proporciona número de prenda: ",
                1, 9999, "Número de prenda inválido!!");
    }

    public int leerNumeroLote() {
        return (int) leerLong(
                "Proporciona número de lote: ",
                1, 9999, "Número de lote inválido!!");
    }


    public String leerModelo() {
        return leerString("Proporciona el modelo: ", null, "Modelo inválido!!");
    }

    public String leerTela() {
        return leerString("Proporciona la tela: ", null, "Tela invalida");
        /*String[] telas = {"ALGODÓN", "POLIÉSTER", "LINO", "SEDA", "LANA"};
        return leerString(
                "Proporciona tela [ALGODÓN/POLIÉSTER/LINO/SEDA/LANA]: ",
                telas, "Tela inválida!!");*/
    }

    public float leerCostoProduccion(float costoMaximo) {
        return leerFlotante(
                "Proporciona el costo de producción [0.01 - " + costoMaximo + "]: ",
                0.01f, costoMaximo, "Costo inválido!!");
    }

    public float leerCostoMaximo() {
        return leerFlotante(
                "Proporciona costo máximo permitido: ",
                0.01f, 99999f, "Costo máximo inválido!!");
    }

    // ─── ATRIBUTOS DE LOTE ───────────────────────────────────────

    public int leerNumeroPiezas() {
        return (int) leerLong(
                "Proporciona número de piezas [50 - 350]: ",
                50, 350, "Número de piezas inválido!!");
    }

    // ─── LEER ENTIDADES COMPLETAS ────────────────────────────────

    // equivalente a leerAlumno() del base
    public Prenda leerPrenda() {
        int numeroPrenda = leerNumeroPrenda();
        String modelo = leerModelo();
        String tela = leerTela();
        float costoMaximo = leerCostoMaximo();
        float costo = leerCostoProduccion(costoMaximo);
        Genero genero = leerGenero();
        Temporada temporada = leerTemporada();
        try {
            return new Prenda(numeroPrenda, modelo, tela, costo,
                    genero, temporada, costoMaximo);
        } catch (ExcepcionDeCostoFueraDeLimite e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    // recibe fabrica para verificar que la prenda del lote exista
    public Lote leerLote(Fabrica fabrica) {
        int numeroLote    = leerNumeroLote();
        int numeroPiezas  = leerNumeroPiezas();
        LocalDate fecha   = leerFecha();
        int numeroPrenda  = leerNumeroPrenda();

        Prenda prenda = fabrica.getPrenda(numeroPrenda);
        if (prenda == null) {
            System.err.println("La prenda " + numeroPrenda + " no existe!!");
            return null;
        }
        try {
            return new Lote(numeroLote, numeroPiezas, fecha, prenda);
        } catch (ExcepcionDeNumeroDePiezasInvalido e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}