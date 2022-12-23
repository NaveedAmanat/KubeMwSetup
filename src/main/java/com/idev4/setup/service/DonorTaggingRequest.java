package com.idev4.setup.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class DonorTaggingRequest {// extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(DonorTaggingRequest.class);
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    private static String url = "https://scsanctions.un.org/resources/xml/en/consolidated.xml";
    @Autowired
    ServletContext context;
    @Autowired
    EntityManager em;

    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // String myNamespace = "tem";
        // String myNamespaceURI = "http://tempuri.org/";
        // String myNamespace2 = "dat";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        // envelope.addNamespaceDeclaration( myNamespace, myNamespaceURI );
        // envelope.addNamespaceDeclaration( myNamespace2, "http://schemas.datacontract.org/2004/07/DataCheckEnquiry" );

    }

    private static SOAPMessage callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
            return soapResponse;
        } catch (Exception e) {
            System.err.println(
                "\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
        return null;
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
        headers.addHeader("Content-Type", "application/xml");

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    public String getDonorTaggingReport() {

        String soapEndpointUrl = url;
        String soapAction = "";
        String jsonPrettyPrintString = "";
        SOAPMessage msg = callSoapWebService(soapEndpointUrl, soapAction);
        // JSONObject report = new JSONObject();
        // Map< String, Map > resp = new HashMap< String, Map >();
        JSONObject respObject = new JSONObject();
        try {
            log.debug("===============================1==============================");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            String strMsg = new String(out.toByteArray());
            log.debug("===============================2==============================");
            JSONObject xmlJSONObj = XML.toJSONObject(strMsg);
            jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

            System.out.println(jsonPrettyPrintString);

        } catch (SOAPException | IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonPrettyPrintString;
    }
}
