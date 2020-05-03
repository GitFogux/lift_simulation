package fr.fogux.lift_simulator.utils;

import fr.fogux.lift_simulator.fichiers.DataTagCompound;
import fr.fogux.lift_simulator.physic.ConfigSimu;
import fr.fogux.lift_simulator.structure.AscDeplacementFunc;
import fr.fogux.lift_simulator.structure.DeplacementFunc;

public class Tests
{
    public static void main (final String[] arg) {

        /*final int b = (int)Math.floor(-2.3d);
        System.out.println(b);*/
        /*
         *  final ConfigSimu c = new ConfigSimu(new DataTagCompound("{acceleration:1.2000001E-7,dureePortes:2000,ascenseurSpeed:0.0023,capaciteAsc:5,repartAscenseurs:{{val:2},{val:2},{val:2},{val:2}},margeInterAsc:0.8,dureeEntreeSortiePers:1200,niveauMin:-3,niveauMax:20}"));
        final long t0 = 0;
        final float xi = 0.00000006f;
        final float vi = -0.000000012f;
        final float xf = 12f;
         */

        final ConfigSimu c = new ConfigSimu(new DataTagCompound("{acceleration:1.2000001E-7,dureePortes:2000,ascenseurSpeed:0.0023,capaciteAsc:5,repartAscenseurs:{{val:2},{val:2},{val:2},{val:2}},margeInterAsc:0.8,dureeEntreeSortiePers:1200,niveauMin:-3,niveauMax:20}"));
        final long t0 = 0;
        final float xi = 4f; //3.6993155f
        System.out.println("maxspeed " + c.getAscenseurSpeed());
        final float vi = 0.0020f; // 0.0005587167f
        //final float vi = 0f;
        final float xf = 3.0f;
        final AscDeplacementFunc fc = AscDeplacementFunc.getDeplacementFunc(c,t0,xi, vi, xf);

        System.out.println("arrivee " + AscDeplacementFunc.getTimeStraightToObjective(c,t0,  vi, xi, xf));

        System.out.println(fc);


        tester(fc, -60*1000,60*1000,100);

    }
    public static void tester(final DeplacementFunc fct,final double xMin,final double xMax,final long nbPoints)
    {
        final Fct fctTest = new Fct() {

            @Override
            public double getY(final double x)
            {
                return fct.getX((long) x);
            }

        };
        tester(fctTest, xMin,xMax,nbPoints);
    }

    public static void tester(final Fct fct,final double xMin,final double xMax,final long nbPoints)
    {
        final double ecart = (xMax-xMin)/nbPoints;
        double v;
        for(double d = xMin; d <= xMax; d += ecart)
        {
            v = fct.getY(d);
            System.out.println(d + " \t " + (Double.isNaN(v)?"":v));
        }
    }
}
