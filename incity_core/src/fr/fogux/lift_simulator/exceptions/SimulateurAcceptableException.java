package fr.fogux.lift_simulator.exceptions;

public class SimulateurAcceptableException extends SimulateurException
{
    private static final long serialVersionUID = 15645491234211699L;
    public boolean printed;

    public SimulateurAcceptableException(final String description)
    {
        super(description);
    }


}
