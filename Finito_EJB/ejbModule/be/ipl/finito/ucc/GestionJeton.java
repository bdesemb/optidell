package be.ipl.finito.ucc;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Jeton;


@Remote
public interface GestionJeton {
    
    Jeton rechercheJetonPourNumero(int numero);

}