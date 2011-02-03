/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era7.bioinfo.bio4j.programs;

import com.era7.bioinfo.bio4jmodel.nodes.GoTermNode;
import com.era7.bioinfo.bio4jmodel.relationships.GoParentRel;
import com.era7.bioinfo.bio4jmodel.relationships.go.MainGoRel;
import com.era7.bioinfo.bioinfoneo4j.Neo4jManager;
import com.era7.bioinfo.bio4j.CommonData;
import com.era7.lib.bioinfoxml.gexf.AttValueXML;
import com.era7.lib.bioinfoxml.gexf.AttValuesXML;
import com.era7.lib.bioinfoxml.gexf.AttributeXML;
import com.era7.lib.bioinfoxml.gexf.AttributesXML;
import com.era7.lib.bioinfoxml.gexf.EdgeXML;
import com.era7.lib.bioinfoxml.gexf.GexfXML;
import com.era7.lib.bioinfoxml.gexf.GraphXML;
import com.era7.lib.bioinfoxml.gexf.NodeXML;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author Pablo Pareja Tobes <ppareja@era7.com>
 */
public class GenerateGexfGo {

    public static int edgesIdCounter = 0;
    public static int nodesCounter = 0;
    public static GoParentRel goParentRel = new GoParentRel(null);
    public static int maxTerms = 200;
    public static ArrayList<String> alreadyVisitedNodes = new ArrayList<String>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("El programa espera un parametro: \n"
                    + "1. Nombre del archivo de salida gefx\n");
        } else {

            BufferedWriter outBuff = null;
            try {

                outBuff = new BufferedWriter(new FileWriter(new File(args[0])));

                outBuff.write("<?xml version=\"1.0\" encoding=\"UTF8\"?>" + "\n");
                outBuff.write("<" + GexfXML.TAG_NAME + ">\n");
                outBuff.write("<" + GraphXML.TAG_NAME + " defaultedgetype=\"directed\">\n");

                //GexfXML gexfXML = new GexfXML();

//                GraphXML graphXML = new GraphXML();
//                graphXML.setDefaultEdgeType(GraphXML.DIRECTED_EDGE_TYPE);
//                gexfXML.setGraph(graphXML);

                //node attributes
                AttributesXML attributesXML = new AttributesXML();
                attributesXML.setClass(AttributesXML.NODE_CLASS);
                AttributeXML idAttributeXML = new AttributeXML();
                idAttributeXML.setId("0");
                idAttributeXML.setTitle("Go Term ID");
                idAttributeXML.setType("string");
                attributesXML.addAttribute(idAttributeXML);
                AttributeXML nameAttributeXML = new AttributeXML();
                nameAttributeXML.setId("1");
                nameAttributeXML.setTitle("Go Term Name");
                nameAttributeXML.setType("string");
                attributesXML.addAttribute(nameAttributeXML);

                outBuff.write(attributesXML.toString() + "\n");

                //graphXML.addAttributes(attributesXML);


//                NodesXML nodesXML = new NodesXML();
//                EdgesXML edgesXML = new EdgesXML();

                StringBuilder nodesXMLStBuilder = new StringBuilder("<nodes>\n");
                StringBuilder edgesXMLStBuilder = new StringBuilder("<edges>\n");

//                graphXML.setNodes(nodesXML);
//                graphXML.setEdges(edgesXML);

                Transaction txn = null;
                Neo4jManager manager = null;

                try {
                    System.out.println("creating neo4j manager...");
                    manager = Neo4jManager.getNeo4JManager(CommonData.DATABASE_FOLDER);
                    System.out.println("getting transaction...");
                    txn = manager.beginTransaction();

                    Iterator<Relationship> iterator = manager.getReferenceNode().getRelationships(new MainGoRel(null), Direction.OUTGOING).iterator();
                    if (iterator.hasNext()) {
                        GoTermNode mainGoTermNode = new GoTermNode(iterator.next().getEndNode());
                        System.out.println("getting ontology for " + mainGoTermNode.getName());
                        getGoDescendants(mainGoTermNode, nodesXMLStBuilder, edgesXMLStBuilder);
                    }

                    txn.success();
                } catch (Exception e) {
                    txn.failure();
                } finally {
                    txn.finish();
                    manager.shutDown();
                }

                //outBuff.write(gexfXML.toString());

                outBuff.write(nodesXMLStBuilder.toString() + "</nodes>\n");
                outBuff.write(edgesXMLStBuilder.toString() + "</edges>\n");

                outBuff.write("</" + GraphXML.TAG_NAME + ">\n");
                outBuff.write("</" + GexfXML.TAG_NAME + ">\n");
                outBuff.close();



                System.out.println("done!! :)");


            } catch (IOException ex) {
                Logger.getLogger(GenerateGexfGo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private static void getGoDescendants(GoTermNode parent, StringBuilder nodes, StringBuilder edges) {

        nodesCounter++;

        NodeXML nodeXML = new NodeXML();
        nodeXML.setId(parent.getId());
        nodeXML.setLabel(parent.getName());

        alreadyVisitedNodes.add(parent.getId());

        AttValuesXML attValuesXML = new AttValuesXML();

        AttValueXML goIdAttValueXML = new AttValueXML();
        goIdAttValueXML.setFor(0);
        goIdAttValueXML.setValue(parent.getId());
        attValuesXML.addAttValue(goIdAttValueXML);

        AttValueXML goNameAttValueXML = new AttValueXML();
        goNameAttValueXML.setFor(1);
        goNameAttValueXML.setValue(parent.getName());
        attValuesXML.addAttValue(goNameAttValueXML);

        nodeXML.setAttvalues(attValuesXML);

        //nodes.addNode(nodeXML);
        nodes.append((nodeXML.toString() + "\n"));

        if (nodesCounter <= maxTerms) {
            Iterator<Relationship> iterator = parent.getNode().getRelationships(goParentRel, Direction.INCOMING).iterator();
            while (iterator.hasNext()) {

                goParentRel = new GoParentRel(iterator.next());
                EdgeXML edge = new EdgeXML();
                edge.setId(String.valueOf(edgesIdCounter++));
                edge.setTarget(parent.getId());
                GoTermNode childGo = new GoTermNode(goParentRel.getStartNode());
                edge.setSource(childGo.getId());
                edge.setType(EdgeXML.DIRECTED_TYPE);
                //edges.addEdge(edge);

                edges.append((edge.toString() + "\n"));

                if (!alreadyVisitedNodes.contains(childGo.getId())) {
                    getGoDescendants(childGo, nodes, edges);
                }

            }
        }


    }
}