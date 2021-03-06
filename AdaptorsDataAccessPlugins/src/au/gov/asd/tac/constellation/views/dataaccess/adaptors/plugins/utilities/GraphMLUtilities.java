package au.gov.asd.tac.constellation.views.dataaccess.adaptors.plugins.utilities;

import static au.gov.asd.tac.constellation.views.dataaccess.adaptors.plugins.importing.ImportFromGraphMLPlugin.DATA_TAG;
import static au.gov.asd.tac.constellation.views.dataaccess.adaptors.plugins.importing.ImportFromGraphMLPlugin.KEY_TAG;
import static au.gov.asd.tac.constellation.views.dataaccess.adaptors.plugins.importing.ImportFromGraphMLPlugin.NAME_TYPE_DELIMITER;
import au.gov.asd.tac.constellation.graph.processing.RecordStore;
import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Copyright 2010-2019 Australian Signals Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 * @author canis_majoris
 */
public class GraphMLUtilities {
    
    /**
     * This method adds attributes to nodes or transactions
     * @param node
     * @param nodeAttributes
     * @param result
     * @param element 
     */
    public static void addAttributes(Node node, HashMap<String, String> nodeAttributes, RecordStore result, String element) {
        
        NodeList children = node.getChildNodes();
        for (int childIndex = 0; childIndex < children.getLength(); childIndex++) {
            final Node childNode = children.item(childIndex);
            if (childNode != null && childNode.getNodeName().equals(DATA_TAG)) {
                final String attribute = childNode.getAttributes().getNamedItem(KEY_TAG).getNodeValue();
                final String value = childNode.getTextContent();
                final String attr = nodeAttributes.get(attribute);
                final String attr_name = attr.split(NAME_TYPE_DELIMITER)[0];
                final String attr_type = attr.split(NAME_TYPE_DELIMITER)[1];
                addAttribute(result, element, attr_type, attr_name, value);
            }
        }
    }
    
    /**
     * This method adds the attribute to the RecordStore depending on the type.
     * @param result
     * @param element
     * @param attr_type
     * @param attr_name
     * @param value 
     */
    public static void addAttribute(RecordStore result, String element, String attr_type, String attr_name, String value) {
        switch (attr_type) {
            case "boolean":
                result.set(element + attr_name, Boolean.parseBoolean(value));
            case "int":
                result.set(element + attr_name, Integer.parseInt(value));
            case "long":
                result.set(element + attr_name, Long.parseLong(value));
            case "float":
                result.set(element + attr_name, Float.parseFloat(value));
            case "double":
                result.set(element + attr_name, Double.parseDouble(value));
            default:
                result.set(element + attr_name, value);
        }
    }
    
    
}
