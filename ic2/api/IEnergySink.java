package ic2.api;

public interface IEnergySink extends IEnergyAcceptor
{
    boolean demandsEnergy();

    int injectEnergy(Direction directionFrom, int amount);
}
