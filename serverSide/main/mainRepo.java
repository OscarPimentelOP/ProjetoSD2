package serverSide.main;

import java.io.FileNotFoundException;

import AuxTools.SimulatorParam;
import serverSide.ServerCom;
import serverSide.Proxys.RepoProxy;
import serverSide.sharedRegionInterfaces.RepoInterface;
import serverSide.sharedRegions.Repo;

public class mainRepo {
    public static void main(String[] args) {
        final int portNumb = SimulatorParam.mainRepoPort;

        ServerCom scon, sconi;
        RepoProxy repoProxy;

        scon = new ServerCom(portNumb);
        scon.start();

        Repo repo = null;
        try {
            repo = new Repo();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    RepoInterface repoInter = new RepoInterface(repo);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	repoProxy = new RepoProxy(sconi, repoInter);    
	    	repoProxy.start ();
	    }
	}
    
}