package champollion;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {

    Enseignant untel;
    UE uml, java;

    @BeforeEach
    public void setUp() {
        untel = new Enseignant("untel", "untel@gmail.com");
        uml = new UE("UML");
        java = new UE("Programmation en java");
    }

    /**
     * 1 — Un nouvel enseignant doit avoir 0 heure prévue.
     */
    @Test
    public void testNouvelEnseignantSansService() {
        assertEquals(0, untel.heuresPrevues(),
            "Un nouvel enseignant doit avoir 0 heures prévues");
    }

    /**
     * 2 — Ajout de 10h TD sur 1 UE.
     */
    @Test
    public void testAjouteHeures() {
        untel.ajouteEnseignement(uml, 0, 10, 0);
        assertEquals(10, untel.heuresPrevuesPourUE(uml));
    }

    /**
     * 3 — Cumul lorsqu’on ajoute deux fois des heures sur la même UE.
     */
    @Test
    public void testCumulHeuresMemeUE() {
        untel.ajouteEnseignement(uml, 0, 10, 0);
        untel.ajouteEnseignement(uml, 0, 20, 0);
        assertEquals(30, untel.heuresPrevuesPourUE(uml));
    }

    /**
     * 4 — Test du calcul équivalent TD (CM = 1.5, TD = 1, TP = 0.75).
     */
    @Test
    public void testEquivalenceHeures() {
        untel.ajouteEnseignement(uml, 2, 2, 4);
        // 2 CM = 3 ; 2 TD = 2 ; 4 TP = 3
        assertEquals(8, untel.heuresPrevuesPourUE(uml));
    }

    /**
     * 5 — Deux UE différentes ne doivent pas mélanger leurs services.
     */
    @Test
    public void testDeuxUE() {
        untel.ajouteEnseignement(uml, 0, 10, 0);
        untel.ajouteEnseignement(java, 0, 20, 0);
        assertEquals(10, untel.heuresPrevuesPourUE(uml));
        assertEquals(20, untel.heuresPrevuesPourUE(java));
        assertEquals(30, untel.heuresPrevues());
    }

    /**
     * 6 — Une UE sans service doit renvoyer 0.
     */
    @Test
    public void testUEInexistante() {
        untel.ajouteEnseignement(uml, 0, 10, 0);
        assertEquals(0, untel.heuresPrevuesPourUE(java));
    }

    /**
     * 7 — Vérifie que les volumes sont cumulés correctement même si on ajoute CM/TD/TP séparément.
     */
    @Test
    public void testAjoutsStandardises() {
        untel.ajouteEnseignement(uml, 1, 0, 0);  // 1 CM → 1.5
        untel.ajouteEnseignement(uml, 0, 2, 0);  // 2 TD → 2
        untel.ajouteEnseignement(uml, 0, 0, 4);  // 4 TP → 3
        assertEquals(7, untel.heuresPrevuesPourUE(uml));
    }


    /**
     * 8 — Test du total global avec plusieurs UE et tous types de cours.
     */
    @Test
    public void testHeuresPrevuesTotales() {
        // UML → 1 CM (1.5) + 2 TD (2) + 2 TP (1.5) = 5
        // Java → 2 CM (3) + 4 TD (4) + 4 TP (3) = 10
        untel.ajouteEnseignement(uml, 1, 2, 2);
        untel.ajouteEnseignement(java, 2, 4, 4);
        assertEquals(15, untel.heuresPrevues());
    }
}
