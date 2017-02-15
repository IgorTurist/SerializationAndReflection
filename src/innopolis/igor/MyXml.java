package innopolis.igor;

import org.w3c.dom.*;

import java.io.File;
import java.lang.reflect.Field;
import javax.management.Attribute;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by igor on 10.02.2017.
 */
public class MyXml {
    public static void savePeopleToXml(Object obj){
        try{
//            if(!(obj instanceof People))
//                throw new Exception("Wrong object type");

            Class obj_class = obj.getClass();
            Field[] fields = obj_class.getDeclaredFields();
// create xml
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
// root elements
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("PeopleXml");
            doc.appendChild(root);
//create Object element
            Element obj_element = doc.createElement("Object");
            obj_element.setAttribute("name",obj_class.getName());
            root.appendChild(obj_element);

            for(Field field: fields){
                field.setAccessible(true);
                Element element = doc.createElement("field");
                element.setAttribute("type",field.getType().getName());
                element.setAttribute("name",field.getName());
                element.setAttribute("value",field.get(obj).toString());
                obj_element.appendChild(element);
            }
// save to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("file.xml"));
            transformer.transform(source, result);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static Object loadPeopleFromXml(String path) {
        Object obj = null;
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes();

            String class_name;
            for(int i = 0;i < list.getLength();i++){
                Node n = list.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE && n.hasChildNodes()) {
                    Element obj_element = (Element)n;

                    class_name = obj_element.getAttribute("name");
                    System.out.println(class_name);
                    Class class_obj = Class.forName(class_name);
                    obj = class_obj.newInstance();

                    NodeList types = obj_element.getChildNodes();
                    for(int j = 0;j<types.getLength();j++){
                        Node type_node = types.item(j);
                        if(type_node.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element type_element = (Element)type_node;
                            String field_name = type_element.getAttribute("name");
                            String field_value = type_element.getAttribute("value");
                            String field_type = type_element.getAttribute("type");

                            Field field = obj.getClass().getDeclaredField(field_name);
                            field.setAccessible(true);
                            if(field.getType().getName() == "int"){
                                field.setInt(obj,Integer.parseInt(field_value));
                            }else if(field.getType().getName() == "double"){
                                field.setDouble(obj,Double.parseDouble(field_value));
                            }else if(field.getType().getName() == "java.lang.String"){
                                field.set(obj,field_value);
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
