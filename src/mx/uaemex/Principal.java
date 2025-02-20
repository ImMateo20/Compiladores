package mx.uaemex;

import java.io.IOException;

import mx.uaemex.AutomataFinito.AutomataFinito;
import mx.uaemex.ExpresionesRegulares.Regexx;
import mx.uaemex.LexerProfe.LexerPrincipal;
import mx.uaemex.LexerPrueba.Lexer;
import mx.uaemex.LexerYO.LexerYO;

public class Principal {
    public static void main(String[] args) throws IOException {
        // Expresiones Regulares
        // Regexx.start(); //descomentar para probar

        // Automatas finitos
        // AutomataFinito.start(); //descomentar para probar

        // Primer prueba de Lexers
        // Lexer.start(); //descomentar para probar

        // Lexers hecho por mi
        // LexerYO.start(); //descomentar para probar

        // Lexers profe
        LexerPrincipal.start(); //descomentar para probar
    }
}
