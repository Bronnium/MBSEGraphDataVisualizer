import com.mbse.graphx.ui.MbseGraphVisualizer;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.view.mxGraph;

public class MainExecution {

	public static void main(String[] args) {
		MbseGraphVisualizer graphicalInterface = new MbseGraphVisualizer();

		graphicalInterface.setGraphData(createDummyData());
		
		graphicalInterface.setVisible(true);
	}


	private static mxGraph createDummyData() {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

        final mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);         
        //layout.setUseBoundingBox(false);
        //layout.setEdgeRouting(false);
        //layout.setLevelDistance(30);
        //layout.setNodeDistance(10);

		
		graph.getModel().beginUpdate();
		try {
            Object root = graph.insertVertex(parent, "treeRoot", "Root", 0, 0, 60, 40);

            Object v1 = graph.insertVertex(parent, "v1", "Child 1", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", root, v1);

            Object v2 = graph.insertVertex(parent, "v2", "Child 2", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", root, v2);

            Object v3 = graph.insertVertex(parent, "v3", "Child 3", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", root, v3);

            Object v11 = graph.insertVertex(parent, "v11", "Child 1.1", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v1, v11);

            Object v12 = graph.insertVertex(parent, "v12", "Child 1.2", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v1, v12);

            Object v21 = graph.insertVertex(parent, "v21", "Child 2.1", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v2, v21);

            Object v22 = graph.insertVertex(parent, "v22", "Child 2.2", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v2, v22);

            Object v221 = graph.insertVertex(parent, "v221", "Child 2.2.1", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v22, v221);

            Object v222 = graph.insertVertex(parent, "v222", "Child 2.2.2", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v22, v222);

            Object v31 = graph.insertVertex(parent, "v31", "Child 3.1", 0, 0, 60, 40);
            graph.insertEdge(parent, null, "", v3, v31);
			
			layout.execute(parent);
		} finally {
			graph.getModel().endUpdate();
		}
		
		return graph;
	}

}
