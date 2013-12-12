package builders;

import bank.Account;
import bank.Client;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.IOException;


public class DOMBuilder extends Builder {

    public void buildClient(String input) {
        try {
            client = new Client();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("ns:account");
            double money;
            boolean activity;
            for (int i = 0; i < nodeLst.getLength(); i++) {
                money = Double.parseDouble(((Node) ((Element) nodeLst.item(i)).getElementsByTagName("ns:money").item(0).getFirstChild()).getNodeValue().toString());
                if ((((Element) nodeLst.item(i)).getAttribute("ns:type")).toString().equalsIgnoreCase("active"))
                    activity = true;
                else
                    activity = false;
                client.addAccount(new Account(activity, money));
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

