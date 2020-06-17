import java.util.ArrayList;
import java.util.Iterator;

import com.mbse.graphx.Port;

public class Utilies {

	public static void positionPort(ArrayList<Port> listOfPorts) {
		double spacing = 0;

		spacing = 1.0 / (listOfPorts.size());

		double Yposition=0;
		for (int i = 0; i < listOfPorts.size(); i++) {
			if (i==0) {
				Yposition = spacing/2.0;
			}
			else
			{
				Yposition = Yposition + spacing;
			}
			System.out.println("Placed: "+Yposition);
			listOfPorts.get(i).getGeometry().setY(Yposition);
		}


	}
	
	public static void sortIRPCollection() {
		
	}

}
