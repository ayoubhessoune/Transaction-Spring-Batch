package com.hessoune.batch.reader;

import com.hessoune.dto.TransactionDto;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

// Custom ItemReader for reading TransactionDto from an XML file using StAX
public class TransactionXmlReader extends StaxEventItemReader<TransactionDto> {

    // Constructor to configure the XML reader properties
    public TransactionXmlReader() {
        this.setResource(new ClassPathResource("transactions.xml")); // Set the resource (XML file)
        this.setFragmentRootElementName("transaction"); // Set the root element name for each transaction
        this.setUnmarshaller(xStreamMarshaller()); // Set the unmarshaller for converting XML to objects
    }

    // Configures the XStream marshaller for unmarshalling XML to TransactionDto
    private XStreamMarshaller xStreamMarshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();

        // Set the annotated class (TransactionDto) to be unmarshalled
        marshaller.setAnnotatedClasses(TransactionDto.class);

        // Configure XStream permissions to control what types are allowed during unmarshalling
        marshaller.getXStream().addPermission(NoTypePermission.NONE);
        marshaller.getXStream().addPermission(NullPermission.NULL);
        marshaller.getXStream().addPermission(PrimitiveTypePermission.PRIMITIVES);
        marshaller.getXStream().allowTypes(new Class[] { TransactionDto.class });

        return marshaller;
    }
}
