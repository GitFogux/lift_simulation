package fr.fogux.lift_simulator;

import fr.fogux.lift_simulator.fichiers.DataTagCompound;
import fr.fogux.lift_simulator.fichiers.TagNames;

public class SimuResult
{
    public final boolean failed; //a la fin tout le monde n'a pas été transporté
    public final long totalTransportTime;
    public final long maxTransportTime;
    public final int nbPersonneTransportees;

    public SimuResult(final boolean failed, final long totalTransportTime, final long maxTransportTime, final int nbPersonneTransportees)
    {
        this.failed = failed;
        this.totalTransportTime = totalTransportTime;
        this.nbPersonneTransportees = nbPersonneTransportees;
        this.maxTransportTime = maxTransportTime;
    }

    public void printFieldsIn(final DataTagCompound c)
    {
        c.setBoolean(TagNames.failed, failed);
        c.setLong(TagNames.totalTransportTime, totalTransportTime);
        c.setLong(TagNames.maxTransportTime, maxTransportTime);
        c.setInt(TagNames.nbPersonneTransportees, nbPersonneTransportees);
    }
}
