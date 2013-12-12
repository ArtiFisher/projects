package builders;

import bank.Account;
import bank.Client;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXBuilder extends Builder {
    Account account;

    public void buildClient(String input) {
        try {
            client = new Client();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                String content;

                @Override
                public void startElement(String uri, String localName,
                                         String qName, Attributes attributes)
                        throws SAXException {

                    //Create a new Employee object when the start tag is found
                    if (qName.equalsIgnoreCase("ns:account")) {
                        account = new Account();
                        if (attributes.getValue("ns:type").equalsIgnoreCase("active"))
                            account.setActive(true);
                        else
                            account.setActive(false);
                    }
                }

                @Override
                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {

                    if (qName.equalsIgnoreCase("ns:account")) {
                        client.addAccount(account);

                    } else if (qName.equalsIgnoreCase("ns:money")) {

                        account.setMoney(Integer.parseInt(content));
                    }

                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    content = String.copyValueOf(ch, start, length).trim();
                }

            };

            saxParser.parse("input.xml", handler);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
