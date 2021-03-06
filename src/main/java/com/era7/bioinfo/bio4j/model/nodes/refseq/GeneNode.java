/*
 * Copyright (C) 2010-2011  "Bio4j"
 *
 * This file is part of Bio4j
 *
 * Bio4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.era7.bioinfo.bio4j.model.nodes.refseq;

import com.era7.bioinfo.bio4j.model.relationships.refseq.GenomeElementGeneRel;
import com.era7.bioinfo.bioinfoneo4j.BasicEntity;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;

/**
 * Gene
 * @author Pablo Pareja Tobes <ppareja@era7.com>
 */
public class GeneNode extends BasicEntity{

    public static final String NODE_TYPE = GeneNode.class.getCanonicalName();

    public static final String NOTE_PROPERTY = "note";
    public static final String POSITIONS_PROPERTY = "positions";


    public GeneNode(Node n){
        super(n);
    }


    public String getPositions(){   return String.valueOf(node.getProperty(POSITIONS_PROPERTY));}
    public String getNote(){   return String.valueOf(node.getProperty(NOTE_PROPERTY));}


    public void setPositions(String value){ node.setProperty(POSITIONS_PROPERTY, value);}
    public void setNote(String value){ node.setProperty(NOTE_PROPERTY, value);}

    public GenomeElementNode getGenomeElement(){
        return new GenomeElementNode(node.getRelationships(new GenomeElementGeneRel(null), Direction.INCOMING).iterator().next().getStartNode());
    }    

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GeneNode){
            GeneNode other = (GeneNode) obj;
            return this.node.equals(other.node);
        }else{
            return false;
        }
    }

}
