package com.mbse.graphx;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
 
public class MainTest extends JFrame {
 
  /** Pour éviter un warning venant du JFrame */
  private static final long serialVersionUID = -8123406571694511514L;
 
  public MainTest() {
    super("JGrapghX tutoriel: Exemple 1");
 
    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
 
    graph.getModel().beginUpdate();
    try {
      Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
      Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
      graph.insertEdge(parent, null, "Edge", v1, v2);
    } finally {
      graph.getModel().endUpdate();
    }
 
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    getContentPane().add(graphComponent);
  }
 
  /**
   * @param args
   */
  public static void main(String[] args) {
    MainTest frame = new MainTest();
        
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 320);
    
    // Construction et injection de la barre d'outils
    JPanel contentPane = (JPanel) frame.getContentPane();
    contentPane.add( createToolBar(), BorderLayout.NORTH );
    
    frame.setVisible(true);
  }

  /* Méthode de construction de la barre d'outils */
  private static JToolBar createToolBar() {

      // La barre d'outils à proprement parler
      JToolBar toolBar = new JToolBar();

      JButton btnNew = new JButton( new ImageIcon( "icons/new.png") );
      btnNew.setToolTipText( "New File (CTRL+N)" );
      //btnNew.addActionListener( this::btnNewListener );
      toolBar.add( btnNew );

      JButton btnSave = new JButton( new ImageIcon( "icons/save.png" ) );
      btnSave.setToolTipText( "Save (CTRL+S)" );
      toolBar.add( btnSave );

      JButton btnSaveAs = new JButton( new ImageIcon( "icons/save_as.png" ) );
      btnSaveAs.setToolTipText( "Save As..." );
      toolBar.add( btnSaveAs );

      toolBar.addSeparator();

      JButton btnCopy = new JButton( new ImageIcon( "icons/copy.png") );
      btnCopy.setToolTipText( "Copy (CTRL+C)" );
      toolBar.add( btnCopy );

      JButton btnCut = new JButton( new ImageIcon( "icons/cut.png") );
      btnCut.setToolTipText( "Cut (CTRL+X)" );
      toolBar.add( btnCut );

      JButton btnPaste = new JButton( new ImageIcon( "icons/paste.png") );
      btnPaste.setToolTipText( "Paste (CTRL+V)" );
      toolBar.add( btnPaste );

      toolBar.addSeparator();

      JButton btnExit = new JButton( new ImageIcon( "icons/exit.png") );
      btnExit.setToolTipText( "Exit (ALT+F4)" );
      toolBar.add( btnExit );

      toolBar.addSeparator();

      // Autres types de composants graphiques
      toolBar.add( new JButton( new ImageIcon( "icons/aFile.png" ) ) );
      toolBar.add( new JCheckBox( "Check me" ) );
      toolBar.add( new JTextField( "Edit me" ) );

      return toolBar;
  }
}