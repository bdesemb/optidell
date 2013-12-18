package be.ipl.finito.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@SuppressWarnings("serial")
@Entity
@Table(name = "PARTIES", schema = "FINITO")
/**
 * Entité sérialisable de partie
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
public class Partie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Min(1)
	@Max(20)
	private int resultatDe=1;
	@Min(0)
	@Max(12)
	private int indiceTirage=0;

	@Enumerated(EnumType.STRING)
	private Etat etat = Etat.EN_ATTENTE;

	@ManyToMany
	@JoinTable(schema = "FINITO", joinColumns = { @JoinColumn(name = "PARTIE_ID") }, inverseJoinColumns = { @JoinColumn(name = "JETON_ID") })
	@MapKeyColumn(name="position")
	private Map<Integer,Jeton> jetonRestant = new HashMap<Integer,Jeton>();

	@OneToMany(mappedBy = "partie", fetch=FetchType.EAGER)
	private List<Plateau> plateauEnJeu = new ArrayList<Plateau>();

	/**
	 * Constructeur vide de Partie utile pour EJB
	 */
	public Partie() {
		super();
	}

	/**
	 * Getter de etat
	 * 
	 * @return etat
	 */
	public Etat getEtat() {
		return etat;
	}

	/**
	 * Setter de etat
	 * 
	 * @param etat
	 */
	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	/**
	 * Getter du résultat de dé
	 * 
	 * @return le résultat de dé
	 */
	public int getResultatDe() {
		return resultatDe;
	}

	/**
	 * ¨ Setter du resultat de dé
	 * 
	 * @param resultatDe
	 */
	public void setResultatDe(int resultatDe) {
		this.resultatDe = resultatDe;
	}

	/**
	 * Getter de id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter de la liste des jetons restants dans la partie
	 * 
	 * @return la liste des jetons restants
	 */
	public Map<Integer, Jeton> getJetonsRestants(){
		return jetonRestant;
	}

	/**
	 * Getter de la liste des plateaux en jeu
	 * 
	 * @return les plateaux en jeu
	 */
	public List<Plateau> getPlateauEnJeu() {
		
		return plateauEnJeu;
	}

	/**
	 * Getter de l'indice de tirage
	 * 
	 * @return l'indice de tirage
	 */
	public int getIndiceTirage() {
		return indiceTirage;
	}

	/**
	 * Setter de l'indice de tirage
	 * 
	 * @param indiceTirage
	 */
	public void setIndiceTirage(int indiceTirage) {
		this.indiceTirage = indiceTirage;
	}

	public boolean isEnAttente() {
		return etat.isEnAttente();
	}

	public boolean isEnCours() {
		return etat.isEnCours();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partie other = (Partie) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum Etat {
		EN_ATTENTE {
			public boolean isEnAttente() {
				return true;
			}
			
			public void debuterPartie(Partie partie){
				List<Integer> listePositions = new ArrayList<Integer>();
				for (int i = 0; i < 3; i++) 
					partie.piocherJeton();
				partie.setEtat(EN_COURS);
				partie.lancerDe();
			}
		},
		EN_COURS {
			public boolean isEnCours() {
				return true;
			}
			
			public Jeton piocherJeton(Partie partie) {
				Map<Integer, Jeton> listeJetonsRestants = partie.getJetonsRestants();
				List<Plateau> listePlateau = partie.getPlateauEnJeu();

				if(listeJetonsRestants.isEmpty())
					return null;
				
				Jeton jeton = listeJetonsRestants.get(partie.getIndiceTirage());
				
				for (Plateau plateau :  listePlateau){
					plateau.getJetonsEnMain().add(jeton);
				}
				return jeton;
			}

			public int[] finirPartie(Partie partie) {
				List<Plateau> listePlateau = partie.getPlateauEnJeu();
				int[] score = new int[listePlateau.size()];

				for (int i = 0; i < listePlateau.size(); i++) {
					score[i] = listePlateau.get(i).calculerScore();
				}
				partie.setEtat(FINI);
				return score;
			}

			public void suspendrePartie(Partie partie) {
				partie.setEtat(Etat.SUSPENDU);
			}
			
		},
		SUSPENDU {
			public void reprendreJoueur(Plateau plateau, Partie partie) {
				plateau.setSuspendu(false);
				List<Plateau>plateauDeJeu = partie.getPlateauEnJeu();
				int nbJoueurPret = 0;
				for (Plateau p : plateauDeJeu){
					if(p.isSuspendu() == false)
						nbJoueurPret ++;
				}
				if(nbJoueurPret == plateauDeJeu.size())
					partie.setEtat(EN_COURS);
			}
		},
		FINI {

		};

		public boolean isEnAttente() {
			return false;
		}

		public boolean isEnCours() {
			return false;
		}

		public void suspendrePartie(Partie partie) {
			throw new UnsupportedOperationException();
		}

		public int[] finirPartie(Partie partie) {
			throw new UnsupportedOperationException();
		}

		public Jeton piocherJeton(Partie partie) {
			throw new UnsupportedOperationException();
		}

		public void reprendreJoueur(Plateau plateau, Partie partie) {
			throw new UnsupportedOperationException();
			
		}
		public void debuterPartie(Partie partie){
			throw new UnsupportedOperationException();
		}

	}

	public int lancerDe() {
		return resultatDe = (int) ((Math.random()*100) % 20);
	}

	public void incrementerTirage() {
		indiceTirage++;
	}

	public int[] finirPartie() {
		int[] score = etat.finirPartie(this);
		return score;
	}

	public Jeton piocherJeton() {
		return etat.piocherJeton(this);
	}

	public void reprendreJoueur(Plateau plateau) {
		etat.reprendreJoueur(plateau, this);
	}
}
