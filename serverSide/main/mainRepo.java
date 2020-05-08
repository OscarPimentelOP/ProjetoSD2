package serverSide.main;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import serverSide.ServerCom;
import serverSide.Proxys.RepoProxy;
import serverSide.sharedRegionInterfaces.RepoInterface;
import serverSide.sharedRegions.Repo;

public class mainRepo {
	
	public static int terminated;
	
    public static void main(String[] args) {
    	
    	//Repository port
        final int portNumb = SimulatorParam.repoPort;

        ServerCom scon, sconi;
        RepoProxy repoProxy;

        //Create listening channel
        scon = new ServerCom(portNumb);
        scon.start();

      //Instantiate Shared Region
        Repo repo = null;
        try {
            repo = new Repo();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
        //Instantiate Shared Region interface
	    RepoInterface repoInter = new RepoInterface(repo);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 2)
	    { 	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	repoProxy = new RepoProxy(sconi, repoInter);    
		    	repoProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
    
}