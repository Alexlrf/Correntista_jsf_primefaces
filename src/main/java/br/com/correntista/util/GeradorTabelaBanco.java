package br.com.correntista.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AlexLRF
 */
public class GeradorTabelaBanco {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Correntista_PU");
        emf.close();
    }
}
