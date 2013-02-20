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

package com.era7.bioinfo.bio4j.blueprints.model.relationships.comment;

import com.tinkerpop.blueprints.Edge;

/**
 * Biophysical and physicochemical properties
 * @author Pablo Pareja Tobes <ppareja@era7.com>
 */
public class BioPhysicoChemicalPropertiesCommentRel extends BasicCommentRel{

    public static final String RELATIONSHIP_NAME = "COMMENT_BIO_PHYSICO_CHEMICAL_PROPERTIES";

    public static final String UNIPROT_ATTRIBUTE_TYPE_VALUE = "biophysicochemical properties";

    public static final String PH_DEPENDENCE_PROPERTY = "biophysicochemicalproperties_comment_ph_dependence";
    public static final String TEMPERATURE_DEPENDENCE_PROPERTY = "biophysicochemicalproperties_comment_temperature_dependence";
    public static final String KINETICS_XML_PROPERTY = "biophysicochemicalproperties_comment_kinetics_xml";
    public static final String ABSORPTION_MAX_PROPERTY = "biophysicochemicalproperties_comment_absortption_max";
    public static final String ABSORPTION_TEXT_PROPERTY = "biophysicochemicalproperties_comment_absorption_text";
    public static final String REDOX_POTENTIAL_PROPERTY = "biophysicochemicalproperties_comment_redox_potential";
    public static final String REDOX_POTENTIAL_EVIDENCE_PROPERTY = "biophysicochemicalproperties_comment_redox_potential_evidence";

    public BioPhysicoChemicalPropertiesCommentRel(Edge e){
        super(e);
    }

    public String getPhDependence(){
        return String.valueOf(edge.getProperty(PH_DEPENDENCE_PROPERTY));
    }
    public String getTemperatureDependence(){
        return String.valueOf(edge.getProperty(TEMPERATURE_DEPENDENCE_PROPERTY));
    }
    public String getKineticsXmlProperty(){
        return String.valueOf(edge.getProperty(KINETICS_XML_PROPERTY));
    }
    public String getAbsorptionMax(){
        return String.valueOf(edge.getProperty(ABSORPTION_MAX_PROPERTY));
    }
    public String getAbsorptionText(){
        return String.valueOf(edge.getProperty(ABSORPTION_TEXT_PROPERTY));
    }
    public String getRedoxPotential(){
        return String.valueOf(edge.getProperty(REDOX_POTENTIAL_PROPERTY));
    }
    public String getRedoxPotentialEvidence(){
        return String.valueOf(edge.getProperty(REDOX_POTENTIAL_EVIDENCE_PROPERTY));
    }

    public void setPhDependence(String value){
        edge.setProperty(PH_DEPENDENCE_PROPERTY, value);
    }
    public void setTemperatureDependence(String value){
        edge.setProperty(TEMPERATURE_DEPENDENCE_PROPERTY, value);
    }
    public void setKineticsXml(String value){
        edge.setProperty(KINETICS_XML_PROPERTY, value);
    }
    public void setAbsorptionMax(String value){
        edge.setProperty(ABSORPTION_MAX_PROPERTY, value);
    }
    public void setAbsorptionText(String value){
        edge.setProperty(ABSORPTION_TEXT_PROPERTY, value);
    }
    public void setRedoxPotential(String value){
        edge.setProperty(REDOX_POTENTIAL_PROPERTY, value);
    }
    public void setRedoxPotentialEvidence(String value){
        edge.setProperty(REDOX_POTENTIAL_EVIDENCE_PROPERTY, value);
    }

    @Override
    public String getLabel() {
        return RELATIONSHIP_NAME;
    }

    @Override
    public String toString(){
        return super.toString();
    }

}

