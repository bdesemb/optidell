package be.ipl.finito.ucc;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;

@Remote
public interface GestionCase {
    
    Case creerCase(int numero);
    boolean placerJeton(Case caseLue, Jeton jeton);
    boolean retirerJeton(Case caseLue, Jeton jeton);
    Case rechercherCasePourId(int id);

}
