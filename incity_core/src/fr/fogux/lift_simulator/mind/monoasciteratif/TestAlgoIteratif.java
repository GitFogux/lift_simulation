package fr.fogux.lift_simulator.mind.monoasciteratif;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.fogux.lift_simulator.Simulation;
import fr.fogux.lift_simulator.batchs.core.MinorableSimulStatCreator;
import fr.fogux.lift_simulator.exceptions.SimulateurAcceptableException;
import fr.fogux.lift_simulator.mind.Algorithme;
import fr.fogux.lift_simulator.mind.trajets.AlgoPersonne;
import fr.fogux.lift_simulator.mind.trajets.Escale;
import fr.fogux.lift_simulator.mind.trajets.EtatMonoAsc;
import fr.fogux.lift_simulator.physic.ConfigSimu;
import fr.fogux.lift_simulator.physic.OutputProvider;
import fr.fogux.lift_simulator.structure.AscId;
import fr.fogux.lift_simulator.utils.BOcamlList;

public class TestAlgoIteratif<T extends Comparable<T>> extends Algorithme implements Comparator<Simulation>
{

    protected EtatMonoAsc etat;

    protected BOcamlList<Escale> trajet;

    protected MinorableSimulStatCreator<T> statCreator;

    public static long debugt;

    public TestAlgoIteratif(final OutputProvider output, final ConfigSimu config, final MinorableSimulStatCreator<T> statCreator)
    {
        super(output, config);
        etat = new EtatMonoAsc(0);
        this.statCreator = statCreator;
    }

    @Override
    public void ping()
    {

    }

    @Override
    public long init()
    {
        return -1;
    }

    @Override
    public void appelExterieur(final int idPersonne, final int niveau, final int destination)
    {
        etat.arrive(new AlgoPersonne(idPersonne, niveau, destination));
        out().println("unappel");
        if(out().simu.getGestio().innerTime() > 0)
        {
            plannifier();

            goToNextEscale();
            if(out().simu.getGestio().innerTime() > 1)
            {
                throw new SimulateurAcceptableException("ce prg n'est pas fait pour une partition standard");
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void plannifier()
    {
        final MonoMemoiser<T> mem = new MonoMemoiser<>(this);
        System.out.println("etatinit " + etat);
        AlgMonoAscIteratif.etatNextSteps(mem, etat, config, new BOcamlList<>(), out().simu);
        final long ti = System.currentTimeMillis();
        for(int i = 0; i < etat.nbSteps() - 1; i ++)
        {
            mem.runStep();
            System.out.println(" restant " + (etat.nbSteps() - i) + " taille " + mem.map.size());

        }
        System.out.println((System.currentTimeMillis() - ti));
        trajet = ((AlgMonoAscIteratif)mem.currentMap().values().stream().min(this).get().getPrgm()).trajet;
        trajet = trajet.reverse();
    }


    @Override
    public List<Integer> listeInvites(final AscId idASc, final int places_disponibles, final int niveau)
    {
        final List<Integer> invites = new ArrayList<>();

        //output.println("trajet " + trajet);
        while(!trajet.isEmpty() && trajet.head.etage == niveau)
        {
            if(trajet.head.invite != null)
            {
                invites.add(trajet.head.invite.id);
            }
            trajet = trajet.queue;
        }
        //output.println("invites " + invites);
        //output.println("trajetfin " + trajet);
        return invites;
    }

    @Override
    public void arretSansOuverture(final AscId idAscenseur)
    {
        throw new SimulateurAcceptableException("A");
    }

    protected void goToNextEscale()
    {
        if(!trajet.isEmpty())
        {
            out().changerDestination(AlgMonoAscIteratif.id, trajet.head.etage, true);
        }
    }

    @Override
    public void finDeTransfertDePersonnes(final AscId idAscenseur, final int niveau)
    {
        goToNextEscale();
    }

    @Override
    public void appelInterieur(final int niveau, final AscId idAscenseur)
    {

    }

    @Override
    public int compare(final Simulation o1, final Simulation o2)
    {
        final T statA = statCreator.produceStat(o1);
        //System.out.println((o1 == o2) + " compare " + ((AlgMonoAscIteratif)o1.getPrgm()).trajet + " val: " + statCreator.produceStat(o1) + " " + o1.getTime() +  " ||| " + ((AlgMonoAscIteratif)o2.getPrgm()).trajet + " val: " + statCreator.produceStat(o2) + " " + o2.getTime() );
        final T statB = statCreator.produceStat(o2);
        return statA.compareTo(statB);
    }

}
