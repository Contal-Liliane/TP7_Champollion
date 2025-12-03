package champollion;

import java.util.ArrayList;
import java.util.List;

public class Enseignant extends Personne {

    /** Liste des services prévus pour cet enseignant */
    private final List<ServicePrevu> servicesPrevus = new ArrayList<>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant.
     *
     * Si un service prévu existe déjà pour cette UE,
     * on cumule les volumes horaires.
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        // Chercher si un service prévu existe déjà pour cette UE
        for (ServicePrevu sp : servicesPrevus) {
            if (sp.getUe().equals(ue)) {
                // Accumulation des volumes horaires
                int newCM = sp.getVolumeCM() + volumeCM;
                int newTD = sp.getVolumeTD() + volumeTD;
                int newTP = sp.getVolumeTP() + volumeTP;

                // Remplacer l’objet existant (plus simple que changer les attributs privés)
                servicesPrevus.remove(sp);
                servicesPrevus.add(new ServicePrevu(ue, newCM, newTD, newTP));
                return;
            }
        }

        // Pas encore de service pour cette UE → ajout simple
        servicesPrevus.add(new ServicePrevu(ue, volumeCM, volumeTD, volumeTP));
    }

    /**
     * Calcule le nombre total d'heures prévues en équivalent TD.
     */
    public int heuresPrevues() {
        double total = 0.0;

        for (ServicePrevu sp : servicesPrevus) {
            total += sp.getVolumeCM() * 1.5;
            total += sp.getVolumeTD() * 1.0;
            total += sp.getVolumeTP() * 0.75;
        }

        return (int) Math.round(total);
    }

    /**
     * Calcule le nombre total d'heures prévues en équivalent TD pour une UE donnée.
     */
    public int heuresPrevuesPourUE(UE ue) {
        double total = 0.0;

        for (ServicePrevu sp : servicesPrevus) {
            if (sp.getUe().equals(ue)) {
                total += sp.getVolumeCM() * 1.5;
                total += sp.getVolumeTD() * 1.0;
                total += sp.getVolumeTP() * 0.75;
            }
        }

        return (int) Math.round(total);
    }
}
