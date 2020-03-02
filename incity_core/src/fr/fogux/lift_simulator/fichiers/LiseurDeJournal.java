package fr.fogux.lift_simulator.fichiers;

import java.util.Iterator;

import fr.fogux.lift_simulator.evenements.Evenement;

public class LiseurDeJournal implements Iterator<Evenement>, Iterable<Evenement>
{
    protected Evenement nextEvent;

    public LiseurDeJournal()
    {
        nextEvent = Evenement.genererEvenement(GestionnaireDeFichiers.getNextJournalLine());
    }

    @Override
    public Iterator<Evenement> iterator()
    {
        return this;
    }

    @Override
    public boolean hasNext()
    {
        return nextEvent != null;
    }

    @Override
    public Evenement next()
    {

        Evenement ev = nextEvent;
        nextEvent = Evenement.genererEvenement(GestionnaireDeFichiers.getNextJournalLine());
        System.out.println("next Event " + nextEvent);
        return ev;
    }

}
