package be.ipl.finito.ucc;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import javax.ejb.Remote;

@Remote
public interface GestionCase {
    
    Case creerCase(int numero);
    boolean placerJeton(Case caseLue, Jeton jeton);
    boolean retirerJeton(Case caseLue, Jeton jeton);

}
