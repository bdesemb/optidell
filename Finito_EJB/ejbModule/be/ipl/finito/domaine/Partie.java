package be.ipl.finito.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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
import javax.persistence.OrderBy;
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
	private int resultatDe = 1;
	@Min(0)
	@Max(12)
	private int indiceTirage = 0;

	@Enumerated(EnumType.STRING)
	private Etat etat = Etat.EN_ATTENTE;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(schema = "FINITO", joinColumns = { @JoinColumn(name = "PARTIE_ID") }, inverseJoinColumns = { @JoinColumn(name = "JETON_ID") })
	@MapKeyColumn(name = "position")
	private Map<Integer, Jeton> jetonRestant = new HashMap<Integer, Jeton>();

	@OneToMany(mappedBy = "partie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("id")
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
	public void setEtat(final Etat etat) {
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
	public void setResultatDe(final int resultatDe) {
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
	public Map<Integer, Jeton> getJetonsRestants() {
		return jetonRestant;
	}

	public void setJetonsRestants(final Map<Integer, Jeton> map) {
		this.jetonRestant = map;
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
	public void setIndiceTirage(final int indiceTirage) {
		this.indiceTirage = indiceTirage;
	}

	/**
	 * Renvoie "true" si l'état de la partie est en attente ou "false" si la partie n'est pas en attente
	 * @return true ou false en fonction de la valeur de l'état enAttente;
	 */
	public boolean isEnAttente() {
		return etat.isEnAttente();
	}
	/**
	 * Renvoie "true" si l'état de la partie est en cours ou "false" si la partie n'est pas en cours
	 * @return true ou false en fonction de la valeur de l'état enCours;
	 */
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
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Partie other = (Partie) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public enum Etat {
		EN_ATTENTE {
			public boolean isEnAttente() {
				return true;
			}

			public void debuterPartie(final Partie partie) {
				partie.setEtat(EN_COURS);
				for (int i = 0; i < 3; i++) {
					partie.incrementerTirage();
				}
				partie.lancerDe();
			}
			
			public void suspendrePartie(final Partie partie){
				partie.setEtat(FINI);
			}
		},
		EN_COURS {
			public boolean isEnCours() {
				return true;
			}

			public Jeton piocherJeton(final Partie partie) {
				Map<Integer, Jeton> listeJetonsRestants = partie
						.getJetonsRestants();

				if (listeJetonsRestants.isEmpty()) {
					System.out.println("J'ai pas de jetons à donner");
					return null;
				}

				Jeton jeton = listeJetonsRestants.get(partie.getIndiceTirage());
				return jeton;
			}

			public void finirPartie(final Partie partie) {
				partie.setEtat(FINI);
			}

			public void suspendrePartie(final Partie partie) {
				partie.setEtat(Etat.SUSPENDU);
			}

		},
		SUSPENDU {
			public void reprendreJoueur(final Plateau plateau,
					final Partie partie) {
				plateau.setSuspendu(false);
				List<Plateau> plateauDeJeu = partie.getPlateauEnJeu();
				int nbJoueurPret = 0;
				for (Plateau p : plateauDeJeu) {
					if (p.isSuspendu() == false) {
						nbJoueurPret++;
					}
				}
				if (nbJoueurPret == plateauDeJeu.size()) {
					partie.setEtat(EN_COURS);
				}
			}
			
			public void suspendrePartie(final Partie partie) {
				partie.setEtat(Etat.SUSPENDU);
			}
			
		},
		FINI {

		};
		/**
		 * Renvoie "true" si l'état de la partie est en attente ou "false" si la partie n'est pas en attente
		 * @return false quelque soit l'état sauf si une implémentation est faites pour un état spécifique
		 */
		public boolean isEnAttente() {
			return false;
		}
		/**
		 * Renvoie "true" si l'état de la partie est en cours ou "false" si la partie n'est pas en cours
		 * @return false quelque soit l'état sauf si une implémentation est faites pour un état spécifique
		 */
		public boolean isEnCours() {
			return false;
		}

		public void suspendrePartie(final Partie partie) {
			throw new UnsupportedOperationException();
		}
		/**
		 * Finit la partie en retournant la table des scores
		 * @return la table des scores
		 */
		public void finirPartie(final Partie partie) {
			throw new UnsupportedOperationException();
		}
		/**
		 * Pioche un jeton dans la liste des jetons restant
		 * @return le jeton pioché
		 */
		public Jeton piocherJeton(final Partie partie) {
			throw new UnsupportedOperationException();
		}
		/**
		 * Reprend un joueur dans une partie
		 * @param plateau
		 */
		public void reprendreJoueur(final Plateau plateau, final Partie partie) {
			throw new UnsupportedOperationException();

		}
		/**
		 *  Débute la partie
		 */
		public void debuterPartie(final Partie partie) {
			throw new UnsupportedOperationException();
		}

	}
	/**
	 * Retourne la valeur d'un dé généré aléatoirement
	 * @return la valeur d'un dé généré aléatoirement
	 */
	public int lancerDe() {
		return resultatDe = (int) ((Math.random() * 100) % 20) + 1;
	}

	/**
	 * Incrémente de 1 l'indice de tirage
	 */
	public void incrementerTirage() {
		indiceTirage++;
	}

	/**
	 * Finit la partie en retournant la table des scores
	 * @return la table des scores
	 */
	public void finirPartie() {
		etat.finirPartie(this);
	}
	/**
	 * Pioche un jeton dans la liste des jetons restant
	 * @return le jeton pioché
	 */
	public Jeton piocherJeton() {
		return etat.piocherJeton(this);
	}
	/**
	 * Reprend un joueur dans une partie
	 * @param plateau
	 */
	public void reprendreJoueur(final Plateau plateau) {
		etat.reprendreJoueur(plateau, this);
	}
	/**
	 *  Débute la partie
	 */
	public void debuterPartie() {
		etat.debuterPartie(this);
	}
}
