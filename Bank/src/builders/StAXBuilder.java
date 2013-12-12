package builders;

import bank.Account;
import bank.Client;
import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StAXBuilder extends Builder {
    Account account;

    public void buildClient(String input) {
        account = new Account();
        try {
            client = new Client();
            String tagContent = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("input.xml"));

            while(reader.hasNext()){
                int event = reader.next();

                switch(event){
                    case XMLStreamConstants.START_ELEMENT:
                        if ((reader.getLocalName()).equalsIgnoreCase("account")){
                            account = new Account();
                        if (reader.getAttributeValue(0).equalsIgnoreCase("active"))
                            account.setActive(true);
                        else
                            account.setActive(false);
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                            if ("money".equals(reader.getLocalName())) {
                                account.setMoney(Integer.parseInt(tagContent));
                                client.addAccount(account);
                            }
                }

            }


        } catch(XMLStreamException e){
            e.printStackTrace();
        } catch(FileNotFoundException e){
            e.printStackTrace();

        }
    }
}
