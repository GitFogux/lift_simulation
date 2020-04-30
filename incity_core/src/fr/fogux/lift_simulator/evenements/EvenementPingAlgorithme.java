package fr.fogux.lift_simulator.evenements;

import fr.fogux.lift_simulator.AnimationProcess;
import fr.fogux.lift_simulator.Simulation;

public class EvenementPingAlgorithme extends Evenement
{

    public EvenementPingAlgorithme(final long time)
    {
        super(time);
    }

    @Override
    public void simuRun(final Simulation simulation)
    {
        simulation.getPrgm().ping();
        if(simulation.getGestio().nbRemainingEventsTimes() > 1)
        {
            simulation.getGestio().executerA(this, simulation.getTime() + simulation.getConfig().getPingTime());
        }
    }

    @Override
    public void visuRun(final AnimationProcess animation)
    {
    }

}
